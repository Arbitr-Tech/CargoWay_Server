package com.arbitr.cargoway.mapper;

import com.arbitr.cargoway.dto.general.cargo.VisibilityStatusDto;
import com.arbitr.cargoway.dto.general.cargo.CargoDto;
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
                .visibilityStatus(VisibilityStatusDto.valueOf(cargoOrder.getVisibility().name()))
                .cargo(cargoDetails)
                .build();

        return cargoOrderRs;
    }

    default Cargo toEntity(CargoDto cargoDetails) {
        CargoDto.DimensionsDto cargoCreateDimensions = cargoDetails.getDimensions();
        CargoDto.RouteDto cargoCreateRoute = cargoDetails.getRoute();

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
                .name(cargoDetails.getName())
                .description(cargoDetails.getDescription())
                .weight(cargoDetails.getWeight())
                .volume(cargoDetails.getVolume())
                .loadType(cargoDetails.getLoadType())
                .unloadType(cargoDetails.getUnloadType())
                .bodyType(cargoDetails.getBodyType())
                .price(cargoDetails.getPrice())
                .typePay(cargoDetails.getTypePay())
                .readyDate(cargoDetails.getReadyDate())
                .deliveryDate(cargoDetails.getDeliveryDate())
                .dimensions(newCargoDimensions)
                .route(newCargoRoute)
                .build();

        return newCargo;
    }
}
