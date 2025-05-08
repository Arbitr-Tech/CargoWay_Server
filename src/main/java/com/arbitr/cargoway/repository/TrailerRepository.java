package com.arbitr.cargoway.repository;

import com.arbitr.cargoway.entity.Trailer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrailerRepository extends JpaRepository<Trailer, UUID> {
    Optional<Trailer> findTrailerByIdAndProfile_Id(UUID trailerId, UUID profileId);
    Page<Trailer> findTrailersByProfile_Id(UUID profileId, Pageable pageable);
    List<Trailer> findTrailersByProfile_Id(UUID profileId);
    List<Trailer> findTrailersByIdIn(Collection<UUID> ids);
}
