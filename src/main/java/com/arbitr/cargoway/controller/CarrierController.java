package com.arbitr.cargoway.controller;

import com.arbitr.cargoway.dto.general.cargo.CarrierVisibilityCategory;
import com.arbitr.cargoway.dto.general.cargo.VisibilityCategory;
import com.arbitr.cargoway.dto.rq.PaginationRq;
import com.arbitr.cargoway.dto.rq.cargo.FilterCargoRq;
import com.arbitr.cargoway.dto.rs.PaginationRs;
import com.arbitr.cargoway.dto.rs.cargo.CargoOrderRs;
import com.arbitr.cargoway.service.CargoOrderResponseService;
import com.arbitr.cargoway.service.CargoOrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cargos/")
@RequiredArgsConstructor
@Tag(name = "Carrier", description = "Управление записями о грузах")
public class CarrierController {
    private final CargoOrderService cargoOrderService;
    private final CargoOrderResponseService cargoOrderResponseService;

    @PostMapping("search/")
    public PaginationRs<CargoOrderRs> searchCargoOrder(@RequestBody FilterCargoRq filterCargoRq,
                                                       @ModelAttribute PaginationRq paginationRq) {
        return cargoOrderService.searchCargoOrders(filterCargoRq, paginationRq);
    }

    @GetMapping("carrier/general/")
    public PaginationRs<CargoOrderRs> getInternalCargos(@RequestParam CarrierVisibilityCategory cargoCategory,
                                                        @ModelAttribute PaginationRq paginationRq) {
        return cargoOrderService.getCarrierGeneralByCategory(cargoCategory, paginationRq);
    }

    @PostMapping("{cargoOrderId}/transport/{transportId}/response/")
    public void makeCargoOrderResponse(@PathVariable("cargoOrderId") UUID cargoOrderId,
                                       @PathVariable("transportId") UUID transportId) {
        cargoOrderResponseService.makeResponse(cargoOrderId, transportId);
    }

    @PostMapping("/{cargoOrderId}/response/cancel/")
    public void cancelCargoOrderResponse(@PathVariable("cargoOrderId") UUID cargoOrderId) {
        cargoOrderResponseService.cancelResponse(cargoOrderId);
    }

    @PostMapping("/{cargoOrderId}/endExecution/")
    public CargoOrderRs endExecutionCargoOrder(@PathVariable("cargoOrderId") UUID cargoOrderId) {
        return cargoOrderService.endExecutionCargoOrder(cargoOrderId);
    }
}
