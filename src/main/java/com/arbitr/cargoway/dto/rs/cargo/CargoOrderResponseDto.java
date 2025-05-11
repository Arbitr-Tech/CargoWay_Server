package com.arbitr.cargoway.dto.rs.cargo;

import com.arbitr.cargoway.dto.general.profile.ProfileShortDto;
import com.arbitr.cargoway.dto.rs.transport.TransportShortDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Детальная информация об отклике на груз")
public class CargoOrderResponseDto {
    @NotNull
    @Schema(description = "Идентификатор отклика", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @NotNull
    @Schema(description = "Дата и время отклика", example = "2025-04-15T10:00:00")
    private LocalDateTime responseDateTime;

    private ProfileShortDto responderDetails;

    private TransportShortDto transportDetails;

}
