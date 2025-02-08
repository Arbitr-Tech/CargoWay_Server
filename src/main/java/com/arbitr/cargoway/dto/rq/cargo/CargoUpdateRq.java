package com.arbitr.cargoway.dto.rq.cargo;

import com.arbitr.cargoway.dto.Photo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Schema(description = "Запрос на создание груза")
public class CargoUpdateRq {

    @Schema(description = "Название предмета", example = "Box")
    @NotBlank(message = "Название не должно быть пустым")
    private String name;

    @Schema(description = "Описание предмета", example = "Большая коробка")
    private String description;

    @Schema(description = "Вес предмета в кг", example = "10")
    @Min(value = 1, message = "Вес должен быть больше 0")
    @NotNull(message = "Вес обязателен")
    private Integer weight;

    @Schema(description = "Объем предмета в куб. метрах", example = "50")
    @Min(value = 1, message = "Объем должен быть больше 0")
    @NotNull(message = "Объем обязателен")
    private Integer volume;

    @Schema(description = "Тип загрузки", example = "Задняя")
    @NotBlank(message = "Тип загрузки обязателен")
    private String loadType;

    @Schema(description = "Тип выгрузки", example = "Задняя")
    @NotBlank(message = "Тип выгрузки обязателен")
    private String unloadType;

    @Schema(description = "Тип кузова машины для перевозки", example = "Тент")
    @NotBlank(message = "Тип кузова обязателен")
    private String bodyType;

    @Schema(description = "Размеры предмета")
    @NotNull(message = "Габариты обязательны")
    private DimensionsDto dimensions;

    @Schema(description = "Маршрут предмета")
    @NotNull(message = "Маршрут обязателен")
    private RouteDto route;

    @Schema(description = "Цена предмета", example = "99.99")
    @DecimalMin(value = "0.01", message = "Цена должна быть больше 0")
    @NotNull(message = "Цена обязательна")
    private BigDecimal price;

    @Schema(description = "Тип оплаты", example = "Наличные")
    @NotBlank(message = "Тип оплаты обязателен")
    private String typePay;

    @Schema(description = "Дата готовности груза", example = "2025-02-08")
    @Future(message = "Дата готовности должна быть в будущем")
    @NotNull(message = "Дата готовности обязательна")
    private LocalDate readyDate;

    @Schema(description = "Желаемая дата доставки", example = "2025-02-10")
    @Future(message = "Дата доставки должна быть в будущем")
    @NotNull(message = "Дата доставки обязательна")
    private LocalDate deliveryDate;

    @Schema(description = "Статус груза", example = "ACTIVE")
    @NotNull(message = "Статус груза обязателен")
    private CargoStatus status;

    @Schema(description = "Список фотографий груза")
    private List<Photo> photos;

    @Data
    public static class DimensionsDto {
        @Schema(description = "Длина предмета в см", example = "100")
        @Min(value = 1, message = "Длина должна быть больше 0")
        @NotNull(message = "Длина обязательна")
        private Integer length;

        @Schema(description = "Ширина предмета в см", example = "50")
        @Min(value = 1, message = "Ширина должна быть больше 0")
        @NotNull(message = "Ширина обязательна")
        private Integer width;

        @Schema(description = "Высота предмета в см", example = "75")
        @Min(value = 1, message = "Высота должна быть больше 0")
        @NotNull(message = "Высота обязательна")
        private Integer height;
    }

    @Data
    public static class RouteDto {
        @Schema(description = "Место отправления", example = "Москва")
        @NotBlank(message = "Место отправления обязательно")
        private String from;

        @Schema(description = "Место назначения", example = "Санкт-Петербург")
        @NotBlank(message = "Место назначения обязательно")
        private String to;
    }
}
