package com.arbitr.cargoway.entity;

import com.arbitr.cargoway.entity.enums.LegalType;
import com.arbitr.cargoway.entity.security.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "legal_type", nullable = false)
    private LegalType legalType;

    @Column(name = "user_rating", nullable = false)
    private Double userRating = 0.0;

    @Column(name = "system_rating", nullable = false)
    private Double systemRating = 0.0;

    @Column(name = "activated", nullable = false)
    private Boolean activated = false;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(mappedBy = "profile", targetEntity = Company.class, cascade = CascadeType.ALL)
    private Company company;

    @OneToOne(mappedBy = "profile", targetEntity = Individual.class, cascade = CascadeType.ALL)
    private Individual individual;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ContactData contactData;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cargo> cargos;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Driver> drivers;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transport> transports;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;
}
