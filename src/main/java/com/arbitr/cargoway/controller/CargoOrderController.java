package com.arbitr.cargoway.controller;

import com.arbitr.cargoway.dto.general.cargo.CargoCategoryDto;
import com.arbitr.cargoway.dto.rq.PaginationRq;
import com.arbitr.cargoway.dto.rq.cargo.CargoOrderCreateRq;
import com.arbitr.cargoway.dto.rq.cargo.CargoOrderUpdateRq;
import com.arbitr.cargoway.dto.rq.cargo.FilterCargoRq;
import com.arbitr.cargoway.dto.rs.PaginationRs;
import com.arbitr.cargoway.dto.rs.cargo.CargoOrderRs;
import com.arbitr.cargoway.service.CargoOrderResponseService;
import com.arbitr.cargoway.service.CargoOrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cargos/")
@RequiredArgsConstructor
@Tag(name = "CargoOrder", description = "Управление записями о грузах")
public class CargoOrderController {
    private final CargoOrderService cargoOrderService;
    private final CargoOrderResponseService cargoOrderResponseService;

    @GetMapping("general/")
    public PaginationRs<CargoOrderRs> getInternalCargos(@RequestParam CargoCategoryDto cargoCategory,
            @ModelAttribute PaginationRq paginationRq) {
        return cargoOrderService.getGeneralCargosByCategory(cargoCategory, paginationRq);
    }

    @GetMapping("{cargoOrderId}/")
    public CargoOrderRs getCargoOrder(@PathVariable("cargoOrderId") UUID cargoOrderId) {
        return cargoOrderService.getCargoOrder(cargoOrderId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CargoOrderRs createNewCargoOrder(@RequestBody @Valid CargoOrderCreateRq cargoOrderCreateRq) {
        return cargoOrderService.createNewCargoOrder(cargoOrderCreateRq);
    }

    @PatchMapping("{cargoOrderId}/publish")
    public CargoOrderRs publishCargoOrder(@PathVariable("cargoOrderId") UUID cargoOrderId) {
        return cargoOrderService.publishCargoOrder(cargoOrderId);
    }

    @PatchMapping("{cargoOrderId}/draft")
    public CargoOrderRs draftCargoOrder(@PathVariable("cargoOrderId") UUID cargoOrderId) {
        return cargoOrderService.draftCargoOrder(cargoOrderId);
    }

    @PatchMapping("{cargoOrderId}/")
    public CargoOrderRs updateCargoOrder(@PathVariable("cargoOrderId") UUID cargoOrderId,
                                         @RequestBody @Valid CargoOrderUpdateRq cargoOrderUpdateRq) {
        return cargoOrderService.updateCargoOrder(cargoOrderId, cargoOrderUpdateRq);
    }

    @DeleteMapping("{cargoOrderId}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCargoOrder(@PathVariable("cargoOrderId") UUID cargoOrderId) {
        cargoOrderService.deleteCargoOrder(cargoOrderId);
    }

    @PostMapping("search/")
    public PaginationRs<CargoOrderRs> searchCargoOrder(@RequestBody @Valid FilterCargoRq filterCargoRq,
                                 @ModelAttribute PaginationRq paginationRq) {
        return cargoOrderService.searchCargoOrders(filterCargoRq, paginationRq);
    }

    @GetMapping("last5/")
    public List<CargoOrderRs> get5LastCargoOrders() {
        return cargoOrderService.getLastCargoOrder();
    }

    @PostMapping("{cargoOrderId}/transport/{transportId}/response/")
    public void makeCargoOrderResponse(@PathVariable("cargoOrderId") UUID cargoOrderId,
                                       @PathVariable("transportId") UUID transportId) {
        cargoOrderResponseService.makeResponse(cargoOrderId, transportId);
    }
}

