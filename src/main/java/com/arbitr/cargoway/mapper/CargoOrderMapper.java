package com.arbitr.cargoway.mapper;

import com.arbitr.cargoway.dto.general.cargo.CargoOrderStatusDto;
import com.arbitr.cargoway.dto.general.cargo.CargoDto;
import com.arbitr.cargoway.dto.rs.cargo.CargoOrderResponseDto;
import com.arbitr.cargoway.dto.rs.cargo.CargoOrderRs;
import com.arbitr.cargoway.entity.Cargo;
import com.arbitr.cargoway.entity.CargoOrder;
import com.arbitr.cargoway.entity.CargoOrderResponse;
import com.arbitr.cargoway.entity.enums.CargoOrderStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {ProfileMapper.class, TransportMapper.class})
public interface CargoOrderMapper {
    @Mapping(source = "responses", target = "responses", qualifiedByName = "toCargoOrderResponseDto")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "orderCreatedAt", target = "orderCreatedAt")
    @Mapping(source = "orderUpdatedAt", target = "orderUpdatedAt")
    @Mapping(source = "startExecution", target = "startExecution")
    @Mapping(source = "endExecution", target = "endExecution")
    @Mapping(source = "visibility", target = "visibilityStatus", qualifiedByName = "mapVisibilityStatus")
    @Mapping(source = "cargo", target = "cargo", qualifiedByName = "toCargoDto")
    @Mapping(source = "executor", target = "executor", qualifiedByName = "toProfileShortDto")
    @Mapping(source = "executorTransport", target = "executorTransport")
    @Mapping(source = "owner", target = "owner", qualifiedByName = "toProfileShortDto")
    CargoOrderRs toRsDto(CargoOrder cargoOrder);

    @Mapping(source = "cargoOrder.responses", target = "responses", qualifiedByName = "toCargoOrderResponseDto")
    @Mapping(source = "cargoOrder.id", target = "id")
    @Mapping(source = "cargoOrder.orderCreatedAt", target = "orderCreatedAt")
    @Mapping(source = "cargoOrder.orderUpdatedAt", target = "orderUpdatedAt")
    @Mapping(source = "cargoOrder.startExecution", target = "startExecution")
    @Mapping(source = "cargoOrder.endExecution", target = "endExecution")
    @Mapping(source = "cargoOrder.visibility", target = "visibilityStatus", qualifiedByName = "mapVisibilityStatus")
    @Mapping(source = "cargo", target = "cargo", qualifiedByName = "toCargoDto")
    @Mapping(source = "cargoOrder.executor", target = "executor", qualifiedByName = "toProfileShortDto")
    @Mapping(source = "cargoOrder.executorTransport", target = "executorTransport")
    @Mapping(source = "cargoOrder.owner", target = "owner", qualifiedByName = "toProfileShortDto")
    CargoOrderRs toRsDto(Cargo cargo);

    @Named("toCargoDto")
    default CargoDto toCargoDto(Cargo cargo) {
        if (cargo == null) {
            return null;
        }
        return CargoDto.builder()
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
                .dimensions(toDimensionsDto(cargo))
                .route(toRouteDto(cargo))
                .build();
    }

    @Named("toDimensionsDto")
    default CargoDto.DimensionsDto toDimensionsDto(Cargo cargo) {
        if (cargo == null) {
            return null;
        }
        return CargoDto.DimensionsDto.builder()
                .width(cargo.getWidth())
                .height(cargo.getHeight())
                .length(cargo.getLength())
                .build();
    }

    @Named("toRouteDto")
    default CargoDto.RouteDto toRouteDto(Cargo cargo) {
        if (cargo == null) {
            return null;
        }
        return CargoDto.RouteDto.builder()
                .from(cargo.getFrom())
                .to(cargo.getTo())
                .build();
    }

    @Named("mapVisibilityStatus")
    default CargoOrderStatusDto mapVisibilityStatus(CargoOrderStatus visibility) {
        return visibility != null ? CargoOrderStatusDto.valueOf(visibility.name()) : null;
    }

    @Named("toCargoOrderResponseDto")
    @Mapping(source = "responder", target = "responderDetails", qualifiedByName = "toProfileShortDto")
    @Mapping(source = "transport", target = "transportDetails")
    CargoOrderResponseDto toCargoOrderResponseDto(CargoOrderResponse cargoOrderResponse);

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
