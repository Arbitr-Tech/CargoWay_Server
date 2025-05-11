package com.arbitr.cargoway.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
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
@Table(name = "driver")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "fullname", nullable = false)
    private String fullName;

    @Column(name = "license_category", nullable = false)
    private String licenseCategory;

    @Column(name = "license_number", nullable = false, unique = true, length = 11)
    private String licenseNumber;

    @PastOrPresent(message = "Дата выдачи прав не может быть в будущем")
    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @FutureOrPresent(message = "Дата окончания действия прав не может быть в прошлом")
    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id", nullable = false)
    private Profile profile;
}
