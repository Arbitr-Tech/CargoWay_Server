package com.arbitr.cargoway.service;

import com.arbitr.cargoway.dto.CargoCategoryDto;
import com.arbitr.cargoway.dto.VisibilityStatusDto;
import com.arbitr.cargoway.dto.rq.PaginationRq;
import com.arbitr.cargoway.dto.rq.cargo.CargoCreateRq;
import com.arbitr.cargoway.dto.rq.cargo.CargoUpdateRq;
import com.arbitr.cargoway.dto.rs.PaginationRs;
import com.arbitr.cargoway.dto.rs.cargo.CargoOrderRs;

import java.util.UUID;

public interface CargoService {
    PaginationRs<CargoOrderRs> getGeneralCargosByCategory(CargoCategoryDto cargoCategoryDto, PaginationRq  paginationRq);
    CargoOrderRs getCargoOrder(UUID cargoOrderId);
    CargoOrderRs createNewCargo(CargoCreateRq cargoCreateRq);
    CargoOrderRs updateCargo(UUID cargoId, CargoUpdateRq cargoUpdateRq);
}
