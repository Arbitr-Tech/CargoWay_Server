package com.arbitr.cargoway.service.impl;

import com.arbitr.cargoway.dto.rq.PaginationRq;
import com.arbitr.cargoway.dto.rq.trailer.TrailerCreateRq;
import com.arbitr.cargoway.dto.rq.trailer.TrailerUpdateRq;
import com.arbitr.cargoway.dto.rs.PaginationRs;
import com.arbitr.cargoway.dto.rs.trailer.TrailerRs;
import com.arbitr.cargoway.entity.Profile;
import com.arbitr.cargoway.entity.Trailer;
import com.arbitr.cargoway.exception.NotFoundException;
import com.arbitr.cargoway.mapper.TrailerMapper;
import com.arbitr.cargoway.repository.TrailerRepository;
import com.arbitr.cargoway.service.ProfileService;
import com.arbitr.cargoway.service.TrailerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrailerServiceImpl implements TrailerService {
    private final ProfileService profileService;
    private final TrailerRepository trailerRepository;
    private final TrailerMapper trailerMapper;

    @Override
    public TrailerRs createTrailer(TrailerCreateRq trailerCreateRq) {
        Profile currentProfile = profileService.getAuthenticatedProfile();

        Trailer newTrailer = trailerMapper.toEntity(trailerCreateRq);
        newTrailer.setProfile(currentProfile);

        trailerRepository.save(newTrailer);
        return trailerMapper.toRsDto(newTrailer);
    }

    @Override
    public Trailer getTrailerByIdAndCurrentProfile(UUID trailerId) {
        Profile currentProfile = profileService.getAuthenticatedProfile();

        return trailerRepository.findTrailerByIdAndProfile_Id(trailerId, currentProfile.getId())
                .orElseThrow(
                        () -> new NotFoundException("Прицеп с id=%s не был найден!".formatted(trailerId))
                );
    }

    @Override
    public TrailerRs getTrailer(UUID trailerId) {
        Trailer foundTrailer = this.getTrailerByIdAndCurrentProfile(trailerId);
        return trailerMapper.toRsDto(foundTrailer);
    }

    @Override
    public List<Trailer> getTrailersByIds(List<UUID> trailersIds) {
        return trailerRepository.findTrailersByIdIn(trailersIds);
    }

    @Override
    public PaginationRs<TrailerRs> getTrailers(PaginationRq paginationRq) {
        Profile currentProfile = profileService.getAuthenticatedProfile();

        Page<Trailer> trailerPage = trailerRepository.findTrailersByProfile_Id(currentProfile.getId(),
                PageRequest.of(paginationRq.getPageNumber(), paginationRq.getPageSize())
        );

        List<TrailerRs> trailers = trailerPage.getContent().stream()
                .map(trailerMapper::toRsDto)
                .toList();

        return PaginationRs.of(
                trailers,
                trailerPage.getNumber(),
                trailerPage.getSize(),
                trailerPage.getTotalPages()
        );
    }

    @Override
    public TrailerRs updateTrailer(UUID trailerId, TrailerUpdateRq trailerUpdateRq) {
        Trailer foundTrailer = this.getTrailerByIdAndCurrentProfile(trailerId);

        if (trailerUpdateRq.getTrailerNumber() != null) {
            foundTrailer.setTrailerNumber(trailerUpdateRq.getTrailerNumber());
        }
        if (trailerUpdateRq.getLiftingCapacity() != null) {
            foundTrailer.setLiftingCapacity(trailerUpdateRq.getLiftingCapacity());
        }
        if (trailerUpdateRq.getBodyType() != null) {
            foundTrailer.setBodyType(trailerUpdateRq.getBodyType());
        }
        if (trailerUpdateRq.getLoadType() != null) {
            foundTrailer.setLoadType(trailerUpdateRq.getLoadType());
        }
        if (trailerUpdateRq.getUnloadType() != null) {
            foundTrailer.setUnloadType(trailerUpdateRq.getUnloadType());
        }
        if (trailerUpdateRq.getWidth() != null) {
            foundTrailer.setWidth(trailerUpdateRq.getWidth());
        }
        if (trailerUpdateRq.getLength() != null) {
            foundTrailer.setLength(trailerUpdateRq.getLength());
        }
        if (trailerUpdateRq.getHeight() != null) {
            foundTrailer.setHeight(trailerUpdateRq.getHeight());
        }
        if (trailerUpdateRq.getVolume() != null) {
            foundTrailer.setVolume(trailerUpdateRq.getVolume());
        }

        trailerRepository.save(foundTrailer);
        return trailerMapper.toRsDto(foundTrailer);
    }

    @Override
    public void deleteTrailer(UUID trailerId) {
        Trailer foundTrailer = this.getTrailerByIdAndCurrentProfile(trailerId);
        trailerRepository.delete(foundTrailer);
    }
}
