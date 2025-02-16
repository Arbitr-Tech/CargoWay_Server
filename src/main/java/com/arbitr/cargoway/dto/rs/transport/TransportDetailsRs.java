package com.arbitr.cargoway.dto.rs.transport;

import com.arbitr.cargoway.dto.rs.ImageRef;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Детальная информация о транспорте")
public class TransportDetailsRs {

    @Schema(description = "Идентификатор транспорта", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(description = "Тип транспорта", example = "Грузовик")
    private String type;

    @Schema(description = "Модель транспорта", example = "Volvo FH16")
    private String model;

    @Schema(description = "Грузоподъемность транспорта (в кг)", example = "10000")
    private Integer capacity;

    @Schema(description = "Объем транспорта (в м³)", example = "50")
    private Integer volume;

    @Schema(description = "Тип загрузки", example = "Верхняя")
    private String loadType;

    @Schema(description = "Тип выгрузки", example = "Задняя")
    private String unloadType;

    @Schema(description = "Габариты транспорта")
    private Dimensions dimensions;

    @Schema(description = "Маршрут транспорта")
    private Route route;

    @Schema(description = "Цена за перевозку", example = "5000.00")
    private BigDecimal price;

    @Schema(description = "Тип оплаты", example = "Предоплата")
    private String typePay;

    @Schema(description = "Дата готовности транспорта", example = "2025-02-08")
    private LocalDate readyDate;

    @Schema(description = "Необходимая дата доставки", example = "2025-02-15")
    private LocalDate deliveryDate;

    @Schema(description = "Статус видимости транспорта", example = "PUBLIC")
    private String status;

    @Schema(description = "Ссылки на фото транспорта")
    private List<ImageRef> photos;

    @Data
    @Schema(description = "Габариты транспорта")
    public static class Dimensions {
        @Schema(description = "Длина транспорта (в метрах)", example = "10")
        private Integer length;

        @Schema(description = "Ширина транспорта (в метрах)", example = "2.5")
        private Integer width;

        @Schema(description = "Высота транспорта (в метрах)", example = "3")
        private Integer height;
    }

    @Data
    @Schema(description = "Маршрут транспорта")
    public static class Route {
        @Schema(description = "Место отправления", example = "Москва")
        private String from;

        @Schema(description = "Место назначения", example = "Санкт-Петербург")
        private String to;
    }
}
