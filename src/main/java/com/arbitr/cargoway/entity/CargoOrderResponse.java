package com.arbitr.cargoway.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cargo_order_responses")
public class CargoOrderResponse {
    @Id
    @Builder.Default
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id = UUID.randomUUID();

    @Builder.Default
    @Column(name = "response_date_time", nullable = false)
    private LocalDateTime responseDateTime = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "responder_id", referencedColumnName = "id", nullable = false)
    private Profile responder;

    @ManyToOne
    @JoinColumn(name = "responder_transport_id", referencedColumnName = "id", nullable = false)
    private Transport transport;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "cargo_order_id", referencedColumnName = "id",  nullable = false)
    private CargoOrder cargoOrder;
}
