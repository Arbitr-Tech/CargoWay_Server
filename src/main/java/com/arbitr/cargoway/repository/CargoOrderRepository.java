package com.arbitr.cargoway.repository;

import com.arbitr.cargoway.entity.CargoOrder;
import com.arbitr.cargoway.entity.enums.CargoOrderStatus;
import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface CargoOrderRepository extends JpaRepository<CargoOrder, UUID> {
    @EntityGraph(attributePaths = {"cargo", "cargo.images"})
    Optional<CargoOrder> findCargoOrderByIdAndOwner_Id(UUID id, UUID ownerId);

    @EntityGraph(attributePaths = {"cargo", "cargo.images"})
    Optional<CargoOrder> findCargoOrderByIdAndOwner_IdOrExecutor_Id(UUID id, UUID ownerId, UUID executorId);

    @EntityGraph(attributePaths = {"cargo", "cargo.images"})
    Page<CargoOrder> findCargoOrdersByVisibilityIsInAndOwner_Id(Set<CargoOrderStatus> visibilities,
                                                              UUID profileId, Pageable pageable);

    @EntityGraph(attributePaths = {"cargo", "cargo.images"})
    Page<CargoOrder> findCargoOrdersByVisibilityIsInAndExecutor_Id(Set<CargoOrderStatus> visibilities,
                                                                     UUID executorId, Pageable pageable);

    @Query("""
    SELECT c_o
        FROM CargoOrderResponse c_o_r
        JOIN c_o_r.cargoOrder c_o
        JOIN c_o.cargo c
        LEFT JOIN c.images img
    WHERE c_o_r.responder.id = :responderId
        AND c_o.visibility IN :visibilities
    ORDER BY c_o.orderUpdatedAt DESC
    """)
    Page<CargoOrder> findCargoOrdersByVisibilityIsInAndResponder_Id(Set<CargoOrderStatus> visibilities,
                                                                    UUID responderId, Pageable pageable);

    @Query("""
    SELECT c_o
        FROM CargoOrder c_o
        JOIN c_o.cargo c
        LEFT JOIN c.images img
    WHERE c_o.visibility IN :visibilities
    ORDER BY c_o.orderUpdatedAt DESC
    LIMIT 5
    """)
    List<CargoOrder> findLast5CargoOrdersByVisibilityIsIn(@PathParam("visibilities") Set<CargoOrderStatus> visibilities);
}
