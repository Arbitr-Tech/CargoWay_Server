package com.arbitr.cargoway.entity;


import com.arbitr.cargoway.entity.enums.VisibilityStatus;
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
@Table(name = "cargo_orders")
public class CargoOrder {
    @Id
    @Builder.Default
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id = UUID.randomUUID();

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false)
    private VisibilityStatus visibility;

    @Column(name = "start_execution")
    private LocalDateTime startExecution;

    @Column(name = "end_execution")
    private LocalDateTime endExecution;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executor_id", referencedColumnName = "id")
    private Profile executor;

    @Builder.Default
    @Column(name = "order_created_at", nullable = false)
    private LocalDateTime orderCreatedAt = LocalDateTime.now();

    @Builder.Default
    @Column(name = "order_updated_at", nullable = false)
    private LocalDateTime orderUpdatedAt = LocalDateTime.now();

    @OneToOne(mappedBy = "cargoOrder", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Cargo cargo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", referencedColumnName = "id", nullable = false)
    private Profile owner;
}
