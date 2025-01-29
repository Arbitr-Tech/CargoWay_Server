package com.arbitr.cargoway.entity;

import com.arbitr.cargoway.entity.security.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id = UUID.randomUUID();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(mappedBy = "profile", targetEntity = Company.class)
    private Company company;

    @OneToOne(mappedBy = "profile", targetEntity = Individual.class)
    private Individual individual;
}
