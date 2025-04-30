package com.arbitr.cargoway.dto.rs.driver;

import com.arbitr.cargoway.dto.general.DriverDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
}
