package com.arbitr.cargoway.service;

import com.arbitr.cargoway.dto.rq.cargo.CargoCreateRq;
import com.arbitr.cargoway.dto.rq.cargo.FilterCargoRq;
import com.arbitr.cargoway.dto.rs.cargo.CargoDetailsRs;

import java.util.List;
import java.util.UUID;

public interface CargoService {
    CargoDetailsRs createNewCargo(CargoCreateRq cargoCreateRq);
    CargoDetailsRs getCargo(UUID cargoId);
    List<CargoDetailsRs> searchCargos(FilterCargoRq filterCargoRq);
    List<CargoDetailsRs> getLastCargos(int number);
    CargoDetailsRs updateCargo(UUID cargoId, CargoCreateRq cargoCreateRq);
    CargoDetailsRs changeCargoGlobalVisibility(UUID cargoId, String status);
    void deleteCargo(UUID cargoId);
}
