package com.arbitr.cargoway.controller;

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
@Tag(name = "Customer", description = "Управление записями о грузах")
public class CustomerController {
    private final CargoOrderService cargoOrderService;
    private final CargoOrderResponseService cargoOrderResponseService;

    @PatchMapping("{cargoOrderId}/publish/")
    public CargoOrderRs publishCargoOrder(@PathVariable("cargoOrderId") UUID cargoOrderId) {
        return cargoOrderService.publishCargoOrder(cargoOrderId);
    }

    @PatchMapping("{cargoOrderId}/draft/")
    public CargoOrderRs draftCargoOrder(@PathVariable("cargoOrderId") UUID cargoOrderId) {
        return cargoOrderService.draftCargoOrder(cargoOrderId);
    }

    @PostMapping("/{cargoOrderId}/response/{responseId}/accept/")
    public CargoOrderRs startExecutionCargoOrder(@PathVariable("cargoOrderId") UUID cargoOrderId,
                                         @PathVariable("responseId") UUID responseId) {
        return cargoOrderResponseService.startExecutionCargoOrder(cargoOrderId, responseId);
    }

    @PostMapping("/{cargoOrderId}/endExecution/confirm/")
    public CargoOrderRs confirmEndExecutionCargoOrder(@PathVariable("cargoOrderId") UUID cargoOrderId) {
        return cargoOrderService.confirmEndExecutionCargoOrder(cargoOrderId);
    }
}
