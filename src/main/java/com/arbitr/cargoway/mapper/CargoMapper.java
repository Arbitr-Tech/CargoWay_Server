package com.arbitr.cargoway.mapper;

import com.arbitr.cargoway.dto.rq.cargo.CargoCreateRq;
import com.arbitr.cargoway.dto.rs.cargo.CargoDetailsRs;
import com.arbitr.cargoway.entity.Cargo;
import org.mapstruct.Mapper;

@Mapper
public interface CargoMapper {
    Cargo buildCargoFrom(CargoCreateRq cargoCreateRq);

    CargoDetailsRs buildCargoDetailsRs(Cargo cargo);
}
