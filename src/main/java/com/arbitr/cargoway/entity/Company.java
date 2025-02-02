package com.arbitr.cargoway.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", nullable = false, unique = false)
    private String name;

    @Column(name = "inn", nullable = false, unique = true, length = 12)
    private String inn;

    @Column(name = "ogrn", nullable = false, unique = true, length = 13)
    private String ogrn;

    @Column(name = "bic", nullable = false, unique = false, length = 9)
    private String bic;

    @Column(name = "correspondent_account", nullable = false, unique = true, length = 20)
    private String correspondentAccount;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @Builder.Default
    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate = LocalDate.now();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;
}
