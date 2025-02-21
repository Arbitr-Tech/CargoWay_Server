package com.arbitr.cargoway.dto.rs.transport;

import com.arbitr.cargoway.dto.rs.ImageRef;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Schema(description = "Детальная информация о транспорте")
public class TransportDetailsRs {

    @Schema(description = "Идентификатор транспорта", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(description = "Марка транспорта", example = "Volvo")
    private String brand;

    @Schema(description = "Модель транспорта", example = "FH16")
    private String model;

    @Schema(description = "Год выпуска транспорта", example = "2020")
    private Year year;

    @Schema(description = "Номер транспорта", example = "A123BC")
    private String transportNumber;

    @Schema(description = "Грузоподъемность транспорта (в кг)", example = "10000")
    private Integer liftingCapacity;

    @Schema(description = "Тип загрузки", example = "Верхняя")
    private String loadType;

    @Schema(description = "Тип выгрузки", example = "Задняя")
    private String unloadType;

    @Schema(description = "Тип кузова", example = "Тент")
    private String bodyType;

    @Schema(description = "Маршрут транспорта")
    private Route route;

    @Schema(description = "Детали прицепа")
    private TrailerDetails trailerDetails;

    @Schema(description = "Цена за перевозку", example = "5000.00")
    private BigDecimal price;

    @Schema(description = "Тип оплаты", example = "Предоплата")
    private String typePay;

    @Schema(description = "Дата готовности транспорта", example = "2025-02-08")
    private LocalDate readyDate;

    @Schema(description = "Статус видимости транспорта", example = "PUBLIC")
    private String status;

    @Schema(description = "Ссылки на фото транспорта")
    private List<ImageRef> photos;

    @Data
    @Builder
    @Schema(description = "Маршрут транспорта")
    public static class Route {
        @Schema(description = "Место отправления", example = "Москва")
        private String from;

        @Schema(description = "Место назначения", example = "Санкт-Петербург")
        private String to;
    }

    @Data
    @Builder
    @Schema(description = "Детали прицепа")
    public static class TrailerDetails {
        @Schema(description = "Длина прицепа (в метрах)", example = "10")
        private Integer length;

        @Schema(description = "Ширина прицепа (в метрах)", example = "2.5")
        private Integer width;

        @Schema(description = "Высота прицепа (в метрах)", example = "3")
        private Integer height;

        @Schema(description = "Объем прицепа (в м³)", example = "30")
        private Integer volume;

        @Schema(description = "Номер прицепа", example = "A123BC")
        private String trailerNumber;
    }
}
