package com.arbitr.cargoway.service;

import java.util.UUID;

public interface CargoOrderResponseService {
    void makeResponse(UUID cargoOrderId, UUID transportId);
}
