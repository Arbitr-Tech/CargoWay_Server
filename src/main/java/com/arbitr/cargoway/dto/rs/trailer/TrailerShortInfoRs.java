package com.arbitr.cargoway.dto.rs.trailer;

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
@Schema(description = "DTO с информацией о прицепе")
public class TrailerShortInfoRs {
    @Schema(description = "Идентификатор трейлера")
    private UUID id;

    @Schema(description = "Название прицепа")
    private String name;
}
