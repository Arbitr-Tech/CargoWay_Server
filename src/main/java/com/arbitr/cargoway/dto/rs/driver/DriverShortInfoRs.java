package com.arbitr.cargoway.dto.rs.driver;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ с данными водителя, включая идентификатор")
public class DriverShortInfoRs {
    @Schema(description = "Идентификатор водителя в БД")
    private UUID id;

    @Schema(description = "ФИО водителя")
    private String fullName;
}
