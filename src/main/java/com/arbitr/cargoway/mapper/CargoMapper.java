package com.arbitr.cargoway.mapper;

import com.arbitr.cargoway.dto.rq.cargo.CargoCreateRq;
import com.arbitr.cargoway.dto.rq.cargo.CargoStatus;
import com.arbitr.cargoway.dto.rs.cargo.CargoDetailsRs;
import com.arbitr.cargoway.entity.Cargo;
import com.arbitr.cargoway.entity.enums.VisibilityStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface CargoMapper {
    @Mapping(source = "status", target = "visibility", qualifiedByName = "mapCargoStatus")
    Cargo buildCargoFrom(CargoCreateRq cargoCreateRq);

    @Named("mapCargoStatus")
    default VisibilityStatus mapCargoStatus(CargoStatus cargoStatus) {
        return VisibilityStatus.valueOf(cargoStatus.name());
    }

    CargoDetailsRs buildCargoDetailsRs(Cargo cargo);
}
