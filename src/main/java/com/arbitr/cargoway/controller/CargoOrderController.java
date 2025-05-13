package com.arbitr.cargoway.controller;

import com.arbitr.cargoway.dto.general.cargo.VisibilityCategory;
import com.arbitr.cargoway.dto.rq.PaginationRq;
import com.arbitr.cargoway.dto.rq.cargo.CargoOrderCreateRq;
import com.arbitr.cargoway.dto.rq.cargo.CargoOrderUpdateRq;
import com.arbitr.cargoway.dto.rs.PaginationRs;
import com.arbitr.cargoway.dto.rs.cargo.CargoOrderRs;
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

    @GetMapping("general/")
    public PaginationRs<CargoOrderRs> getInternalCargos(@RequestParam VisibilityCategory cargoCategory,
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

    @PatchMapping("{cargoOrderId}/")
    public CargoOrderRs updateCargoOrder(@PathVariable("cargoOrderId") UUID cargoOrderId,
                                         @RequestBody CargoOrderUpdateRq cargoOrderUpdateRq) {
        return cargoOrderService.updateCargoOrder(cargoOrderId, cargoOrderUpdateRq);
    }

    @PostMapping("{cargoOrderId}/cancel/")
    public CargoOrderRs cancelCargoOrder(@PathVariable("cargoOrderId") UUID cargoOrderId) {
        return cargoOrderService.cancelExecutionCargoOrder(cargoOrderId);
    }

    @DeleteMapping("{cargoOrderId}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCargoOrder(@PathVariable("cargoOrderId") UUID cargoOrderId) {
        cargoOrderService.deleteCargoOrder(cargoOrderId);
    }

    @GetMapping("last5/")
    public List<CargoOrderRs> get5LastCargoOrders() {
        return cargoOrderService.getLastCargoOrder();
    }
}

