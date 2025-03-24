package com.arbitr.cargoway.dto.rs.profile;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO-класс деталями отзыва")
public class ReviewRs {
    @Schema(description = "id отзыва пользователя")
    private UUID id;

    @Schema(description = "Заголовок отзыва")
    private String title;

    @Schema(description = "Описание отзыва")
    private String comment;

    @Schema(description = "Рейтинг от пользователя")
    private Double rating;

    @Schema(description = "Время и дата создания отзыва")
    private LocalDateTime createdAt;
}
