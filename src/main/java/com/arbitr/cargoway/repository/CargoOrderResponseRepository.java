package com.arbitr.cargoway.repository;

import com.arbitr.cargoway.entity.CargoOrderResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CargoOrderResponseRepository extends JpaRepository<CargoOrderResponse, UUID> {
}
