package com.arbitr.cargoway.repository;

import com.arbitr.cargoway.entity.CargoOrder;
import com.arbitr.cargoway.entity.enums.VisibilityStatus;
import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface CargoOrderRepository extends JpaRepository<CargoOrder, UUID> {
    Page<CargoOrder> findCargoOrdersByVisibilityIsInAndProfile_Id(Set<VisibilityStatus> visibility,
                                                              UUID profileId, Pageable pageable);
}
