package com.arbitr.cargoway.dto.general;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO с информацией об отзыве")
public class ReviewDto {
    @Schema(description = "Комментарий отзыва")
    private String comment;

    @Schema(description = "Рейтинг отзыва")
    private Integer rating;
}
