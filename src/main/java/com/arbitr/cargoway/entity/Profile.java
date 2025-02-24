package com.arbitr.cargoway.entity;

import com.arbitr.cargoway.entity.enums.ProfileType;
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
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "profile_type", nullable = false)
    private ProfileType profileType;

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
}
