package com.arbitr.cargoway.mapper;

import com.arbitr.cargoway.dto.general.DriverDto;
import com.arbitr.cargoway.dto.rs.driver.DriverRs;
import com.arbitr.cargoway.dto.rs.driver.DriverShortInfoRs;
import com.arbitr.cargoway.entity.Driver;
import org.mapstruct.Mapper;

@Mapper
public interface DriverMapper {
    Driver toEntity(DriverDto driverDto);
    DriverRs toRsDto(Driver driver);
    DriverShortInfoRs toShortRsDto(Driver driver);
}
