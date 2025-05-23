package com.arbitr.cargoway.dto.rs.driver;

import com.arbitr.cargoway.dto.general.DriverDto;
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
@Schema(description = "Ответ с данными водителя, включая идентификатор", allOf = DriverDto.class)
public class DriverRs extends DriverDto {
    @Schema(description = "Идентификатор водителя в БД")
    private UUID id;

    @Schema(description = "Фотографии водительского удостоверения")
    private List<FileRs> images;
}
