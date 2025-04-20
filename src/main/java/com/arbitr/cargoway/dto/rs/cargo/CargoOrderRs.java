package com.arbitr.cargoway.dto.rs.cargo;

import com.arbitr.cargoway.dto.general.cargo.VisibilityStatusDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Детальная информация о заказе груза")
public class CargoOrderRs {

    @NotNull
    @Schema(description = "Идентификатор заказа", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @NotNull
    @Schema(description = "Статус видимости заказа", example = "PUBLIC")
    private VisibilityStatusDto visibilityStatus;

    @NotNull
    @Schema(description = "Дата и время начала выполнения заказа", example = "2025-04-15T10:00:00")
    private LocalDateTime startExecution;

    @NotNull
    @Schema(description = "Дата и время окончания выполнения заказа", example = "2025-04-16T18:00:00")
    private LocalDateTime endExecution;

    @NotNull
    @Schema(description = "Дата создания заказа", example = "2025-04-14T12:00:00")
    private LocalDateTime orderCreatedAt;

    @NotNull
    @Schema(description = "Дата последнего обновления заказа", example = "2025-04-14T12:30:00")
    private LocalDateTime orderUpdatedAt;

    @NotNull
    @Schema(description = "Детали груза")
    private CargoDetails cargo;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Детали груза")
    public static class CargoDetails {
        @NotNull
        @Schema(description = "Название груза", example = "Контейнер")
        private String name;

        @Schema(description = "Описание груза", example = "Контейнер с электроникой")
        private String description;

        @NotNull
        @Schema(description = "Вес груза в кг", example = "1200")
        private Integer weight;

        @NotNull
        @Schema(description = "Объем груза в кубических метрах", example = "8")
        private Integer volume;

        @NotNull
        @Schema(description = "Тип загрузки", example = "Задняя")
        private String loadType;

        @NotNull
        @Schema(description = "Тип выгрузки", example = "Боковая")
        private String unloadType;

        @NotNull
        @Schema(description = "Тип кузова для погрузки", example = "Фургон")
        private String bodyType;

        @NotNull
        @Schema(description = "Габариты груза")
        private Dimensions dimensions;

        @NotNull
        @Schema(description = "Маршрут груза")
        private Route route;

        @NotNull
        @Schema(description = "Стоимость груза", example = "999.99")
        private BigDecimal price;

        @NotNull
        @Schema(description = "Тип оплаты", example = "Карта")
        private String typePay;

        @NotNull
        @Schema(description = "Дата готовности груза", example = "2025-02-08")
        private LocalDate readyDate;

        @NotNull
        @Schema(description = "Ожидаемая дата доставки", example = "2025-02-15")
        private LocalDate deliveryDate;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Габариты груза")
    public static class Dimensions {
        @NotNull
        @Schema(description = "Длина груза в см", example = "200")
        private Integer length;

        @NotNull
        @Schema(description = "Ширина груза в см", example = "100")
        private Integer width;

        @NotNull
        @Schema(description = "Высота груза в см", example = "150")
        private Integer height;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Маршрут груза")
    public static class Route {
        @NotNull
        @Schema(description = "Пункт отправления", example = "Москва")
        private String from;

        @NotNull
        @Schema(description = "Пункт назначения", example = "Санкт-Петербург")
        private String to;
    }
}
