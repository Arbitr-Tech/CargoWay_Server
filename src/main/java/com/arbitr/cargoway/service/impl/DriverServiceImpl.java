package com.arbitr.cargoway.service.impl;

import com.arbitr.cargoway.dto.rq.PaginationRq;
import com.arbitr.cargoway.dto.rq.driver.DriverCreateRq;
import com.arbitr.cargoway.dto.rq.driver.DriverUpdateRq;
import com.arbitr.cargoway.dto.rs.driver.DriverRs;
import com.arbitr.cargoway.dto.rs.PaginationRs;
import com.arbitr.cargoway.dto.rs.driver.DriverShortInfoRs;
import com.arbitr.cargoway.entity.Driver;
import com.arbitr.cargoway.entity.Profile;
import com.arbitr.cargoway.exception.NotFoundException;
import com.arbitr.cargoway.mapper.DriverMapper;
import com.arbitr.cargoway.repository.DriverRepository;
import com.arbitr.cargoway.service.DriverService;
import com.arbitr.cargoway.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final ProfileService profileService;
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    // TODO: создание и обновление с фотографиями прав

    @Override
    public DriverRs createDriver(DriverCreateRq driverCreateRq) {
        Profile currentProfile = profileService.getAuthenticatedProfile();

        Driver newDriver = driverMapper.toEntity(driverCreateRq);
        newDriver.setProfile(currentProfile);
        driverRepository.save(newDriver);

        return driverMapper.toRsDto(newDriver);
    }

    @Override
    public PaginationRs<DriverRs> getCurrentProfileDrivers(PaginationRq paginationRq) {
        Profile currentProfile = profileService.getAuthenticatedProfile();

        Page<Driver> profileDrivers = driverRepository.findDriversByProfile_id(
                currentProfile.getId(),
                PageRequest.of(paginationRq.getPageNumber(), paginationRq.getPageSize())
        );

        List<DriverRs> driversRs = profileDrivers.getContent().stream()
                .map(driverMapper::toRsDto).toList();

        return PaginationRs.of(
                driversRs,
                profileDrivers.getNumber(),
                profileDrivers.getSize(),
                profileDrivers.getTotalPages()
        );
    }

    @Override
    public List<DriverShortInfoRs> getListCurrentProfileDrivers() {
        Profile currentProfile = profileService.getAuthenticatedProfile();

        List<Driver> drivers = driverRepository.findDriversByProfile_id(currentProfile.getId());

        return drivers.stream()
                .map(driverMapper::toShortRsDto)
                .toList();
    }

    @Override
    public DriverRs getDriver(UUID driverId) {
        Driver foundDriver = this.getDriverByIdAndCurrentProfile(driverId);
        return driverMapper.toRsDto(foundDriver);
    }

    @Override
    public Driver getDriverByIdAndCurrentProfile(UUID driverId) {
        Profile currentProfile = profileService.getAuthenticatedProfile();

        return driverRepository.findDriverByIdAndProfileId(driverId, currentProfile.getId()).orElseThrow(
                () -> new NotFoundException("Водитель с id=%s не был найден!".formatted(driverId))
        );
    }

    @Override
    public DriverRs updateDriver(UUID driverId, DriverUpdateRq driverUpdateRq) {
        Driver existingDriver = this.getDriverByIdAndCurrentProfile(driverId);

        if (driverUpdateRq.getFullName() != null) {
            existingDriver.setFullName(driverUpdateRq.getFullName());
        }
        if (driverUpdateRq.getLicenseCategory() != null) {
            existingDriver.setLicenseCategory(driverUpdateRq.getLicenseCategory());
        }
        if (driverUpdateRq.getLicenseNumber() != null) {
            existingDriver.setLicenseNumber(driverUpdateRq.getLicenseNumber());
        }
        if (driverUpdateRq.getIssueDate() != null) {
            existingDriver.setIssueDate(driverUpdateRq.getIssueDate());
        }
        if (driverUpdateRq.getExpirationDate() != null) {
            existingDriver.setExpirationDate(driverUpdateRq.getExpirationDate());
        }

        driverRepository.save(existingDriver);

        return driverMapper.toRsDto(existingDriver);
    }

    @Override
    public void deleteDriverById(UUID driverId) {
        Driver existingDriver = this.getDriverByIdAndCurrentProfile(driverId);
        driverRepository.delete(existingDriver);
    }
}
