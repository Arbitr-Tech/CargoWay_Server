package com.arbitr.cargoway.dto.rq.transaport;

import com.arbitr.cargoway.dto.Photo;
import com.arbitr.cargoway.dto.rq.cargo.RecordStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class TransportCreateRq {

    @Schema(description = "Тип транспорта", example = "Грузовик")
    @NotBlank(message = "Тип транспорта не может быть пустым")
    private String type;

    @Schema(description = "Модель транспорта", example = "Volvo FH16")
    @NotBlank(message = "Модель транспорта не может быть пустой")
    private String model;

    @Schema(description = "Грузоподъемность транспорта (в кг)", example = "10000")
    @Min(value = 0, message = "Грузоподъемность должна быть положительным числом")
    private Integer capacity;

    @Schema(description = "Объем транспорта (в м³)", example = "50")
    @Min(value = 0, message = "Объем должен быть положительным числом")
    private Integer volume;

    @Schema(description = "Тип загрузки", example = "Верхняя")
    @NotBlank(message = "Тип загрузки не может быть пустым")
    private String loadType;

    @Schema(description = "Тип выгрузки", example = "Задняя")
    @NotBlank(message = "Тип выгрузки не может быть пустым")
    private String unloadType;

    @Schema(description = "Размеры транспорта")
    @NotNull(message = "Размеры транспорта не могут быть null")
    private DimensionsDto dimensions;

    @Schema(description = "Маршрут транспорта")
    @NotNull(message = "Маршрут транспорта не может быть null")
    private RouteDto route;

    @Schema(description = "Цена за перевозку", example = "5000.00")
    @DecimalMin(value = "0.00", message = "Цена должна быть больше или равна 0")
    private BigDecimal price;

    @Schema(description = "Тип оплаты", example = "Предоплата")
    @NotBlank(message = "Тип оплаты не может быть пустым")
    private String typePay;

    @Schema(description = "Дата готовности транспорта", example = "2025-02-08")
    @Future(message = "Дата готовности должна быть в будущем")
    private LocalDate readyDate;

    @Schema(description = "Необходимая дата доставки", example = "2025-02-15")
    @Future(message = "Дата доставки должна быть в будущем")
    private LocalDate deliveryDate;

    @Schema(description = "Статус транспорта")
    private RecordStatus status;

    @Schema(description = "ID фотографий транспорта")
    private List<Photo> photos;

    @Data
    public static class DimensionsDto {
        @Schema(description = "Длина транспорта (в метрах)", example = "10")
        @Min(value = 0, message = "Длина должна быть положительным числом")
        private Integer length;

        @Schema(description = "Ширина транспорта (в метрах)", example = "2.5")
        @Min(value = 0, message = "Ширина должна быть положительным числом")
        private Integer width;

        @Schema(description = "Высота транспорта (в метрах)", example = "3")
        @Min(value = 0, message = "Высота должна быть положительным числом")
        private Integer height;
    }

    @Data
    public static class RouteDto {
        @Schema(description = "Место отправления", example = "Москва")
        @NotBlank(message = "Место отправления не может быть пустым")
        private String from;

        @Schema(description = "Место назначения", example = "Санкт-Петербург")
        @NotBlank(message = "Место назначения не может быть пустым")
        private String to;
    }
}
