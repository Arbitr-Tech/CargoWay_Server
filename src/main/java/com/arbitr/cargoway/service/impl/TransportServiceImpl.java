package com.arbitr.cargoway.service.impl;

import com.arbitr.cargoway.dto.general.EmbeddedTrailerDto;
import com.arbitr.cargoway.dto.rq.PaginationRq;
import com.arbitr.cargoway.dto.rq.transaport.TransportCreateRq;
import com.arbitr.cargoway.dto.rq.transaport.TransportUpdateRq;
import com.arbitr.cargoway.dto.rs.PaginationRs;
import com.arbitr.cargoway.dto.rs.transport.TransportRs;
import com.arbitr.cargoway.entity.Driver;
import com.arbitr.cargoway.entity.Profile;
import com.arbitr.cargoway.entity.Trailer;
import com.arbitr.cargoway.entity.Transport;
import com.arbitr.cargoway.exception.NotFoundException;
import com.arbitr.cargoway.mapper.TransportMapper;
import com.arbitr.cargoway.repository.TransportRepository;
import com.arbitr.cargoway.service.DriverService;
import com.arbitr.cargoway.service.ProfileService;
import com.arbitr.cargoway.service.TrailerService;
import com.arbitr.cargoway.service.TransportService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransportServiceImpl implements TransportService {
    private final ProfileService profileService;
    private final DriverService driverService;
    private final TrailerService trailerService;
    private final TransportRepository transportRepository;
    private final TransportMapper transportMapper;

    @Override
    public TransportRs createTransport(TransportCreateRq transportCreateRq) {
        Profile currentProfile = profileService.getAuthenticatedProfile();

        List<Trailer> transportTrailers = trailerService.getTrailersByIds(transportCreateRq.getTrailersIds());

        if (!transportTrailers.isEmpty() && !(transportTrailers.size() == transportCreateRq.getTrailersIds().size())) {
            throw new NotFoundException("Один или несколько элементов из переданного списка не были обнаружены в БД");
        }

        Driver foundDriver = driverService.getDriverByIdAndCurrentProfile(transportCreateRq.getDriverId());

        Transport newTransport = transportMapper.toEntity(transportCreateRq);
        newTransport.setProfile(currentProfile);
        newTransport.setDriver(foundDriver);
        newTransport.setTrailers(transportTrailers);
        transportTrailers.forEach(trailer -> trailer.setTransport(newTransport));

        transportRepository.save(newTransport);
        return transportMapper.toRsDto(newTransport);
    }

    @Override
    public PaginationRs<TransportRs> getTransports(PaginationRq paginationRq) {
        Profile currentProfile = profileService.getAuthenticatedProfile();

        Page<Transport> transportPage = transportRepository.findTransportsByProfile_Id(currentProfile.getId(),
                PageRequest.of(paginationRq.getPageNumber(), paginationRq.getPageSize()));

        List<TransportRs> transports = transportPage.getContent().stream()
                .map(transportMapper::toRsDto)
                .toList();

        return PaginationRs.of(
                transports,
                transportPage.getNumber(),
                transportPage.getSize(),
                transportPage.getTotalPages()
        );
    }

    @Override
    public TransportRs getTransport(UUID transportId) {
        Transport foundTransport = transportRepository.findById(transportId).orElseThrow(
                () -> new NotFoundException("Транспорт с id=%s не был найден!".formatted(transportId))
        );
        return transportMapper.toRsDto(foundTransport);
    }

    @Override
    public Transport getTransportByIdAndCurrentProfile(UUID transportId) {
        Profile currentProfile = profileService.getAuthenticatedProfile();

        return transportRepository.findTransportByIdAndProfileId(transportId, currentProfile.getId()).orElseThrow(
                () -> new NotFoundException("Транспорт с id=%s не был найден!".formatted(transportId))
        );
    }

    @Override
    @Transactional
    public TransportRs updateTransport(UUID transportId, TransportUpdateRq transportUpdateRq) {
        Transport existingTransport = this.getTransportByIdAndCurrentProfile(transportId);

        if (transportUpdateRq.getBrand() != null) {
            existingTransport.setBrand(transportUpdateRq.getBrand());
        }
        if (transportUpdateRq.getModel() != null) {
            existingTransport.setModel(transportUpdateRq.getModel());
        }
        if (transportUpdateRq.getManufactureYear() != null) {
            existingTransport.setManufactureYear(transportUpdateRq.getManufactureYear());
        }
        if (transportUpdateRq.getTransportNumber() != null) {
            existingTransport.setTransportNumber(transportUpdateRq.getTransportNumber());
        }

        if (transportUpdateRq.getDriverId() != null) {
            Driver existingDriver = driverService.getDriverByIdAndCurrentProfile(transportUpdateRq.getDriverId());
            existingTransport.setDriver(existingDriver);
        }

        if (transportUpdateRq.getEmbeddedTrailerDetails() != null) {
            Transport.TrailerDetails trailerDetails = existingTransport.getEmbeddedTrailerDetails() != null
                    ? existingTransport.getEmbeddedTrailerDetails()
                    : new Transport.TrailerDetails();
            EmbeddedTrailerDto trailerDto = transportUpdateRq.getEmbeddedTrailerDetails();

            if (trailerDto.getBodyType() != null) {
                trailerDetails.setBodyType(trailerDto.getBodyType());
            }
            if (trailerDto.getLiftingCapacity() != null) {
                trailerDetails.setLiftingCapacity(trailerDto.getLiftingCapacity());
            }
            if (trailerDto.getUnloadType() != null) {
                trailerDetails.setUnloadType(trailerDto.getUnloadType());
            }
            if (trailerDto.getLoadType() != null) {
                trailerDetails.setLoadType(trailerDto.getLoadType());
            }
            if (trailerDto.getWidth() != null) {
                trailerDetails.setWidth(trailerDto.getWidth());
            }
            if (trailerDto.getHeight() != null) {
                trailerDetails.setHeight(trailerDto.getHeight());
            }
            if (trailerDto.getLength() != null) {
                trailerDetails.setLength(trailerDto.getLength());
            }
            if (trailerDto.getVolume() != null) {
                trailerDetails.setVolume(trailerDto.getVolume());
            }
            existingTransport.setEmbeddedTrailerDetails(trailerDetails);
        }

        if (transportUpdateRq.getTrailersIds() != null) {
            List<Trailer> transportTrailers = trailerService.getTrailersByIds(transportUpdateRq.getTrailersIds());

            if (!transportTrailers.isEmpty() && !(transportTrailers.size() == transportUpdateRq.getTrailersIds().size())) {
                throw new NotFoundException("Один или несколько элементов из переданного списка не были обнаружены в БД");
            }
            existingTransport.setTrailers(transportTrailers);
            transportTrailers.forEach(trailer -> trailer.setTransport(existingTransport));
        }

        transportRepository.save(existingTransport);

        return transportMapper.toRsDto(existingTransport);
    }

    @Override
    public void deleteTransport(UUID transportId) {
        Transport foundTransport = this.getTransportByIdAndCurrentProfile(transportId);
        transportRepository.delete(foundTransport);
    }
}
