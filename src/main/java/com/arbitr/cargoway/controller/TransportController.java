package com.arbitr.cargoway.controller;

import com.arbitr.cargoway.dto.rq.cargo.RecordStatus;
import com.arbitr.cargoway.dto.rq.transaport.FilterTransportRq;
import com.arbitr.cargoway.dto.rq.transaport.TransportCreateRq;
import com.arbitr.cargoway.dto.rq.transaport.TransportUpdateRq;
import com.arbitr.cargoway.dto.rs.transport.TransportDetailsRs;
import com.arbitr.cargoway.service.TransportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transports/")
public class TransportController {
    private final TransportService transportService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransportDetailsRs createTransport(@RequestBody @Valid TransportCreateRq transportCreateRq) {
        return transportService.createNewTransport(transportCreateRq);
    }

    @GetMapping("{transportId}/")
    public TransportDetailsRs getTransportDetails(@PathVariable("transportId") UUID transportId) {
        return transportService.getTransportDetails(transportId);
    }

    @GetMapping("search/")
    public List<TransportDetailsRs> searchTransport(FilterTransportRq filter) {
        return transportService.searchTransport(filter);
    }

    @PatchMapping("{transportId}/")
    public TransportDetailsRs updateTransport(@PathVariable("transportId") UUID transportId,
                                              @RequestBody TransportUpdateRq transportUpdateRq) {
        return transportService.updateTransport(transportId, transportUpdateRq);
    }

    @DeleteMapping("{transportId}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTransport(@PathVariable("transportId") UUID transportId) {
        transportService.deleteTransport(transportId);
    }

    @GetMapping("count/{number}/")
    public List<TransportDetailsRs> getLastCargos(@PathVariable int number) {
        return transportService.getLastTransports(number);
    }

    @PatchMapping("{transportId}/change-status/")
    public TransportDetailsRs changeCargoStatus(@PathVariable UUID transportId, @RequestParam @Valid RecordStatus status) {
        return transportService.changeTransportVisibility(transportId, status);
    }
}
