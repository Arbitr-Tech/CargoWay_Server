package com.arbitr.cargoway.dto.general;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "UUID фотографии для создания новой сущности")
public class Photo {
    @Schema(description = "UUID фотографии")
    private UUID id;
}
