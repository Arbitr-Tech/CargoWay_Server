package com.arbitr.cargoway.controller;

import com.arbitr.cargoway.dto.rq.cargo.CargoCreateRq;
import com.arbitr.cargoway.dto.rq.cargo.CargoStatus;
import com.arbitr.cargoway.dto.rq.cargo.CargoUpdateRq;
import com.arbitr.cargoway.dto.rq.cargo.FilterCargoRq;
import com.arbitr.cargoway.dto.rs.cargo.CargoDetailsRs;
import com.arbitr.cargoway.service.CargoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cargos/")
public class CargoController {
    private final CargoService cargoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CargoDetailsRs createCargo(@RequestBody @Valid CargoCreateRq cargoCreateRq) {
        return cargoService.createNewCargo(cargoCreateRq);
    }

    @GetMapping("{cargoId}/")
    public CargoDetailsRs getCargoDetails(@PathVariable UUID cargoId) {
        return cargoService.getCargo(cargoId);
    }

    @GetMapping
    public List<CargoDetailsRs> searchCargos(@Valid FilterCargoRq filter) {
        return cargoService.searchCargos(filter);
    }

    @GetMapping("count/{number}/")
    public List<CargoDetailsRs> getLastCargos(@PathVariable int number) {
        return cargoService.getLastCargos(number);
    }

    @PatchMapping("{cargoId}/")
    public CargoDetailsRs updateCargo(@PathVariable UUID cargoId, @RequestBody @Valid CargoUpdateRq cargoUpdateRq) {
        return cargoService.updateCargo(cargoId, cargoUpdateRq);
    }

    @PatchMapping("{cargoId}/change-status/")
    public CargoDetailsRs changeCargoStatus(@PathVariable UUID cargoId, @RequestParam @Valid CargoStatus status) {
        return cargoService.changeCargoGlobalVisibility(cargoId, status);
    }

    @DeleteMapping("{cargoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCargo(@PathVariable UUID cargoId) {
        cargoService.deleteCargo(cargoId);
    }
}
