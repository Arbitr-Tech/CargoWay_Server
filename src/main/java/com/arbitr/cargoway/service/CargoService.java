package com.arbitr.cargoway.service;

import com.arbitr.cargoway.dto.general.cargo.CargoCategoryDto;
import com.arbitr.cargoway.dto.rq.PaginationRq;
import com.arbitr.cargoway.dto.rq.cargo.CargoOrderCreateRq;
import com.arbitr.cargoway.dto.rq.cargo.CargoOrderUpdateRq;
import com.arbitr.cargoway.dto.rs.PaginationRs;
import com.arbitr.cargoway.dto.rs.cargo.CargoOrderRs;
import com.arbitr.cargoway.entity.CargoOrder;

import java.util.UUID;

public interface CargoService {
    PaginationRs<CargoOrderRs> getGeneralCargosByCategory(CargoCategoryDto cargoCategoryDto, PaginationRq  paginationRq);
    CargoOrderRs getCargoOrder(UUID cargoOrderId);
    CargoOrderRs createNewCargoOrder(CargoOrderCreateRq cargoOrderCreateRq);
    CargoOrderRs publishCargoOrder(UUID cargoOrderId);
    CargoOrderRs draftCargoOrder(UUID cargoOrderId);
    CargoOrderRs updateCargoOrder(UUID cargoOrderId, CargoOrderUpdateRq cargoOrderUpdateRq);
    void deleteCargoOrder(UUID cargoOrderId);
    CargoOrder getCargoOrderById(UUID cargoOrderId);
}
