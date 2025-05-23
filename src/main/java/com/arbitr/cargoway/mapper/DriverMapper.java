package com.arbitr.cargoway.mapper;

import com.arbitr.cargoway.dto.general.DriverDto;
import com.arbitr.cargoway.dto.rs.driver.DriverRs;
import com.arbitr.cargoway.dto.rs.driver.DriverShortInfoRs;
import com.arbitr.cargoway.entity.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ImageMapper.class})
public interface DriverMapper {
    Driver toEntity(DriverDto driverDto);

    @Mapping(source = "licenseImages", target = "images")
    DriverRs toRsDto(Driver driver);
    DriverShortInfoRs toShortRsDto(Driver driver);
}
