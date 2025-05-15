package com.arbitr.cargoway.repository;

import com.arbitr.cargoway.entity.CargoOrderResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CargoOrderResponseRepository extends JpaRepository<CargoOrderResponse, UUID> {
    Optional<CargoOrderResponse> findCargoOrderResponseByIdAndCargoOrder_Id(UUID responseId, UUID cargoOrderId);
    List<CargoOrderResponse> findCargoOrderResponsesByCargoOrder_IdAndResponder_Id(UUID cargoOrderId, UUID responderId);
    Optional<CargoOrderResponse> findCargoOrderResponseByCargoOrder_IdAndResponder_Id(UUID cargoOrderId, UUID responderId);

    @Modifying
    void deleteCargoOrderResponsesByCargoOrder_Id(UUID cargoOrderId);
}
