package com.arbitr.cargoway.service;

import com.arbitr.cargoway.dto.general.cargo.CarrierVisibilityCategory;
import com.arbitr.cargoway.dto.general.cargo.VisibilityCategory;
import com.arbitr.cargoway.dto.rq.PaginationRq;
import com.arbitr.cargoway.dto.rq.cargo.CargoOrderCreateRq;
import com.arbitr.cargoway.dto.rq.cargo.CargoOrderUpdateRq;
import com.arbitr.cargoway.dto.rq.cargo.FilterCargoRq;
import com.arbitr.cargoway.dto.rs.PaginationRs;
import com.arbitr.cargoway.dto.rs.cargo.CargoOrderRs;
import com.arbitr.cargoway.entity.CargoOrder;
import com.arbitr.cargoway.entity.enums.CargoOrderStatus;

import java.util.List;
import java.util.UUID;

public interface CargoOrderService {
    PaginationRs<CargoOrderRs> getGeneralCargosByCategory(VisibilityCategory visibilityCategory,
                                                          PaginationRq  paginationRq);
    PaginationRs<CargoOrderRs> getCarrierGeneralByCategory(CarrierVisibilityCategory carrierVisibilityCategory,
                                                           PaginationRq paginationRq);
    List<CargoOrderRs> getLastCargoOrder();
    CargoOrderRs getCargoOrder(UUID cargoOrderId);
    CargoOrderRs createNewCargoOrder(CargoOrderCreateRq cargoOrderCreateRq);
    CargoOrderRs publishCargoOrder(UUID cargoOrderId);
    CargoOrderRs draftCargoOrder(UUID cargoOrderId);
    CargoOrderRs updateCargoOrder(UUID cargoOrderId, CargoOrderUpdateRq cargoOrderUpdateRq);
    CargoOrderRs endExecutionCargoOrder(UUID cargoOrderId);
    CargoOrderRs confirmEndExecutionCargoOrder(UUID cargoOrderId);
    CargoOrderRs cancelExecutionCargoOrder(UUID cargoOrderId);
    void deleteCargoOrder(UUID cargoOrderId);
    PaginationRs<CargoOrderRs> searchCargoOrders(FilterCargoRq filterCargoRq, PaginationRq paginationRq);
    CargoOrder getCargoOrderById(UUID cargoOrderId);
    CargoOrder getCargoOrderByIdAndCurrentProfile(UUID cargoOrderId);
    void setStatusToCargoOrder(CargoOrder cargoOrder, CargoOrderStatus cargoOrderStatus);
}
