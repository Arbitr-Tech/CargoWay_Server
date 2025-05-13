package com.arbitr.cargoway.service;

import com.arbitr.cargoway.dto.rs.cargo.CargoOrderRs;

import java.util.UUID;

public interface CargoOrderResponseService {
    void makeResponse(UUID cargoOrderId, UUID transportId);
    void cancelResponse(UUID cargoOrderId);
    CargoOrderRs startExecutionCargoOrder(UUID cargoOrderId, UUID responseId);
}
