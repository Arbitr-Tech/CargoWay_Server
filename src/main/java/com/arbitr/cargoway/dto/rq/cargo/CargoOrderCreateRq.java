package com.arbitr.cargoway.dto.rq.cargo;

import com.arbitr.cargoway.dto.general.cargo.CargoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class CargoOrderCreateRq extends CargoDto {
    @Schema(description = "id фотографий груза")
    private List<UUID> imagesIds;
}
