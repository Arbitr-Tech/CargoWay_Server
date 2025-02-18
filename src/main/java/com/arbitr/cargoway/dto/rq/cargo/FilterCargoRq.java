package com.arbitr.cargoway.dto.rq.cargo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FilterCargoRq {

    @Schema(description = "Минимальный вес груза (в кг)", example = "10")
    private Integer weightFrom;

    @Schema(description = "Максимальный вес груза (в кг)", example = "100")
    private Integer weightTo;

    @Schema(description = "Минимальный объем груза (в м³)", example = "5")
    private Integer volumeFrom;

    @Schema(description = "Максимальный объем груза (в м³)", example = "50")
    private Integer volumeTo;

    @Schema(description = "Тип загрузки", example = "Задняя")
    private String loadType;

    @Schema(description = "Тип выгрузки", example = "Задняя")
    private String unloadType;

    @Schema(description = "Цена груза от", example = "99.99")
    private BigDecimal priceFrom;

    @Schema(description = "Цена груза до", example = "1000.99")
    private BigDecimal priceTo;

    @Schema(description = "Тип кузова для перевозки")
    private String bodyType;

    @Schema(description = "Дата готовности груза", example = "2025-02-09")
    private LocalDate readyDate;

    @Schema(description = "Маршрут груза")
    private RouteDto route;

    @Schema(description = "Габариты груза")
    private DimensionsDto dimensions;

    @Data
    public static class RouteDto {
        @Schema(description = "Место отправления", example = "New York")
        private String from;

        @Schema(description = "Место назначения", example = "Los Angeles")
        private String to;
    }

    @Data
    public static class DimensionsDto {
        @Schema(description = "Длина груза (в см)", example = "100")
        private Integer length;

        @Schema(description = "Ширина груза (в см)", example = "50")
        private Integer width;

        @Schema(description = "Высота груза (в см)", example = "75")
        private Integer height;
    }
}
