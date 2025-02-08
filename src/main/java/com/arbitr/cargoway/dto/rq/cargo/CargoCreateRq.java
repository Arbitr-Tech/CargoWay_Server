package com.arbitr.cargoway.dto.rq.cargo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CargoCreateRq {
    @Schema(description = "Название предмета", example = "Box")
    @NotBlank(message = "Name must not be blank")
    private String name;

    @Schema(description = "Описание предмета", example = "A large box")
    private String description;

    @Schema(description = "Вес предмета", example = "10")
    @Min(value = 0, message = "Weight must be a positive number")
    private Integer weight;

    @Schema(description = "Объем предмета", example = "50")
    @Min(value = 0, message = "Volume must be a positive number")
    private Integer volume;

    @Schema(description = "Тип загрузки", example = "Задняя")
    private String loadType;

    @Schema(description = "Тип выгрузки", example = "Задняя")
    private String unloadType;

    @Schema(description = "Размеры предмета")
    @NotNull(message = "Dimensions cannot be null")
    private DimensionsDto dimensions;

    @Schema(description = "Маршрут предмета")
    @NotNull(message = "Route cannot be null")
    private RouteDto route;

    @Schema(description = "Цена предмета", example = "99.99")
    @DecimalMin(value = "0.00", message = "Price must be greater than or equal to 0")
    private BigDecimal price;

    @Schema(description = "Дата готовности предмета", example = "2025-02-08")
    @Future(message = "Ready date must be in the future")
    private LocalDate readyDate;

    @Schema(description = "Необходимая дата доставки")
    @Future(message = "Ready date must be in the future")
    private LocalDate deliveryDate;

    @Schema(description = "Статус груза")
    private CargoStatus status;

    @Data
    public static class DimensionsDto {
        @Schema(description = "Длина предмета", example = "10")
        @Min(value = 0, message = "Length must be positive")
        private Integer length;

        @Schema(description = "Ширина предмета", example = "5")
        @Min(value = 0, message = "Width must be positive")
        private Integer width;

        @Schema(description = "Высота предмета", example = "20")
        @Min(value = 0, message = "Height must be positive")
        private Integer height;
    }

    @Data
    public static class RouteDto {
        @Schema(description = "Место отправления", example = "New York")
        @NotBlank(message = "From location must not be blank")
        private String from;

        @Schema(description = "Место назначения", example = "Los Angeles")
        @NotBlank(message = "To location must not be blank")
        private String to;
    }
}
