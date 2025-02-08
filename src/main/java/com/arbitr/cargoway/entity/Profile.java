package com.arbitr.cargoway.entity;

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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(mappedBy = "profile", targetEntity = Company.class, cascade = CascadeType.ALL)
    private Company company;

    @OneToOne(mappedBy = "profile", targetEntity = Individual.class, cascade = CascadeType.ALL)
    private Individual individual;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cargo> cargo;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transport> transport;
}
