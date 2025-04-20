package com.arbitr.cargoway.service.impl;

import com.arbitr.cargoway.dto.general.cargo.CargoCategoryDto;
import com.arbitr.cargoway.dto.general.cargo.CargoDto;
import com.arbitr.cargoway.dto.rq.PaginationRq;
import com.arbitr.cargoway.dto.rq.cargo.CargoOrderCreateRq;
import com.arbitr.cargoway.dto.rq.cargo.CargoOrderUpdateRq;
import com.arbitr.cargoway.dto.rs.PaginationRs;
import com.arbitr.cargoway.dto.rs.cargo.CargoOrderRs;
import com.arbitr.cargoway.entity.Cargo;
import com.arbitr.cargoway.entity.CargoOrder;
import com.arbitr.cargoway.entity.enums.VisibilityStatus;
import com.arbitr.cargoway.entity.security.User;
import com.arbitr.cargoway.exception.NotFoundException;
import com.arbitr.cargoway.exception.ResourceConflictException;
import com.arbitr.cargoway.mapper.CargoOrderMapper;
import com.arbitr.cargoway.repository.CargoOrderRepository;
import com.arbitr.cargoway.service.AuthService;
import com.arbitr.cargoway.service.CargoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CargoServiceImpl implements CargoService {
    private final AuthService authService;
    private final CargoOrderRepository cargoOrderRepository;
    private final CargoOrderMapper cargoOrderMapper;

    public PaginationRs<CargoOrderRs> getGeneralCargosByCategory(CargoCategoryDto cargoCategoryDto, PaginationRq  paginationRq) {
        User currentUser = authService.getAuthenticatedUser();

        Page<CargoOrder> generalCargosPage =
                cargoOrderRepository.findCargoOrdersByVisibilityIsInAndProfile_Id(cargoCategoryDto.getVisibleStatuses(),
                currentUser.getProfile().getId(),
                PageRequest.of(paginationRq.getPageNumber(), paginationRq.getPageSize())
                );

        List<CargoOrderRs> generalCargoOrderRs = generalCargosPage.getContent().stream()
                .map(cargoOrderMapper::toRsDto)
                .toList();

        return PaginationRs.<CargoOrderRs>builder()
                .content(generalCargoOrderRs)
                .pageNumber(generalCargosPage.getNumber())
                .pageSize(generalCargosPage.getSize())
                .totalPages(generalCargosPage.getTotalPages())
                .build();
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
        User currentUser = authService.getAuthenticatedUser();

        Cargo newCargo = cargoOrderMapper.toEntity(cargoOrderCreateRq);

        CargoOrder newCargoOrder = CargoOrder.builder()
                .cargo(newCargo)
                .visibility(VisibilityStatus.DRAFT)
                .profile(currentUser.getProfile())
                .build();

        cargoOrderRepository.save(newCargoOrder);

        return cargoOrderMapper.toRsDto(newCargoOrder);
    }

    @Override
    public CargoOrderRs publishCargoOrder(UUID cargoOrderId) {
        return changeInternalVisibilityStatus(cargoOrderId, VisibilityStatus.DRAFT, VisibilityStatus.PUBLISHED);
    }

    @Override
    public CargoOrderRs draftCargoOrder(UUID cargoOrderId) {
        return changeInternalVisibilityStatus(cargoOrderId, VisibilityStatus.PUBLISHED, VisibilityStatus.DRAFT);
    }

    private CargoOrderRs changeInternalVisibilityStatus(UUID cargoOrderId, VisibilityStatus currentStatus,
                                                        VisibilityStatus newStatus) {
        CargoOrder foundCargoOrder = this.getCargoOrderById(cargoOrderId);

        if (!foundCargoOrder.getVisibility().equals(currentStatus)) {
            throw new ResourceConflictException(
                    "Ошибка при изменении статуса записи с %s на %s! Id=%s"
                            .formatted(currentStatus, newStatus, cargoOrderId)
            );
        }

        foundCargoOrder.setVisibility(newStatus);

        cargoOrderRepository.save(foundCargoOrder);
        return cargoOrderMapper.toRsDto(foundCargoOrder);
    }

    @Override
    public CargoOrderRs updateCargoOrder(UUID cargoOrderId, CargoOrderUpdateRq cargoOrderUpdateRq) {
        CargoOrder foundCargoOrder = this.getCargoOrderById(cargoOrderId);

        if (!foundCargoOrder.getVisibility().equals(VisibilityStatus.DRAFT)) {
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
            CargoDto.DimensionsDto dimensions = cargoOrderUpdateRq.getDimensions();
            Cargo.Dimensions cargoDimensions = cargoDetails.getDimensions();

            if (dimensions.getLength() != null) {
                cargoDimensions.setLength(dimensions.getLength());
            }
            if (dimensions.getWidth() != null) {
                cargoDimensions.setWidth(dimensions.getWidth());
            }
            if (dimensions.getHeight() != null) {
                cargoDimensions.setHeight(dimensions.getHeight());
            }
            cargoDetails.setDimensions(cargoDimensions);
        }

        if (cargoOrderUpdateRq.getRoute() != null) {
            CargoDto.RouteDto route = cargoOrderUpdateRq.getRoute();
            Cargo.Route cargoRoute = cargoDetails.getRoute();

            if (route.getFrom() != null) {
                cargoRoute.setFrom(route.getFrom());
            }
            if (route.getTo() != null) {
                cargoRoute.setTo(route.getTo());
            }
            cargoDetails.setRoute(cargoRoute);
        }

        foundCargoOrder.setCargo(cargoDetails);
//        if (!cargoOrderUpdateRq.getPhotos().isEmpty()) {
//            cargoOrder.setPhotos(cargoOrderUpdateRq.getPhotos());
//        }
        cargoOrderRepository.save(foundCargoOrder);

        return cargoOrderMapper.toRsDto(foundCargoOrder);
    }

    @Override
    public CargoOrder getCargoOrderById(UUID cargoOrderId) {
        return cargoOrderRepository.findById(cargoOrderId)
                .orElseThrow(
                        () -> new NotFoundException("Запись о грузе с id=%s не найдена!".formatted(cargoOrderId))
                );
    }
}
