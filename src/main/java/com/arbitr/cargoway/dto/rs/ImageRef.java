package com.arbitr.cargoway.dto.rs;

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
@Schema(description = "Данные возвращаемого изображения")
public class ImageRef {

    @Schema(description = "Уникальный глобальный строковый идентификатор")
    private UUID guid;

    @Schema(description = "Пусть к ресурсу")
    private String path;
}
