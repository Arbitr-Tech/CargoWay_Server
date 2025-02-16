package com.arbitr.cargoway.service;

import com.arbitr.cargoway.dto.rq.cargo.RecordStatus;
import com.arbitr.cargoway.dto.rq.transaport.TransportCreateRq;
import com.arbitr.cargoway.dto.rq.transaport.FilterTransportRq;
import com.arbitr.cargoway.dto.rs.transport.TransportDetailsRs;
import com.arbitr.cargoway.dto.rq.transaport.TransportUpdateRq;

import java.util.List;
import java.util.UUID;

public interface TransportService {
    TransportDetailsRs createNewTransport(TransportCreateRq transportCreateRq);
    TransportDetailsRs getTransportDetails(UUID transportId);
    TransportDetailsRs updateTransport(UUID transportId, TransportUpdateRq transportUpdateRq);
    void deleteTransport(UUID transportId);
    List<TransportDetailsRs> searchTransport(FilterTransportRq filter);
    List<TransportDetailsRs> getLastTransports(int number);
    TransportDetailsRs changeTransportVisibility(UUID transportId, RecordStatus status);
}
