package com.arbitr.cargoway.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
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

    @Column(name = "name", nullable = false)
    private String name;

    @Pattern(regexp = "\\d{10}|\\d{12}", message = "ИНН должен состоять из 10 или 12 цифр")
    @Column(name = "inn", nullable = false, unique = true, length = 12)
    private String inn;

    @Pattern(regexp = "\\d{13}", message = "ОГРН должен состоять из 13 цифр")
    @Column(name = "ogrn", nullable = false, unique = true, length = 13)
    private String ogrn;

    @Pattern(regexp = "\\d{9}", message = "БИК должен состоять из 9 цифр")
    @Column(name = "bic", nullable = false, length = 9)
    private String bic;

    @Pattern(regexp = "\\d{20}", message = "Корреспондентский счёт должен состоять из 20 цифр")
    @Column(name = "correspondent_account", nullable = false, unique = true, length = 20)
    private String correspondentAccount;

    @PastOrPresent
    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @Builder.Default
    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate = LocalDate.now();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;
}
