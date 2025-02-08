package com.arbitr.cargoway.repository;

import com.arbitr.cargoway.entity.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CargoRepository extends JpaRepository<Cargo, UUID> {
    Optional<Cargo> findCargoById(UUID id);

    void deleteCargoById(UUID id);

    @Query("SELECT c FROM Cargo c")
    List<Cargo> findLimitCargos(Pageable pageable);

}
