package com.arbitr.cargoway.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trailers")
public class Trailer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "trailer_number", nullable = false, unique = true)
    private String trailerNumber;

    @Column(name = "lifting_capacity", nullable = false)
    private Integer liftingCapacity;

    @Column(name = "load_type", nullable = false)
    private String loadType;

    @Column(name = "unload_type", nullable = false)
    private String unloadType;

    @Column(name = "bodyType", nullable = false)
    private String bodyType;

    @Column(name = "length", nullable = false)
    private Integer length;

    @Column(name = "width", nullable = false)
    private Integer width;

    @Column(name = "height", nullable = false)
    private Integer height;

    @Column(name = "trailer_volume", nullable = false)
    private Integer volume;

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "transport_id", referencedColumnName = "id")
    private Transport transport;
}
