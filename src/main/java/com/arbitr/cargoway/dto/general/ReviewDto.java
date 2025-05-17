package com.arbitr.cargoway.dto.general;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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

    @Max(value = 5, message = "Рейтинг не может быть больше 5")
    @Min(value = 1, message = "Рейтинг не может быть меньше 1")
    @Schema(description = "Рейтинг отзыва")
    private Integer rating;
}
