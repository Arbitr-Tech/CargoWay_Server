package com.arbitr.cargoway.controller;

import com.arbitr.cargoway.dto.rq.PaginationRq;
import com.arbitr.cargoway.dto.rq.driver.DriverCreateRq;
import com.arbitr.cargoway.dto.rq.driver.DriverUpdateRq;
import com.arbitr.cargoway.dto.rs.driver.DriverRs;
import com.arbitr.cargoway.dto.rs.PaginationRs;
import com.arbitr.cargoway.dto.rs.driver.DriverShortInfoRs;
import com.arbitr.cargoway.service.DriverService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/drivers/")
@RequiredArgsConstructor
@Tag(name = "Управление водителями", description = "Управление водителями текущего перевозчика")
public class DriverController {
    private final DriverService driverService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverRs createDriver(@RequestBody @Valid DriverCreateRq driverCreateRq) {
        return driverService.createDriver(driverCreateRq);
    }

    @GetMapping
    public PaginationRs<DriverRs> getDrivers(@ModelAttribute PaginationRq paginationRq) {
        return driverService.getCurrentProfileDrivers(paginationRq);
    }

    @GetMapping("list/")
    public List<DriverShortInfoRs> getListDrivers() {
        return driverService.getListCurrentProfileDrivers();
    }

    @GetMapping("{driverId}/")
    public DriverRs getDriverById(@PathVariable("driverId") UUID driverId) {
        return driverService.getDriver(driverId);
    }

    @PatchMapping("{driverId}/")
    public DriverRs updateDriver(@PathVariable("driverId") UUID driverId,
                                 @RequestBody DriverUpdateRq driverUpdateRq) {
        return driverService.updateDriver(driverId, driverUpdateRq);
    }

    @DeleteMapping("{driverId}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void driverDelete(@PathVariable("driverId") UUID driverId) {
        driverService.deleteDriverById(driverId);
    }
}
