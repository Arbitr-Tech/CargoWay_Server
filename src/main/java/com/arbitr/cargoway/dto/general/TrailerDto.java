package com.arbitr.cargoway.dto.general;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO с информацией о прицепе")
public class TrailerDto {
    @Schema(description = "Номер прицепа", example = "A123BC")
    private String trailerNumber;

    @Schema(description = "Грузоподъемность транспорта (в кг)", example = "10000")
    private Integer liftingCapacity;

    @Schema(description = "Тип кузова", example = "Тент")
    private String bodyType;

    @Schema(description = "Тип загрузки", example = "Верхняя")
    private String loadType;

    @Schema(description = "Тип выгрузки", example = "Задняя")
    private String unloadType;

    @Schema(description = "Длина прицепа (в метрах)", example = "10")
    private Integer length;

    @Schema(description = "Ширина прицепа (в метрах)", example = "2.5")
    private Integer width;

    @Schema(description = "Высота прицепа (в метрах)", example = "3")
    private Integer height;

    @Schema(description = "Объем прицепа (в м³)", example = "30")
    private Integer volume;
}
