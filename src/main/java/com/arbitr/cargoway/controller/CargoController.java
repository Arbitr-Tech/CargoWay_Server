package com.arbitr.cargoway.controller;

import com.arbitr.cargoway.dto.CargoCategoryDto;
import com.arbitr.cargoway.dto.VisibilityStatusDto;
import com.arbitr.cargoway.dto.rq.PaginationRq;
import com.arbitr.cargoway.dto.rq.cargo.CargoCreateRq;
import com.arbitr.cargoway.dto.rq.cargo.CargoUpdateRq;
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
    public CargoOrderRs createNewCargo(@RequestBody @Valid CargoCreateRq cargoCreateRq) {
        return cargoService.createNewCargo(cargoCreateRq);
    }

    @PatchMapping("{cargoId}/")
    public CargoOrderRs updateCargo(@PathVariable("cargoId") UUID cargoId,
                                    @RequestBody @Valid CargoUpdateRq cargoUpdateRq) {
        return cargoService.updateCargo(cargoId, cargoUpdateRq);
    }
}

