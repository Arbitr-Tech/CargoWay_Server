package com.arbitr.cargoway.dto.rs.trailer;

import com.arbitr.cargoway.dto.general.TrailerDto;
import com.arbitr.cargoway.dto.rs.FileRs;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TrailerRs extends TrailerDto {
    @Schema(description = "Идентификатор трейлера")
    private UUID id;

    @Schema(description = "Изображения полуприцепа")
    private List<FileRs> images;
}
