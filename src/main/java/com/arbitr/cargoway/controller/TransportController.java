package com.arbitr.cargoway.controller;

import com.arbitr.cargoway.dto.rq.PaginationRq;
import com.arbitr.cargoway.dto.rq.transaport.TransportCreateRq;
import com.arbitr.cargoway.dto.rq.transaport.TransportUpdateRq;
import com.arbitr.cargoway.dto.rs.PaginationRs;
import com.arbitr.cargoway.dto.rs.trailer.TrailerRs;
import com.arbitr.cargoway.dto.rs.transport.TransportRs;
import com.arbitr.cargoway.service.TransportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/transports/")
@RequiredArgsConstructor
@Tag(name = "Управление транспортами", description = "Управление транспортами текущего перевозчика")
public class TransportController {
    private final TransportService transportService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransportRs createTransport(@RequestBody @Valid TransportCreateRq transportCreateRq) {
        return transportService.createTransport(transportCreateRq);
    }

    @GetMapping("{transportId}/")
    public TransportRs getTransport(@PathVariable("transportId") UUID transportId) {
        return transportService.getTransport(transportId);
    }

    @GetMapping
    public PaginationRs<TransportRs> getTransports(@ModelAttribute PaginationRq paginationRq) {
        return transportService.getTransports(paginationRq);
    }

    @PatchMapping("{transportId}/")
    public TransportRs updateTransport(@PathVariable("transportId") UUID transportId,
                                       @RequestBody TransportUpdateRq transportUpdateRq) {
        return transportService.updateTransport(transportId, transportUpdateRq);
    }

    @DeleteMapping("{transportId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTransport(@PathVariable("transportId") UUID transportId) {
        transportService.deleteTransport(transportId);
    }
}
