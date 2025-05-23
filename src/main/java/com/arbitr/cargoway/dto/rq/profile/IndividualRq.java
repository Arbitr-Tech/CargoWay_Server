package com.arbitr.cargoway.dto.rq.profile;

import com.arbitr.cargoway.dto.general.profile.IndividualDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Schema(description = "DTO-сущность с данными физ лица, дополненная id фото")
public class IndividualRq extends IndividualDto {
    @Schema(description = "Идентификаторы изображений паспорта")
    private List<UUID> imagesIds;
}
