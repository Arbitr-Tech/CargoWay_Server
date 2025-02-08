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
    @Size(min = 1, message = "Поле не должно быть пустым")
    private String name;

    @Schema(description = "Описание предмета", example = "Большая коробка")
    @Size(min = 1, message = "Поле не должно быть пустым")
    private String description;

    @Schema(description = "Вес предмета в кг", example = "10")
    @Min(value = 1, message = "Вес должен быть больше 0")
    private Integer weight;

    @Schema(description = "Объем предмета в куб. метрах", example = "50")
    @Min(value = 1, message = "Объем должен быть больше 0")
    private Integer volume;

    @Schema(description = "Тип загрузки", example = "Задняя")
    @Size(min = 1, message = "Поле не должно быть пустым")
    private String loadType;

    @Schema(description = "Тип выгрузки", example = "Задняя")
    @Size(min = 1, message = "Поле не должно быть пустым")
    private String unloadType;

    @Schema(description = "Тип кузова машины для перевозки", example = "Тент")
    @Size(min = 1, message = "Поле не должно быть пустым")
    private String bodyType;

    @Schema(description = "Размеры предмета")
    private DimensionsDto dimensions;

    @Schema(description = "Маршрут предмета")
    private RouteDto route;

    @Schema(description = "Цена предмета", example = "99.99")
    @DecimalMin(value = "0.01", message = "Цена должна быть больше 0")
    private BigDecimal price;

    @Schema(description = "Тип оплаты", example = "Наличные")
    @Size(min = 1, message = "Поле не должно быть пустым")
    private String typePay;

    @Schema(description = "Дата готовности груза", example = "2025-02-08")
    @Future(message = "Дата готовности должна быть в будущем")
    private LocalDate readyDate;

    @Schema(description = "Желаемая дата доставки", example = "2025-02-10")
    @Future(message = "Дата доставки должна быть в будущем")
    private LocalDate deliveryDate;

    @Schema(description = "Статус груза", example = "ACTIVE")
    private CargoStatus status;

    @Schema(description = "Список фотографий груза")
    private List<Photo> photos;

    @Data
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
    public static class RouteDto {
        @Schema(description = "Место отправления", example = "Москва")
        @NotBlank(message = "Место отправления обязательно")
        private String from;

        @Schema(description = "Место назначения", example = "Санкт-Петербург")
        @NotBlank(message = "Место назначения обязательно")
        private String to;
    }
}
