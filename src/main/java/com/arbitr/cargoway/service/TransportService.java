package com.arbitr.cargoway.service;

import com.arbitr.cargoway.dto.rq.PaginationRq;
import com.arbitr.cargoway.dto.rq.transaport.TransportCreateRq;
import com.arbitr.cargoway.dto.rq.transaport.TransportUpdateRq;
import com.arbitr.cargoway.dto.rs.PaginationRs;
import com.arbitr.cargoway.dto.rs.transport.TransportRs;
import com.arbitr.cargoway.entity.Transport;

import java.util.UUID;

public interface TransportService {
    TransportRs createTransport(TransportCreateRq transportCreateRq);
    PaginationRs<TransportRs> getTransports(PaginationRq paginationRq);
    TransportRs getTransport(UUID transportId);
    Transport getTransportByIdAndCurrentProfile(UUID transportId);
    TransportRs updateTransport(UUID transportId, TransportUpdateRq transportUpdateRq);
    void deleteTransport(UUID transportId);
}
