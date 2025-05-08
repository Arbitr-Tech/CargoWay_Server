package com.arbitr.cargoway.service;

import com.arbitr.cargoway.dto.rq.PaginationRq;
import com.arbitr.cargoway.dto.rq.trailer.TrailerCreateRq;
import com.arbitr.cargoway.dto.rq.trailer.TrailerUpdateRq;
import com.arbitr.cargoway.dto.rs.PaginationRs;
import com.arbitr.cargoway.dto.rs.trailer.TrailerRs;
import com.arbitr.cargoway.dto.rs.trailer.TrailerShortInfoRs;
import com.arbitr.cargoway.entity.Trailer;

import java.util.List;
import java.util.UUID;

public interface TrailerService {
    TrailerRs createTrailer(TrailerCreateRq trailerCreateRq);
    Trailer getTrailerByIdAndCurrentProfile(UUID trailerId);
    TrailerRs getTrailer(UUID trailerId);
    List<Trailer> getTrailersByIds(List<UUID> trailersIds);
    PaginationRs<TrailerRs> getCurrentProfileTrailers(PaginationRq paginationRq);
    List<TrailerShortInfoRs> getListCurrentProfileTrailers();
    TrailerRs updateTrailer(UUID trailerId, TrailerUpdateRq trailerUpdateRq);
    void deleteTrailer(UUID trailerId);
}
