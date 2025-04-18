package com.arbitr.cargoway.service.impl;

import com.arbitr.cargoway.dto.CargoCategoryDto;
import com.arbitr.cargoway.dto.VisibilityStatusDto;
import com.arbitr.cargoway.dto.rq.PaginationRq;
import com.arbitr.cargoway.dto.rq.cargo.CargoCreateRq;
import com.arbitr.cargoway.dto.rq.cargo.CargoUpdateRq;
import com.arbitr.cargoway.dto.rs.PaginationRs;
import com.arbitr.cargoway.dto.rs.cargo.CargoOrderRs;
import com.arbitr.cargoway.entity.Cargo;
import com.arbitr.cargoway.entity.CargoOrder;
import com.arbitr.cargoway.entity.enums.VisibilityStatus;
import com.arbitr.cargoway.entity.security.User;
import com.arbitr.cargoway.exception.NotFoundException;
import com.arbitr.cargoway.mapper.CargoOrderMapper;
import com.arbitr.cargoway.repository.CargoOrderRepository;
import com.arbitr.cargoway.service.AuthService;
import com.arbitr.cargoway.service.CargoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
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
    public CargoOrderRs createNewCargo(CargoCreateRq cargoCreateRq) {
        User currentUser = authService.getAuthenticatedUser();

        Cargo newCargo = cargoOrderMapper.toEntity(cargoCreateRq);

        CargoOrder newCargoOrder = CargoOrder.builder()
                .cargo(newCargo)
                .visibility(VisibilityStatus.DRAFT)
                .profile(currentUser.getProfile())
                .build();

        cargoOrderRepository.save(newCargoOrder);

        return cargoOrderMapper.toRsDto(newCargoOrder);
    }

    @Override
    public CargoOrderRs updateCargo(UUID cargoId, CargoUpdateRq cargoUpdateRq) {
        return null;
    }
}
