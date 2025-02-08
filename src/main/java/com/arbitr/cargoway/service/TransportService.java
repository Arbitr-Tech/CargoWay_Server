package com.arbitr.cargoway.service;

import com.arbitr.cargoway.dto.rq.cargo.TransportCreateRq;
import com.arbitr.cargoway.dto.rs.transport.FilterTransportRq;
import com.arbitr.cargoway.dto.rs.transport.TransportDetailsRs;
import com.arbitr.cargoway.dto.rs.transport.TransportUpdateRq;

import java.util.List;
import java.util.UUID;

public interface TransportService {
    List<TransportDetailsRs> searchTransport(FilterTransportRq filter);
    TransportDetailsRs getTransportDetails(UUID transportId);
    TransportDetailsRs createNewTransport(TransportCreateRq transportCreateRq);
    TransportDetailsRs updateTransport(UUID transportId, TransportUpdateRq transportUpdateRq);
    void deleteTransport(UUID transportId);
}
