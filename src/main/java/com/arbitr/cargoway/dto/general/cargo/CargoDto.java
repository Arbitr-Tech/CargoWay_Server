package com.arbitr.cargoway.dto.general.cargo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CargoDto {
    @Schema(description = "Название предмета", example = "Box")
    @NotBlank(message = "Название груза не может быть пустым")
    private String name;

    @Schema(description = "Описание предмета", example = "A large box")
    private String description;

    @Schema(description = "Вес предмета", example = "10")
    @Min(value = 0, message = "Вес не может быть отрицательным")
    private Integer weight;

    @Schema(description = "Объем предмета", example = "50")
    @Min(value = 0, message = "Объем не может быть отрицательным")
    private Integer volume;

    @Schema(description = "Тип загрузки", example = "Задняя")
    @NotBlank(message = "Тип загрузки не может быть пустым")
    private String loadType;

    @Schema(description = "Тип выгрузки", example = "Задняя")
    @NotBlank(message = "Тип выгрузки не может быть пустым")
    private String unloadType;

    @Schema(description = "Тип кузова машины для перевозки", example = "Тент")
    @NotBlank(message = "Тип кузова машины для перевозки не может быть пустым")
    private String bodyType;

    @Schema(description = "Цена предмета", example = "99.99")
    @DecimalMin(value = "0.00", message = "Price must be greater than or equal to 0")
    private BigDecimal price;

    @Schema(description = "Тип оплаты")
    @NotBlank(message = "Тип оплаты не может быть пустым")
    private String typePay;

    @Schema(description = "Дата готовности предмета", example = "2025-02-08")
    @Future(message = "Дата готовности груза не может быть прошедшей")
    private LocalDate readyDate;

    @Schema(description = "Необходимая дата доставки")
    @Future(message = "Дата доставки груза не может быть прошедшей")
    private LocalDate deliveryDate;

    @Schema(description = "Размеры предмета")
    private DimensionsDto dimensions;

    @Schema(description = "Маршрут предмета")
    private RouteDto route;

    @Schema(description = "id фотографий груза")
    private List<UUID> photos;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DimensionsDto {
        @Schema(description = "Длина предмета в см", example = "100")
        @Min(value = 1, message = "Длина должна быть больше 0")
        private Integer length;

        @Schema(description = "Ширина предмета в см", example = "50")
        @Min(value = 1, message = "Ширина должна быть больше 0")
        private Integer width;

        @Schema(description = "Высота предмета в см", example = "75")
        @Min(value = 1, message = "Высота должна быть больше 0")
        private Integer height;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RouteDto {
        @Schema(description = "Место отправления", example = "Москва")
        @NotBlank(message = "Место отправления обязательно")
        private String from;

        @Schema(description = "Место назначения", example = "Санкт-Петербург")
        @NotBlank(message = "Место назначения обязательно")
        private String to;
    }
}
