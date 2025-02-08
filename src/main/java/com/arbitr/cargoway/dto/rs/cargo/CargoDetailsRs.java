package com.arbitr.cargoway.dto.rs.cargo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@Schema(description = "Детальная информация о грузе")
public class CargoDetailsRs {

    @Schema(description = "Идентификатор груза", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(description = "Название груза", example = "Контейнер")
    private String name;

    @Schema(description = "Описание груза", example = "Контейнер с электроникой")
    private String description;

    @Schema(description = "Вес груза в кг", example = "1200")
    private Integer weight;

    @Schema(description = "Объем груза в кубических метрах", example = "8")
    private Integer volume;

    @Schema(description = "Тип загрузки", example = "Задняя")
    private String loadType;

    @Schema(description = "Тип выгрузки", example = "Боковая")
    private String unloadType;

    @Schema(description = "Габариты груза")
    private Dimensions dimensions;

    @Schema(description = "Маршрут груза")
    private Route route;

    @Schema(description = "Стоимость груза", example = "999.99")
    private BigDecimal price;

    @Schema(description = "Дата готовности груза", example = "2025-02-08")
    private LocalDate readyDate;

    @Schema(description = "Ожидаемая дата доставки", example = "2025-02-15")
    private LocalDate deliveryDate;

    @Schema(description = "Статус видимости груза", example = "PUBLIC")
    private String visibility;

    @Data
    @Builder
    @Schema(description = "Габариты груза")
    public static class Dimensions {
        @Schema(description = "Длина груза в см", example = "200")
        private Integer length;

        @Schema(description = "Ширина груза в см", example = "100")
        private Integer width;

        @Schema(description = "Высота груза в см", example = "150")
        private Integer height;
    }

    @Data
    @Builder
    @Schema(description = "Маршрут груза")
    public static class Route {
        @Schema(description = "Пункт отправления", example = "Москва")
        private String from;

        @Schema(description = "Пункт назначения", example = "Санкт-Петербург")
        private String to;
    }
}
