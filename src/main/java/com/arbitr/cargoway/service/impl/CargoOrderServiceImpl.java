package com.arbitr.cargoway.service.impl;

import com.arbitr.cargoway.dto.general.cargo.CarrierVisibilityCategory;
import com.arbitr.cargoway.dto.general.cargo.VisibilityCategory;
import com.arbitr.cargoway.dto.general.cargo.CargoDto;
import com.arbitr.cargoway.dto.rq.PaginationRq;
import com.arbitr.cargoway.dto.rq.cargo.CargoOrderCreateRq;
import com.arbitr.cargoway.dto.rq.cargo.CargoOrderUpdateRq;
import com.arbitr.cargoway.dto.rq.cargo.FilterCargoRq;
import com.arbitr.cargoway.dto.rs.PaginationRs;
import com.arbitr.cargoway.dto.rs.cargo.CargoOrderRs;
import com.arbitr.cargoway.entity.Cargo;
import com.arbitr.cargoway.entity.CargoOrder;
import com.arbitr.cargoway.entity.Profile;
import com.arbitr.cargoway.entity.enums.CargoOrderStatus;
import com.arbitr.cargoway.exception.BadRequestException;
import com.arbitr.cargoway.exception.NotFoundException;
import com.arbitr.cargoway.exception.ResourceConflictException;
import com.arbitr.cargoway.mapper.CargoOrderMapper;
import com.arbitr.cargoway.repository.CargoOrderRepository;
import com.arbitr.cargoway.repository.CargoOrderResponseRepository;
import com.arbitr.cargoway.repository.CargoRepository;
import com.arbitr.cargoway.repository.specification.CargoSpecification;
import com.arbitr.cargoway.service.CargoOrderService;
import com.arbitr.cargoway.service.ProfileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CargoOrderServiceImpl implements CargoOrderService {
    private final ProfileService profileService;
    private final CargoOrderRepository cargoOrderRepository;
    private final CargoRepository cargoRepository;
    private final CargoOrderMapper cargoOrderMapper;
    private final CargoOrderResponseRepository cargoOrderResponseRepository;

    public PaginationRs<CargoOrderRs> getGeneralCargosByCategory(VisibilityCategory visibilityCategory,
                                                                 PaginationRq  paginationRq) {
        Profile currentProfile = profileService.getAuthenticatedProfile();

        Page<CargoOrder> generalCargosPage =
                cargoOrderRepository.findCargoOrdersByVisibilityIsInAndOwner_Id(visibilityCategory.getVisibleStatuses(),
                        currentProfile.getId(),
                        PageRequest.of(paginationRq.getPageNumber(), paginationRq.getPageSize())
                );

        List<CargoOrderRs> generalCargoOrderRs = generalCargosPage.getContent().stream()
                .map(cargoOrderMapper::toRsDto)
                .toList();

        return PaginationRs.of(
                generalCargoOrderRs,
                generalCargosPage.getNumber(),
                generalCargosPage.getSize(),
                generalCargosPage.getTotalPages()
        );
    }

    @Override
    public PaginationRs<CargoOrderRs> getCarrierGeneralByCategory(CarrierVisibilityCategory carrierVisibilityCategory,
                                                                  PaginationRq paginationRq) {
        Profile currentProfile = profileService.getAuthenticatedProfile();

        Page<CargoOrder> generalCargosPage = null;

        if (carrierVisibilityCategory.equals(CarrierVisibilityCategory.ACTIVE) ||
                carrierVisibilityCategory.equals(CarrierVisibilityCategory.HISTORY)) {
            generalCargosPage = cargoOrderRepository.findCargoOrdersByVisibilityIsInAndExecutor_Id(
                    carrierVisibilityCategory.getVisibleStatuses(),
                    currentProfile.getId(),
                    PageRequest.of(paginationRq.getPageNumber(), paginationRq.getPageSize()));
        }

        if (carrierVisibilityCategory.equals(CarrierVisibilityCategory.WAITING)) {
            generalCargosPage = cargoOrderRepository.findCargoOrdersByVisibilityIsInAndResponder_Id(
                    carrierVisibilityCategory.getVisibleStatuses(),
                    currentProfile.getId(),
                    PageRequest.of(paginationRq.getPageNumber(), paginationRq.getPageSize()));
        }

        if (generalCargosPage == null) {
            throw new BadRequestException("Указан несуществующий тип категории!");
        }

        List<CargoOrderRs> generalCargoOrderRs = generalCargosPage.getContent().stream()
                .map(cargoOrderMapper::toRsDto)
                .toList();

        return PaginationRs.of(
                generalCargoOrderRs,
                generalCargosPage.getNumber(),
                generalCargosPage.getSize(),
                generalCargosPage.getTotalPages()
        );
    }

    @Override
    public List<CargoOrderRs> getLastCargoOrder() {
        return cargoOrderRepository.findLast5CargoOrdersByVisibilityIsIn(Set.of(CargoOrderStatus.PUBLISHED,
                CargoOrderStatus.BIDDING)).stream()
                .map(cargoOrderMapper::toRsDto)
                .toList();
    }

    @Override
    public CargoOrderRs getCargoOrder(UUID cargoOrderId) {
        CargoOrder foundCargoOrder = cargoOrderRepository.findById(cargoOrderId).orElseThrow(
                () -> new NotFoundException("Заявка с id=%s не найдена!".formatted(cargoOrderId))
        );
        return cargoOrderMapper.toRsDto(foundCargoOrder);
    }

    @Override
    public CargoOrderRs createNewCargoOrder(CargoOrderCreateRq cargoOrderCreateRq) {
        Profile currentProfile = profileService.getAuthenticatedProfile();

        Cargo newCargo = cargoOrderMapper.toEntity(cargoOrderCreateRq);

        CargoOrder newCargoOrder = CargoOrder.builder()
                .cargo(newCargo)
                .visibility(CargoOrderStatus.DRAFT)
                .owner(currentProfile)
                .build();

        newCargo.setCargoOrder(newCargoOrder);
        cargoOrderRepository.save(newCargoOrder);

        return cargoOrderMapper.toRsDto(newCargoOrder);
    }

    @Override
    public CargoOrderRs publishCargoOrder(UUID cargoOrderId) {
        CargoOrder foundCargoOrder = this.getCargoOrderByIdAndCurrentProfile(cargoOrderId);

        if (!foundCargoOrder.getVisibility().equals(CargoOrderStatus.DRAFT)) {
            throw new ResourceConflictException("Ошибка при публикации записи! Id записи=%s".formatted(cargoOrderId));
        }

        foundCargoOrder.setVisibility(CargoOrderStatus.PUBLISHED);

        cargoOrderRepository.save(foundCargoOrder);
        return cargoOrderMapper.toRsDto(foundCargoOrder);
    }

    @Transactional
    @Override
    public CargoOrderRs draftCargoOrder(UUID cargoOrderId) {
        CargoOrder foundCargoOrder = this.getCargoOrderByIdAndCurrentProfile(cargoOrderId);

        if (!foundCargoOrder.getVisibility().equals(CargoOrderStatus.PUBLISHED) &&
                !foundCargoOrder.getVisibility().equals(CargoOrderStatus.BIDDING)) {
            throw new ResourceConflictException(
                    "Ошибка при снятии записи с публикации или отмене торгов! Id записи=%s".formatted(cargoOrderId)
            );
        }

        cargoOrderResponseRepository.deleteCargoOrderResponsesByCargoOrder_Id(cargoOrderId);
        cargoOrderResponseRepository.flush();
        foundCargoOrder.setVisibility(CargoOrderStatus.DRAFT);

        cargoOrderRepository.save(foundCargoOrder);
        return cargoOrderMapper.toRsDto(foundCargoOrder);
    }

    @Override
    public CargoOrderRs updateCargoOrder(UUID cargoOrderId, CargoOrderUpdateRq cargoOrderUpdateRq) {
        CargoOrder foundCargoOrder = this.getCargoOrderByIdAndCurrentProfile(cargoOrderId);

        if (!foundCargoOrder.getVisibility().equals(CargoOrderStatus.DRAFT)) {
            throw new ResourceConflictException(
                    "Запись о грузе невозможно обновить, так как запись не в статусе черновика! Id=%s"
                            .formatted(cargoOrderId)
            );
        }

        Cargo cargoDetails = foundCargoOrder.getCargo();

        if (cargoOrderUpdateRq.getName() != null) {
            cargoDetails.setName(cargoOrderUpdateRq.getName());
        }
        if (cargoOrderUpdateRq.getDescription() != null) {
            cargoDetails.setDescription(cargoOrderUpdateRq.getDescription());
        }
        if (cargoOrderUpdateRq.getWeight() != null) {
            cargoDetails.setWeight(cargoOrderUpdateRq.getWeight());
        }
        if (cargoOrderUpdateRq.getVolume() != null) {
            cargoDetails.setVolume(cargoOrderUpdateRq.getVolume());
        }
        if (cargoOrderUpdateRq.getLoadType() != null) {
            cargoDetails.setLoadType(cargoOrderUpdateRq.getLoadType());
        }
        if (cargoOrderUpdateRq.getUnloadType() != null) {
            cargoDetails.setUnloadType(cargoOrderUpdateRq.getUnloadType());
        }
        if (cargoOrderUpdateRq.getBodyType() != null) {
            cargoDetails.setBodyType(cargoOrderUpdateRq.getBodyType());
        }
        if (cargoOrderUpdateRq.getPrice() != null) {
            cargoDetails.setPrice(cargoOrderUpdateRq.getPrice());
        }
        if (cargoOrderUpdateRq.getTypePay() != null) {
            cargoDetails.setTypePay(cargoOrderUpdateRq.getTypePay());
        }
        if (cargoOrderUpdateRq.getReadyDate() != null) {
            cargoDetails.setReadyDate(cargoOrderUpdateRq.getReadyDate());
        }
        if (cargoOrderUpdateRq.getDeliveryDate() != null) {
            cargoDetails.setDeliveryDate(cargoOrderUpdateRq.getDeliveryDate());
        }

        if (cargoOrderUpdateRq.getDimensions() != null) {
            CargoDto.DimensionsDto newDimensions = cargoOrderUpdateRq.getDimensions();

            if (newDimensions.getLength() != null) {
                cargoDetails.setLength(newDimensions.getLength());
            }
            if (newDimensions.getWidth() != null) {
                cargoDetails.setWidth(newDimensions.getWidth());
            }
            if (newDimensions.getHeight() != null) {
                cargoDetails.setHeight(newDimensions.getHeight());
            }
        }

        if (cargoOrderUpdateRq.getRoute() != null) {
            CargoDto.RouteDto newRoute = cargoOrderUpdateRq.getRoute();

            if (newRoute.getFrom() != null) {
                cargoDetails.setFrom(newRoute.getFrom());
            }
            if (newRoute.getTo() != null) {
                cargoDetails.setTo(newRoute.getTo());
            }
        }

        foundCargoOrder.setOrderUpdatedAt(LocalDateTime.now());
        foundCargoOrder.setCargo(cargoDetails);
//        if (!cargoOrderUpdateRq.getPhotos().isEmpty()) {
//            cargoOrder.setPhotos(cargoOrderUpdateRq.getPhotos());
//        }
        cargoOrderRepository.save(foundCargoOrder);

        return cargoOrderMapper.toRsDto(foundCargoOrder);
    }

    @Override
    public CargoOrderRs endExecutionCargoOrder(UUID cargoOrderId) {
        CargoOrder existingCargoOrder = this.getCargoOrderById(cargoOrderId);

        existingCargoOrder.setEndExecution(LocalDateTime.now());
        cargoOrderRepository.save(existingCargoOrder);

        return cargoOrderMapper.toRsDto(existingCargoOrder);
    }

    @Override
    public CargoOrderRs confirmEndExecutionCargoOrder(UUID cargoOrderId) {
        CargoOrder existingCargoOrder = this.getCargoOrderByIdAndCurrentProfile(cargoOrderId);

        if (existingCargoOrder.getEndExecution() == null) {
            throw new ResourceConflictException(
                    "Перевозчик не завершил исполнение, поэтому успешно закрыть заказ невозможно! Id заказа=%s"
                            .formatted(cargoOrderId)
            );
        }

        this.setStatusToCargoOrder(existingCargoOrder, CargoOrderStatus.COMPLETED);
        existingCargoOrder.setEndExecution(LocalDateTime.now());

        cargoOrderRepository.save(existingCargoOrder);

        return cargoOrderMapper.toRsDto(existingCargoOrder);
    }

    @Transactional
    @Override
    public CargoOrderRs cancelExecutionCargoOrder(UUID cargoOrderId) {
        Profile currentProfile = profileService.getAuthenticatedProfile();

        CargoOrder exisitingCargoOrder = this.getCargoOrderById(cargoOrderId);

        if (!exisitingCargoOrder.getVisibility().equals(CargoOrderStatus.IN_PROGRESS)) {
            throw new ResourceConflictException("Невозможно отменить заказ, так как он не состоянии исполнения! Id=%s"
                    .formatted(cargoOrderId));
        }

        if (!exisitingCargoOrder.getExecutor().getId().equals(currentProfile.getId()) &&
                !exisitingCargoOrder.getOwner().getId().equals(currentProfile.getId())
        ) {
            throw new ResourceConflictException(
                    "Текущий профиль не может отменить заказ, так как не связан с ним! id заказа=%s".
                            formatted(cargoOrderId)
            );
        }

        this.setStatusToCargoOrder(exisitingCargoOrder, CargoOrderStatus.CANCELED);
        exisitingCargoOrder.setEndExecution(LocalDateTime.now());

        cargoOrderRepository.save(exisitingCargoOrder);

        return cargoOrderMapper.toRsDto(exisitingCargoOrder);
    }

    @Transactional
    @Override
    public void deleteCargoOrder(UUID cargoOrderId) {
        CargoOrder foundCargoOrder = this.getCargoOrderByIdAndCurrentProfile(cargoOrderId);

        if (foundCargoOrder.getVisibility().equals(CargoOrderStatus.IN_PROGRESS)) {
            throw new ResourceConflictException(
                    "Запись о грузе невозможно удалить, так как заказ находится в исполнении! Id=%s"
                            .formatted(cargoOrderId)
            );
        }

        cargoOrderRepository.delete(foundCargoOrder);
    }

    @Override
    public PaginationRs<CargoOrderRs> searchCargoOrders(FilterCargoRq filterCargoRq, PaginationRq paginationRq) {
        Set<CargoOrderStatus> allowedStatuses = Set.of(
                CargoOrderStatus.PUBLISHED,
                CargoOrderStatus.BIDDING
        );

        Specification<Cargo> specification = CargoSpecification.withFilter(filterCargoRq, allowedStatuses);
        Page<Cargo> filteredCargosPage = cargoRepository.findAll(specification,
                PageRequest.of(paginationRq.getPageNumber(), paginationRq.getPageSize()));

        List<CargoOrderRs> filteredCargoOrdersRs = filteredCargosPage.getContent().stream()
                .map(cargoOrderMapper::toRsDto)
                .toList();

        return PaginationRs.of(
                filteredCargoOrdersRs,
                filteredCargosPage.getNumber(),
                filteredCargosPage.getSize(),
                filteredCargosPage.getTotalPages()
        );
    }

    @Override
    public CargoOrder getCargoOrderById(UUID cargoOrderId) {
        return cargoOrderRepository.findById(cargoOrderId)
                .orElseThrow(
                        () -> new NotFoundException("Запись о грузе с id=%s не найдена!".formatted(cargoOrderId))
                );
    }

    @Override
    public CargoOrder getCargoOrderByIdAndCurrentProfile(UUID cargoOrderId) {
        Profile currentProfile = profileService.getAuthenticatedProfile();

        return cargoOrderRepository.findCargoOrderByIdAndOwner_Id(cargoOrderId, currentProfile.getId())
                .orElseThrow(
                        () -> new NotFoundException("Запись о грузе с id=%s не найдена!".formatted(cargoOrderId))
                );
    }

    @Override
    public void setStatusToCargoOrder(CargoOrder cargoOrder, CargoOrderStatus cargoOrderStatus) {
        if (!(cargoOrder.getVisibility().equals(cargoOrderStatus))) {
            cargoOrder.setVisibility(cargoOrderStatus);
        }
    }
}
