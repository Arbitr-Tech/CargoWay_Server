package com.arbitr.cargoway.dto.general;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO водителя с информацией о правах")
public class DriverDto {
    @Schema(description = "Категория прав")
    private String licenseCategory;

    @Schema(description = "Номер прав")
    private String licenseNumber;

    @Schema(description = "Дата выдачи прав")
    private LocalDate issueDate;

    @Schema(description = "Дата окончания действия прав")
    private LocalDate expirationDate;
}
