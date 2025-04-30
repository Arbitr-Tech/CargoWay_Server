package com.arbitr.cargoway.dto.general;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO с информацией о грузовой части машины")
public class EmbeddedTrailerDto {
    @Schema(description = "Грузоподъемность транспорта (в кг)", example = "10000")
    @Min(value = 1, message = "Грузоподъемность должна быть больше 0")
    private Integer liftingCapacity;

    @Schema(description = "Тип кузова", example = "Тент")
    @NotBlank(message = "Тип кузова не может быть пустым")
    private String bodyType;

    @Schema(description = "Тип загрузки", example = "Верхняя")
    @NotBlank(message = "Тип загрузки не может быть пустым")
    private String loadType;

    @Schema(description = "Тип выгрузки", example = "Задняя")
    @NotBlank(message = "Тип выгрузки не может быть пустым")
    private String unloadType;

    @Schema(description = "Длина прицепа (в метрах)", example = "10")
    @Min(value = 1, message = "Длина должна быть больше 0")
    private Integer length;

    @Schema(description = "Ширина прицепа (в метрах)", example = "2.5")
    @Min(value = 1, message = "Ширина должна быть больше 0")
    private Integer width;

    @Schema(description = "Высота прицепа (в метрах)", example = "3")
    @Min(value = 1, message = "Высота должна быть больше 0")
    private Integer height;

    @Schema(description = "Объем прицепа (в м³)", example = "30")
    @Min(value = 1, message = "Объем должен быть больше 0")
    private Integer volume;
}
