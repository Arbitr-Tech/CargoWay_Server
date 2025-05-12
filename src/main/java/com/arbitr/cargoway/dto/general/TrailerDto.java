package com.arbitr.cargoway.dto.general;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO с информацией о прицепе")
public class TrailerDto {
    @NotBlank(message = "Название прицепа не может быть пустым")
    @Size(max = 100, message = "Название прицепа не должно превышать 100 символов")
    @Schema(description = "Название прицепа")
    private String name;

    @NotBlank(message = "Номер прицепа не может быть пустым")
    @Pattern(regexp = "^[АВЕКМНОРСТУХ]{2}\\d{4}$", message = "Номер прицепа должен соответствовать формату: 2 буквы, 4 цифры (например, АВ1234)")
    @Schema(description = "Номер прицепа", example = "А123ВС")
    private String trailerNumber;

    @NotNull(message = "Грузоподъемность не может быть пустой")
    @Positive(message = "Грузоподъемность должна быть положительной")
    @Max(value = 100000, message = "Грузоподъемность не должна превышать 100000 кг")
    @Schema(description = "Грузоподъемность транспорта (в кг)", example = "10000")
    private Integer liftingCapacity;

    @NotBlank(message = "Тип кузова не может быть пустым")
    @Size(max = 50, message = "Тип кузова не должен превышать 50 символов")
    @Schema(description = "Тип кузова", example = "Тент")
    private String bodyType;

    @NotBlank(message = "Тип загрузки не может быть пустым")
    @Size(max = 50, message = "Тип загрузки не должен превышать 50 символов")
    @Schema(description = "Тип загрузки", example = "Верхняя")
    private String loadType;

    @NotBlank(message = "Тип выгрузки не может быть пустым")
    @Size(max = 50, message = "Тип выгрузки не должен превышать 50 символов")
    @Schema(description = "Тип выгрузки", example = "Задняя")
    private String unloadType;

    @NotNull(message = "Длина прицепа не может быть пустой")
    @Positive(message = "Длина прицепа должна быть положительной")
    @Max(value = 50, message = "Длина прицепа не должна превышать 50 метров")
    @Schema(description = "Длина прицепа (в метрах)", example = "10")
    private Integer length;

    @NotNull(message = "Ширина прицепа не может быть пустой")
    @Positive(message = "Ширина прицепа должна быть положительной")
    @Max(value = 10, message = "Ширина прицепа не должна превышать 10 метров")
    @Schema(description = "Ширина прицепа (в метрах)", example = "2")
    private Integer width;

    @NotNull(message = "Высота прицепа не может быть пустой")
    @Positive(message = "Высота прицепа должна быть положительной")
    @Max(value = 10, message = "Высота прицепа не должна превышать 10 метров")
    @Schema(description = "Высота прицепа (в метрах)", example = "3")
    private Integer height;

    @NotNull(message = "Объем прицепа не может быть пустым")
    @Positive(message = "Объем прицепа должен быть положительным")
    @Max(value = 1000, message = "Объем прицепа не должен превышать 1000 м³")
    @Schema(description = "Объем прицепа (в м³)", example = "30")
    private Integer volume;
}
