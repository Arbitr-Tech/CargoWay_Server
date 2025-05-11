package com.arbitr.cargoway.dto.general.profile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Краткие данные о профиле")
public class ProfileShortDto {
    private UUID id;
    private String profileName;
    private Double systemRating;
    private Double userRating;
}
