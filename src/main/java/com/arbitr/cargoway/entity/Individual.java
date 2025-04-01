package com.arbitr.cargoway.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "individuals")
public class Individual {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "fullname", nullable = false)
    private String fullName;

    @Pattern(regexp = "\\d{4} \\d{6}", message = "Номер паспорта должен быть в формате 'XXXX XXXXXX'")
    @Column(name = "passport_number", nullable = false, unique = true, length = 11)
    private String passportNumber;

    @Size(max = 255, message = "Поле 'Кем выдан' не должно превышать 255 символов")
    @Column(name = "issued_by", nullable = false)
    private String issuedBy;

    @PastOrPresent(message = "Дата выдачи не может быть в будущем")
    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Pattern(regexp = "\\d{3}-\\d{3}", message = "Код подразделения должен быть в формате 'XXX-XXX'")
    @Column(name = "department_code", nullable = false, length = 7)
    private String departmentCode;

    @Size(max = 500, message = "Адрес регистрации не должен превышать 500 символов")
    @Column(name = "registration_address", nullable = false, length = 500)
    private String registrationAddress;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", referencedColumnName = "id", nullable = false)
    private Profile profile;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "passport_data_image",
            joinColumns = @JoinColumn(name = "passport_data_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private List<Image> passportImages = new ArrayList<>();
}
