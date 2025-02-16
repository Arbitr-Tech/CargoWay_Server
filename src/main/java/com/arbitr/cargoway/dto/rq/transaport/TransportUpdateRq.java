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
@Schema(description = "Запрос на обновление данных транспорта")
public class TransportUpdateRq {

    @Schema(description = "Тип транспорта", example = "Грузовик")
    @Size(min = 1, message = "Тип транспорта не может быть пустым")
    private String type;

    @Schema(description = "Модель транспорта", example = "Volvo FH16")
    @Size(min = 1, message = "Модель транспорта не может быть пустой")
    private String model;

    @Schema(description = "Грузоподъемность транспорта (в кг)", example = "10000")
    @Min(value = 1, message = "Грузоподъемность должна быть больше 0")
    private Integer capacity;

    @Schema(description = "Объем транспорта (в м³)", example = "50")
    @Min(value = 1, message = "Объем должен быть больше 0")
    private Integer volume;

    @Schema(description = "Тип загрузки", example = "Верхняя")
    @Size(min = 1, message = "Тип загрузки не может быть пустым")
    private String loadType;

    @Schema(description = "Тип выгрузки", example = "Задняя")
    @Size(min = 1, message = "Тип выгрузки не может быть пустым")
    private String unloadType;

    @Schema(description = "Размеры транспорта")
    private DimensionsDto dimensions;

    @Schema(description = "Маршрут транспорта")
    private RouteDto route;

    @Schema(description = "Цена за перевозку", example = "5000.00")
    @DecimalMin(value = "0.01", message = "Цена должна быть больше 0")
    private BigDecimal price;

    @Schema(description = "Тип оплаты", example = "Предоплата")
    @Size(min = 1, message = "Тип оплаты не может быть пустым")
    private String typePay;

    @Schema(description = "Дата готовности транспорта", example = "2025-02-08")
    @Future(message = "Дата готовности должна быть в будущем")
    private LocalDate readyDate;

    @Schema(description = "Необходимая дата доставки", example = "2025-02-15")
    @Future(message = "Дата доставки должна быть в будущем")
    private LocalDate deliveryDate;

    @Schema(description = "Статус транспорта", example = "ACTIVE")
    private RecordStatus status;

    @Schema(description = "Список ID фотографий транспорта")
    private List<Photo> photos;

    @Data
    public static class DimensionsDto {
        @Schema(description = "Длина транспорта (в метрах)", example = "10")
        @Min(value = 1, message = "Длина должна быть больше 0")
        private Integer length;

        @Schema(description = "Ширина транспорта (в метрах)", example = "2.5")
        @Min(value = 1, message = "Ширина должна быть больше 0")
        private Integer width;

        @Schema(description = "Высота транспорта (в метрах)", example = "3")
        @Min(value = 1, message = "Высота должна быть больше 0")
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
