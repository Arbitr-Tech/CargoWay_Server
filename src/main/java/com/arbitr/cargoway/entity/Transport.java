package com.arbitr.cargoway.entity;

import com.arbitr.cargoway.entity.enums.VisibilityStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transports")
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "year", nullable = false)
    private Year year;

    @Column(name = "transport_number", nullable = false)
    private String transportNumber;

    @Column(name = "lifting_capacity", nullable = false)
    private Integer liftingCapacity;

    @Column(name = "load_type", nullable = false)
    private String loadType;

    @Column(name = "unload_type", nullable = false)
    private String unloadType;

    @Column(name = "bodyType", nullable = false)
    private String bodyType;

    @Embedded
    private Route route;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "type_pay", nullable = false)
    private String typePay;

    @Column(name = "ready_date", nullable = false)
    private LocalDate readyDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false)
    private VisibilityStatus visibility;

    @Builder.Default
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Embeddable
    @Data
    public static class Route {
        @Column(name = "route_from", nullable = false)
        private String from;

        @Column(name = "route_to", nullable = false)
        private String to;
    }

    @Embeddable
    @Data
    public static class TrailerDetails {
        @Column(name = "length", nullable = false)
        private Integer length;

        @Column(name = "width", nullable = false)
        private Integer width;

        @Column(name = "height", nullable = false)
        private Integer height;

        @Column(name = "trailer_volume", nullable = false)
        private Integer volume;

        @Column(name = "trailer_number", nullable = false)
        private String trailerNumber;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", referencedColumnName = "id", nullable = false)
    private Profile profile;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "driver_id", referencedColumnName = "id", nullable = false)
    private Driver driver;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "transport_images",
            joinColumns = @JoinColumn(name = "transport_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private List<Image> images = new ArrayList<>();
}