package com.arbitr.cargoway.dto.rs.transport;

import com.arbitr.cargoway.dto.general.EmbeddedTrailerDto;
import com.arbitr.cargoway.dto.rs.driver.DriverRs;
import com.arbitr.cargoway.dto.rs.trailer.TrailerRs;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Детальная информация о транспорте")
public class TransportRs {
    @Schema(description = "Идентификатор транспорта", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

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

    @Schema(description = "Данные о водителе транспорта")
    private DriverRs driver;

    @Schema(description = "Данные о встроенном прицепе")
    private EmbeddedTrailerDto embeddedTrailer;

    @Schema(description = "Информация о прицепах транспорта")
    private List<TrailerRs> trailers;

//    @Schema(description = "ID фотографий транспорта")
//    private List<UUID> photos;
}
