package com.arbitr.cargoway.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "fullname", nullable = false, unique = false, length = 250)
    private String fullname;

    @Column(name = "passport_num", nullable = false, unique = false, length = 6)
    private Integer passportNum;

    @Column(name = "passport_series", nullable = false, unique = false, length = 4)
    private Integer passportSeries;

    @Column(name = "who_give", nullable = false, unique = false, length = 350)
    private String whoGive;

    @Column(name = "department_code", nullable = false, unique = false, length = 10)
    private String departmentCode;

    @Column(name = "phone_number", nullable = false, unique = false, length = 100)
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "individuals_images",
            joinColumns = @JoinColumn(name = "individual_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private List<Image> images = new ArrayList<>();
}
