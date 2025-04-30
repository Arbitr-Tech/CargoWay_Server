package com.arbitr.cargoway.dto.rq.transaport;

import com.arbitr.cargoway.dto.general.EmbeddedTrailerDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.Year;
import java.util.List;
import java.util.UUID;

@Data
@Schema(description = "Запрос на создание транспорта")
public class TransportCreateRq {
    @Schema(description = "Идентификатор водителя")
    private UUID driverId;

    @Schema(description = "Марка транспорта", example = "Volvo")
    @NotBlank(message = "Марка транспорта не может быть пустой")
    private String brand;

    @Schema(description = "Модель транспорта", example = "FH16")
    @NotBlank(message = "Модель транспорта не может быть пустой")
    private String model;

    @Schema(description = "Год выпуска транспорта", example = "2020")
    @NotNull(message = "Год выпуска транспорта не может быть пустым")
    private Year manufactureYear;

    @Schema(description = "Номер транспорта", example = "A123BC")
    @NotBlank(message = "Номер транспорта не может быть пустым")
    private String transportNumber;

    @Schema(description = "Детали прицепа")
    private EmbeddedTrailerDto embeddedTrailerDetails;

    @Schema(description = "Идентификаторы прицепов, которые закрепляются за транспортом")
    private List<UUID> trailersIds;

    @Schema(description = "ID фотографий транспорта")
    private List<UUID> photos;
}
