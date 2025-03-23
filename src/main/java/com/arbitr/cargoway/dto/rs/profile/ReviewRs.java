package com.arbitr.cargoway.dto.rs.profile;


import java.time.LocalDateTime;
import java.util.UUID;

public class ReviewRs {
    private UUID id;

    private String title;

    private String comment;

    private Double rating;

    private LocalDateTime createdAt;
}
