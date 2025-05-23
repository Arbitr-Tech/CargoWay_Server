package com.arbitr.cargoway.dto.rq.driver;

import com.arbitr.cargoway.dto.general.DriverDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Schema(description = "DTO-класс с данными для добавления данных о водителе")
public class DriverCreateRq extends DriverDto {
    @Schema(description = "Идентификаторы изображений ВУ")
    private List<UUID> imagesIds;
}
