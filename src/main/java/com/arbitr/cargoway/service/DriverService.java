package com.arbitr.cargoway.service;

import com.arbitr.cargoway.dto.rq.PaginationRq;
import com.arbitr.cargoway.dto.rq.driver.DriverCreateRq;
import com.arbitr.cargoway.dto.rq.driver.DriverUpdateRq;
import com.arbitr.cargoway.dto.rs.driver.DriverRs;
import com.arbitr.cargoway.dto.rs.PaginationRs;
import com.arbitr.cargoway.dto.rs.driver.DriverShortInfoRs;
import com.arbitr.cargoway.entity.Driver;

import java.util.List;
import java.util.UUID;

public interface DriverService {
    DriverRs createDriver(DriverCreateRq driverCreateRq);
    PaginationRs<DriverRs> getCurrentProfileDrivers(PaginationRq paginationRq);
    List<DriverShortInfoRs> getListCurrentProfileDrivers();
    DriverRs getDriver(UUID driverId);
    Driver getDriverByIdAndCurrentProfile(UUID driverId);
    DriverRs updateDriver(UUID driverId, DriverUpdateRq driverUpdateRq);
    void deleteDriverById(UUID driverId);
}
