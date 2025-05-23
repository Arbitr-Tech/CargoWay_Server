package com.arbitr.cargoway.dto.rq.trailer;

import com.arbitr.cargoway.dto.general.TrailerDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Schema(description = "Для создания сущности прицепа")
public class TrailerCreateRq extends TrailerDto {
    @Schema(description = "Идентификаторы изображений полуприцепа")
    private List<UUID> imagesIds;
}
