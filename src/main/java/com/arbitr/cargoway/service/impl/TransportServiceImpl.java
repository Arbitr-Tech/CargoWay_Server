package com.arbitr.cargoway.service.impl;

import com.arbitr.cargoway.dto.rq.cargo.RecordStatus;
import com.arbitr.cargoway.dto.rq.transaport.FilterTransportRq;
import com.arbitr.cargoway.dto.rq.transaport.TransportCreateRq;
import com.arbitr.cargoway.dto.rq.transaport.TransportUpdateRq;
import com.arbitr.cargoway.dto.rs.transport.TransportDetailsRs;
import com.arbitr.cargoway.entity.Image;
import com.arbitr.cargoway.entity.Profile;
import com.arbitr.cargoway.entity.Transport;
import com.arbitr.cargoway.exception.NotFoundException;
import com.arbitr.cargoway.mapper.TransportMapper;
import com.arbitr.cargoway.repository.TransportRepository;
import com.arbitr.cargoway.service.ImageService;
import com.arbitr.cargoway.service.ProfileService;
import com.arbitr.cargoway.service.TransportService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransportServiceImpl implements TransportService {
    private final ProfileService profileService;
    private final ImageService imageService;
    private final TransportRepository transportRepository;
    private final TransportMapper transportMapper;

    @Transactional
    @Override
    public TransportDetailsRs createNewTransport(TransportCreateRq transportCreateRq) {
        Transport newTransport = transportMapper.buildTransportFrom(transportCreateRq);

        List<Image> transportImages = imageService.getImagesByIds(transportCreateRq.getPhotos());
        Profile userProfile = profileService.getAuthenticatedProfile();

        newTransport.setProfile(userProfile);
        newTransport.setImages(transportImages);

        transportRepository.save(newTransport);
        return transportMapper.buildTransportDetailsRs(newTransport);
    }

    @Override
    public TransportDetailsRs getTransportDetails(UUID transportId) {
        Transport foundTransport = findTransportOrElseThrowNotFound(transportId);
        return transportMapper.buildTransportDetailsRs(foundTransport);
    }

    @Override
    public List<TransportDetailsRs> searchTransport(FilterTransportRq filter) {
        // Реализация поиска с использованием спецификаций или QueryDSL
        // Заглушка для примера:
        return transportRepository.findAll().stream()
                .map(transportMapper::buildTransportDetailsRs)
                .toList();
    }

    @Override
    public List<TransportDetailsRs> getLastTransports(int number) {
        Pageable pageRequest = PageRequest.of(0, number, Sort.by("createdAt").descending());
        return transportRepository.findAll(pageRequest).getContent().stream()
                .map(transportMapper::buildTransportDetailsRs)
                .toList();
    }

    @Transactional
    @Override
    public TransportDetailsRs updateTransport(UUID transportId, TransportUpdateRq transportUpdateRq) {
        return null;
    }

    @Override
    public TransportDetailsRs changeTransportVisibility(UUID transportId, RecordStatus status) {
        Transport transport = findTransportOrElseThrowNotFound(transportId);
        transport.setVisibility(transportMapper.mapStatusToVisibility(status));
        transportRepository.save(transport);
        return transportMapper.buildTransportDetailsRs(transport);
    }

    @Transactional
    @Override
    public void deleteTransport(UUID transportId) {
        Transport transport = findTransportOrElseThrowNotFound(transportId);
        transportRepository.delete(transport);
    }

    private Transport findTransportOrElseThrowNotFound(UUID transportId) {
        return transportRepository.findById(transportId)
                .orElseThrow(() -> new NotFoundException("Транспорт с ID %s не найден".formatted(transportId)));
    }
}
