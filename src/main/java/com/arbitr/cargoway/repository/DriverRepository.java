package com.arbitr.cargoway.repository;

import com.arbitr.cargoway.entity.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DriverRepository extends JpaRepository<Driver, UUID> {
    Optional<Driver> findDriverByIdAndProfileId(UUID id, UUID profileId);
    Page<Driver> findDriversByProfile_id(UUID profileId, Pageable pageable);
    List<Driver> findDriversByProfile_id(UUID profileId);
}
