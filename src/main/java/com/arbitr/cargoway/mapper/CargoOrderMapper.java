package com.arbitr.cargoway.mapper;

import com.arbitr.cargoway.dto.VisibilityStatusDto;
import com.arbitr.cargoway.dto.rq.cargo.CargoCreateRq;
import com.arbitr.cargoway.dto.rs.cargo.CargoOrderRs;
import com.arbitr.cargoway.entity.Cargo;
import com.arbitr.cargoway.entity.CargoOrder;
import org.mapstruct.Mapper;

@Mapper
public interface CargoOrderMapper {
    default CargoOrderRs toRsDto(CargoOrder cargoOrder) {
        Cargo cargo = cargoOrder.getCargo();
        Cargo.Dimensions cargoDimensions = cargo.getDimensions();
        Cargo.Route cargoRoute = cargo.getRoute();

        CargoOrderRs.Dimensions cargoOrderDimensions = CargoOrderRs.Dimensions.builder()
                .width(cargoDimensions.getWidth())
                .height(cargoDimensions.getHeight())
                .length(cargoDimensions.getLength())
                .build();

        CargoOrderRs.Route cargoOrderRoute = CargoOrderRs.Route.builder()
                .from(cargoRoute.getFrom())
                .to(cargoRoute.getTo())
                .build();

        CargoOrderRs.CargoDetails cargoDetails = CargoOrderRs.CargoDetails.builder()
                .name(cargo.getName())
                .description(cargo.getDescription())
                .weight(cargo.getWeight())
                .volume(cargo.getVolume())
                .loadType(cargo.getLoadType())
                .unloadType(cargo.getUnloadType())
                .bodyType(cargo.getBodyType())
                .price(cargo.getPrice())
                .typePay(cargo.getTypePay())
                .readyDate(cargo.getReadyDate())
                .deliveryDate(cargo.getDeliveryDate())
                .price(cargo.getPrice())
                .dimensions(cargoOrderDimensions)
                .route(cargoOrderRoute)
                .build();

        CargoOrderRs cargoOrderRs = CargoOrderRs.builder()
                .id(cargoOrder.getId())
                .orderCreatedAt(cargoOrder.getOrderCreatedAt())
                .orderUpdatedAt(cargoOrder.getOrderUpdatedAt())
                .startExecution(cargoOrder.getStartExecution())
                .endExecution(cargoOrder.getEndExecution())
                .visibility(VisibilityStatusDto.valueOf(cargoOrder.getVisibility().name()))
                .cargo(cargoDetails)
                .build();

        return cargoOrderRs;
    }

    default Cargo toEntity(CargoCreateRq cargoCreateRq) {
        CargoCreateRq.Dimensions cargoCreateDimensions = cargoCreateRq.getDimensions();
        CargoCreateRq.Route cargoCreateRoute = cargoCreateRq.getRoute();

        Cargo.Dimensions newCargoDimensions = Cargo.Dimensions.builder()
                .height(cargoCreateDimensions.getHeight())
                .width(cargoCreateDimensions.getWidth())
                .length(cargoCreateDimensions.getLength())
                .build();

        Cargo.Route newCargoRoute = Cargo.Route.builder()
                .from(cargoCreateRoute.getFrom())
                .to(cargoCreateRoute.getTo())
                .build();

        Cargo newCargo = Cargo.builder()
                .name(cargoCreateRq.getName())
                .description(cargoCreateRq.getDescription())
                .weight(cargoCreateRq.getWeight())
                .volume(cargoCreateRq.getVolume())
                .loadType(cargoCreateRq.getLoadType())
                .unloadType(cargoCreateRq.getUnloadType())
                .bodyType(cargoCreateRq.getBodyType())
                .price(cargoCreateRq.getPrice())
                .typePay(cargoCreateRq.getTypePay())
                .readyDate(cargoCreateRq.getReadyDate())
                .deliveryDate(cargoCreateRq.getDeliveryDate())
                .dimensions(newCargoDimensions)
                .route(newCargoRoute)
                .build();

        return newCargo;
    }
}
