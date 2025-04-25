package com.arbitr.cargoway.repository;

import com.arbitr.cargoway.entity.CargoOrder;
import com.arbitr.cargoway.entity.enums.VisibilityStatus;
import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface CargoOrderRepository extends JpaRepository<CargoOrder, UUID> {
    @EntityGraph(attributePaths = {"cargo", "cargo.images"})
    Page<CargoOrder> findCargoOrdersByVisibilityIsInAndOwner_Id(Set<VisibilityStatus> visibilities,
                                                              UUID profileId, Pageable pageable);

    @Query("""
    SELECT c_o
        FROM CargoOrder c_o
        JOIN c_o.cargo c
        LEFT JOIN c.images img
    WHERE c_o.visibility IN :visibilities
    ORDER BY c_o.orderUpdatedAt DESC
    LIMIT 5
    """)
    List<CargoOrder> findLast5CargoOrdersByVisibilityIsIn(@PathParam("visibilities") Set<VisibilityStatus> visibilities);
}
