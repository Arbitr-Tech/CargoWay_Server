package com.arbitr.cargoway.service.impl;

import com.arbitr.cargoway.dto.rq.transaport.TransportCreateRq;
import com.arbitr.cargoway.dto.rq.transaport.FilterTransportRq;
import com.arbitr.cargoway.dto.rs.transport.TransportDetailsRs;
import com.arbitr.cargoway.dto.rq.transaport.TransportUpdateRq;
import com.arbitr.cargoway.service.TransportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransportServiceImpl implements TransportService {
    @Override
    public List<TransportDetailsRs> searchTransport(FilterTransportRq filter) {
        return List.of();
    }

    @Override
    public TransportDetailsRs getTransportDetails(UUID transportId) {
        return null;
    }

    @Override
    public TransportDetailsRs createNewTransport(TransportCreateRq transportCreateRq) {
        return null;
    }

    @Override
    public TransportDetailsRs updateTransport(UUID transportId, TransportUpdateRq transportUpdateRq) {
        return null;
    }

    @Override
    public void deleteTransport(UUID transportId) {

    }
}
