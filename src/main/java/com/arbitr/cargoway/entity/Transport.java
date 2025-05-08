package com.arbitr.cargoway.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "manufacture_year", nullable = false)
    private Year manufactureYear;

    @Column(name = "transport_number", nullable = false)
    private String transportNumber;

    @Embedded
    @Column(name = "embedded_trailer_details")
    private TrailerDetails embeddedTrailerDetails;

    @Builder.Default
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Data
    @Builder
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TrailerDetails {
        @Column(name = "lifting_capacity")
        private Integer liftingCapacity;

        @Column(name = "load_type")
        private String loadType;

        @Column(name = "unload_type")
        private String unloadType;

        @Column(name = "bodyType")
        private String bodyType;

        @Column(name = "length")
        private Integer length;

        @Column(name = "width")
        private Integer width;

        @Column(name = "height")
        private Integer height;

        @Column(name = "trailer_volume")
        private Integer volume;
    }

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id", nullable = false)
    private Profile profile;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "driver_id", referencedColumnName = "id", nullable = false)
    private Driver driver;

    @OneToMany(mappedBy = "transport", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Trailer> trailers;

    @ManyToMany
    @JoinTable(
            name = "transport_images",
            joinColumns = @JoinColumn(name = "transport_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private List<Image> images = new ArrayList<>();
}