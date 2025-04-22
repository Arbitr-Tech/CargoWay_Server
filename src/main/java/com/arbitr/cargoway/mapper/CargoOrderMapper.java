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
        return generalRsDto(cargo, cargoOrder);
    }

    default CargoOrderRs toRsDto(Cargo cargo) {
        CargoOrder cargoOrder = cargo.getCargoOrder();
        return generalRsDto(cargo, cargoOrder);
    }

    default CargoOrderRs generalRsDto(Cargo cargo, CargoOrder cargoOrder) {
        CargoOrderRs.Dimensions cargoOrderDimensions = CargoOrderRs.Dimensions.builder()
                .width(cargo.getWidth())
                .height(cargo.getHeight())
                .length(cargo.getLength())
                .build();

        CargoOrderRs.Route cargoOrderRoute = CargoOrderRs.Route.builder()
                .from(cargo.getFrom())
                .to(cargo.getTo())
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
                .dimensions(cargoOrderDimensions)
                .route(cargoOrderRoute)
                .build();

        return CargoOrderRs.builder()
                .id(cargoOrder.getId())
                .orderCreatedAt(cargoOrder.getOrderCreatedAt())
                .orderUpdatedAt(cargoOrder.getOrderUpdatedAt())
                .startExecution(cargoOrder.getStartExecution())
                .endExecution(cargoOrder.getEndExecution())
                .visibilityStatus(VisibilityStatusDto.valueOf(cargoOrder.getVisibility().name()))
                .cargo(cargoDetails)
                .build();
    }

    default Cargo toEntity(CargoDto cargoDetails) {
        CargoDto.DimensionsDto cargoDimensionsDetails = cargoDetails.getDimensions();
        CargoDto.RouteDto cargoRouteDetails = cargoDetails.getRoute();

        return Cargo.builder()
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
                .from(cargoRouteDetails.getFrom())
                .to(cargoRouteDetails.getTo())
                .height(cargoDimensionsDetails.getHeight())
                .width(cargoDimensionsDetails.getWidth())
                .length(cargoDimensionsDetails.getLength())
                .build();
    }
}
