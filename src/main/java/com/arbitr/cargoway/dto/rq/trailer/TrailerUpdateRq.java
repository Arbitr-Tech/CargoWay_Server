package com.arbitr.cargoway.dto.rq.trailer;

import com.arbitr.cargoway.dto.general.TrailerDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Schema(description = "DTO для обновления данных сущности прицепа")
public class TrailerUpdateRq extends TrailerDto {
    @Schema(description = "Идентификаторы изображений полуприцепа")
    private List<UUID> imagesIds;
}
