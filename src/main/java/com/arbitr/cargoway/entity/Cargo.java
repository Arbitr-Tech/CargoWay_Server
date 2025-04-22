package com.arbitr.cargoway.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cargos", indexes = {
        @Index(name = "idx_cargo_weight", columnList = "weight"),
        @Index(name = "idx_cargo_volume", columnList = "volume"),
        @Index(name = "idx_cargo_price", columnList = "price"),
        @Index(name = "idx_cargo_route", columnList = "from, to"),
        @Index(name = "idx_cargo_dates", columnList = "ready_date, deliveryDate")
})
public class Cargo {
    @Id
    @Builder.Default
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id = UUID.randomUUID();

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "weight", nullable = false)
    private Integer weight;

    @Column(name = "volume", nullable = false)
    private Integer volume;

    @Column(name = "load_type", nullable = false)
    private String loadType;

    @Column(name = "unload_type", nullable = false)
    private String unloadType;

    @Column(name = "body_type", nullable = false)
    private String bodyType;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "type_pay", nullable = false)
    private String typePay;

    @Column(name = "ready_date", nullable = false)
    private LocalDate readyDate;

    @Column(name = "delivery_date", nullable = false)
    private LocalDate deliveryDate;

    @Column(name = "route_from", nullable = false)
    private String from;

    @Column(name = "route_to", nullable = false)
    private String to;

    @Column(name = "length", nullable = false)
    private Integer length;

    @Column(name = "width", nullable = false)
    private Integer width;

    @Column(name = "height", nullable = false)
    private Integer height;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cargo_order_id", referencedColumnName = "id", nullable = false)
    private CargoOrder cargoOrder;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "cargos_images",
            joinColumns = @JoinColumn(name = "cargo_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private List<Image> images = new ArrayList<>();
}
