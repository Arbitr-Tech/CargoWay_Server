package com.arbitr.cargoway.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "DTO для id фото")
public class Photo {
    @Schema(description = "UUID фотографии")
    private UUID id;
}
