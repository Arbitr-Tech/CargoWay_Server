package com.arbitr.cargoway.controller;

import com.arbitr.cargoway.dto.general.cargo.CargoCategoryDto;
import com.arbitr.cargoway.dto.rq.PaginationRq;
import com.arbitr.cargoway.dto.rq.cargo.CargoOrderCreateRq;
import com.arbitr.cargoway.dto.rq.cargo.CargoOrderUpdateRq;
import com.arbitr.cargoway.dto.rs.PaginationRs;
import com.arbitr.cargoway.dto.rs.cargo.CargoOrderRs;
import com.arbitr.cargoway.service.CargoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cargos/")
@RequiredArgsConstructor
@Tag(name = "Profile", description = "Управление профилем пользователя")
public class CargoController {
    private final CargoService cargoService;

    @GetMapping("general/")
    public PaginationRs<CargoOrderRs> getInternalCargos(@PathParam("cargoCategory") CargoCategoryDto cargoCategory,
            @ModelAttribute PaginationRq paginationRq) {
        return cargoService.getGeneralCargosByCategory(cargoCategory, paginationRq);
    }

    @GetMapping("{cargoOrderId}/")
    public CargoOrderRs getCargoOrder(@PathVariable("cargoOrderId") UUID cargoOrderId) {
        return cargoService.getCargoOrder(cargoOrderId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CargoOrderRs createNewCargoOrder(@RequestBody @Valid CargoOrderCreateRq cargoOrderCreateRq) {
        return cargoService.createNewCargoOrder(cargoOrderCreateRq);
    }

    @PatchMapping("{cargoOrderId}/publish")
    public CargoOrderRs publishCargoOrder(@PathVariable("cargoOrderId") UUID cargoOrderId) {
        return cargoService.publishCargoOrder(cargoOrderId);
    }

    @PatchMapping("{cargoOrderId}/draft")
    public CargoOrderRs draftCargoOrder(@PathVariable("cargoOrderId") UUID cargoOrderId) {
        return cargoService.draftCargoOrder(cargoOrderId);
    }

    @PatchMapping("{cargoOrderId}/")
    public CargoOrderRs updateCargoOrder(@PathVariable("cargoOrderId") UUID cargoOrderId,
                                         @RequestBody @Valid CargoOrderUpdateRq cargoOrderUpdateRq) {
        return cargoService.updateCargoOrder(cargoOrderId, cargoOrderUpdateRq);
    }
}

