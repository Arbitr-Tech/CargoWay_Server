package com.arbitr.cargoway.entity;

import com.arbitr.cargoway.entity.enums.VisibilityStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "transports")
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "volume", nullable = false)
    private Integer volume;

    @Column(name = "load_type", nullable = false)
    private String loadType;

    @Column(name = "unload_type", nullable = false)
    private String unloadType;

    @Embedded
    private Dimensions dimensions;

    @Embedded
    private Route route;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "ready_date", nullable = false)
    private LocalDate readyDate;

    @Column(name = "delivery_date", nullable = false)
    private LocalDate deliveryDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility")
    private VisibilityStatus visibility;

    @Embeddable
    @Data
    public static class Dimensions {
        @Column(name = "length", nullable = false)
        private Integer length;

        @Column(name = "width", nullable = false)
        private Integer width;

        @Column(name = "height", nullable = false)
        private Integer height;
    }

    @Embeddable
    @Data
    public static class Route {
        @Column(name = "route_from", nullable = false)
        private String from;

        @Column(name = "route_to", nullable = false)
        private String to;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", referencedColumnName = "id", nullable = false)
    private Profile profile;
}