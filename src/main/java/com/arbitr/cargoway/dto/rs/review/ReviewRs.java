package com.arbitr.cargoway.dto.rs.review;

import com.arbitr.cargoway.dto.general.ReviewDto;
import com.arbitr.cargoway.dto.general.profile.ProfileShortDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReviewRs extends ReviewDto {
    @Schema(description = "Идентификатор отзыва")
    private UUID id;

    @Schema(description = "Дата и время создания отзыва")
    private LocalDateTime createdAt;

    @Schema(description = "Данные профиля о комментаторе отзыва")
    private ProfileShortDto commentator;
}
