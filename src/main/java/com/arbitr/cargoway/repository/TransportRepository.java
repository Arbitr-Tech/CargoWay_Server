package com.arbitr.cargoway.repository;

import com.arbitr.cargoway.entity.Transport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TransportRepository extends JpaRepository<Transport, UUID> {
    Optional<Transport> findTransportByIdAndProfileId(UUID id, UUID profileId);
    Page<Transport> findTransportsByProfile_Id(UUID profileId, Pageable pageable);
}
