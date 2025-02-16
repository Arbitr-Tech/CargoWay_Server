package com.arbitr.cargoway.repository;

import com.arbitr.cargoway.entity.Transport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransportRepository extends JpaRepository<Transport, UUID> {
}
