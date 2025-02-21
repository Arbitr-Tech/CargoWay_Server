package com.arbitr.cargoway.dto.rq.transaport;

import com.arbitr.cargoway.dto.Photo;
import com.arbitr.cargoway.dto.rq.cargo.RecordStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Data
@Schema(description = "Запрос на создание транспорта")
public class TransportCreateRq {

    @Schema(description = "Марка транспорта", example = "Volvo")
    @NotBlank(message = "Марка транспорта не может быть пустой")
    private String brand;

    @Schema(description = "Модель транспорта", example = "FH16")
    @NotBlank(message = "Модель транспорта не может быть пустой")
    private String model;

    @Schema(description = "Год выпуска транспорта", example = "2020")
    @NotNull(message = "Год выпуска транспорта не может быть пустым")
    private Year year;

    @Schema(description = "Номер транспорта", example = "A123BC")
    @NotBlank(message = "Номер транспорта не может быть пустым")
    private String transportNumber;

    @Schema(description = "Грузоподъемность транспорта (в кг)", example = "10000")
    @Min(value = 1, message = "Грузоподъемность должна быть больше 0")
    private Integer liftingCapacity;

    @Schema(description = "Тип загрузки", example = "Верхняя")
    @NotBlank(message = "Тип загрузки не может быть пустым")
    private String loadType;

    @Schema(description = "Тип выгрузки", example = "Задняя")
    @NotBlank(message = "Тип выгрузки не может быть пустым")
    private String unloadType;

    @Schema(description = "Тип кузова", example = "Тент")
    @NotBlank(message = "Тип кузова не может быть пустым")
    private String bodyType;

    @Schema(description = "Маршрут транспорта")
    @NotNull(message = "Маршрут транспорта не может быть null")
    private RouteDto route;

    @Schema(description = "Детали прицепа")
    private TrailerDetailsDto trailerDetails;

    @Schema(description = "Цена за перевозку", example = "5000.00")
    @DecimalMin(value = "0.00", message = "Цена должна быть больше или равна 0")
    private BigDecimal price;

    @Schema(description = "Тип оплаты", example = "Предоплата")
    @NotBlank(message = "Тип оплаты не может быть пустым")
    private String typePay;

    @Schema(description = "Дата готовности транспорта", example = "2025-02-08")
    @Future(message = "Дата готовности должна быть в будущем")
    private LocalDate readyDate;

    @Schema(description = "Статус транспорта")
    private RecordStatus status;

    @Schema(description = "ID фотографий транспорта")
    private List<Photo> photos;

    @Data
    @Schema(description = "Маршрут транспорта")
    public static class RouteDto {
        @Schema(description = "Место отправления", example = "Москва")
        @NotBlank(message = "Место отправления не может быть пустым")
        private String from;

        @Schema(description = "Место назначения", example = "Санкт-Петербург")
        @NotBlank(message = "Место назначения не может быть пустым")
        private String to;
    }

    @Data
    @Schema(description = "Детали прицепа")
    public static class TrailerDetailsDto {
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

        @Schema(description = "Номер прицепа", example = "A123BC")
        @NotBlank(message = "Номер прицепа не может быть пустым")
        private String trailerNumber;
    }
}
