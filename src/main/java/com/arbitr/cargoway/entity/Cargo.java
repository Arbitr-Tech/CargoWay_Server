package com.arbitr.cargoway.entity;

import com.arbitr.cargoway.entity.enums.VisibilityStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cargos")
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

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
