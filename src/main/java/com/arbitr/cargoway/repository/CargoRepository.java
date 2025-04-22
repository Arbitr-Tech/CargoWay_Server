package com.arbitr.cargoway.repository;

import com.arbitr.cargoway.entity.Cargo;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, UUID>, JpaSpecificationExecutor<Cargo> {

    @NotNull
    @EntityGraph(attributePaths = {"images"})
    Page<Cargo> findAll(Specification<Cargo> spec, @NotNull Pageable pageable);
}
