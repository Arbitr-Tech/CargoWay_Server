package com.arbitr.cargoway.service.impl;

import com.arbitr.cargoway.dto.rq.PaginationRq;
import com.arbitr.cargoway.dto.rq.trailer.TrailerCreateRq;
import com.arbitr.cargoway.dto.rq.trailer.TrailerUpdateRq;
import com.arbitr.cargoway.dto.rs.PaginationRs;
import com.arbitr.cargoway.dto.rs.trailer.TrailerRs;
import com.arbitr.cargoway.dto.rs.trailer.TrailerShortInfoRs;
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
    public PaginationRs<TrailerRs> getCurrentProfileTrailers(PaginationRq paginationRq) {
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
    public List<TrailerShortInfoRs> getListCurrentProfileTrailers() {
        Profile currentProfile = profileService.getAuthenticatedProfile();

        List<Trailer> trailers = trailerRepository.findTrailersByProfile_Id(currentProfile.getId());

        return trailers.stream()
                .map(trailerMapper::toShortRsDto)
                .toList();
    }

    @Override
    public TrailerRs updateTrailer(UUID trailerId, TrailerUpdateRq trailerUpdateRq) {
        Trailer existingTrailer = this.getTrailerByIdAndCurrentProfile(trailerId);

        if (trailerUpdateRq.getName() != null) {
            existingTrailer.setName(trailerUpdateRq.getName());
        }
        if (trailerUpdateRq.getTrailerNumber() != null) {
            existingTrailer.setTrailerNumber(trailerUpdateRq.getTrailerNumber());
        }
        if (trailerUpdateRq.getLiftingCapacity() != null) {
            existingTrailer.setLiftingCapacity(trailerUpdateRq.getLiftingCapacity());
        }
        if (trailerUpdateRq.getBodyType() != null) {
            existingTrailer.setBodyType(trailerUpdateRq.getBodyType());
        }
        if (trailerUpdateRq.getLoadType() != null) {
            existingTrailer.setLoadType(trailerUpdateRq.getLoadType());
        }
        if (trailerUpdateRq.getUnloadType() != null) {
            existingTrailer.setUnloadType(trailerUpdateRq.getUnloadType());
        }
        if (trailerUpdateRq.getWidth() != null) {
            existingTrailer.setWidth(trailerUpdateRq.getWidth());
        }
        if (trailerUpdateRq.getLength() != null) {
            existingTrailer.setLength(trailerUpdateRq.getLength());
        }
        if (trailerUpdateRq.getHeight() != null) {
            existingTrailer.setHeight(trailerUpdateRq.getHeight());
        }
        if (trailerUpdateRq.getVolume() != null) {
            existingTrailer.setVolume(trailerUpdateRq.getVolume());
        }

        trailerRepository.save(existingTrailer);
        return trailerMapper.toRsDto(existingTrailer);
    }

    @Override
    public void deleteTrailer(UUID trailerId) {
        Trailer foundTrailer = this.getTrailerByIdAndCurrentProfile(trailerId);
        trailerRepository.delete(foundTrailer);
    }
}
