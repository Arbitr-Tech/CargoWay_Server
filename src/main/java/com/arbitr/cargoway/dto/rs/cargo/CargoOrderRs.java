package com.arbitr.cargoway.dto.rs.cargo;

import com.arbitr.cargoway.dto.general.cargo.CargoDto;
import com.arbitr.cargoway.dto.general.cargo.CargoOrderStatusDto;
import com.arbitr.cargoway.dto.general.profile.ProfileShortDto;
import com.arbitr.cargoway.dto.rs.transport.TransportShortDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Детальная информация о заказе груза")
public class CargoOrderRs {
    @Schema(description = "Идентификатор заказа", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(description = "Статус видимости заказа", example = "PUBLIC")
    private CargoOrderStatusDto visibilityStatus;

    @Schema(description = "Дата и время начала выполнения заказа", example = "2025-04-15T10:00:00")
    private LocalDateTime startExecution;

    @Schema(description = "Дата и время окончания выполнения заказа", example = "2025-04-16T18:00:00")
    private LocalDateTime endExecution;

    @Schema(description = "Дата создания заказа", example = "2025-04-14T12:00:00")
    private LocalDateTime orderCreatedAt;

    @Schema(description = "Дата последнего обновления заказа", example = "2025-04-14T12:30:00")
    private LocalDateTime orderUpdatedAt;

    @Schema(description = "Краткая информация о профиле исполнителя")
    private ProfileShortDto executor;

    @Schema(description = "Краткая информация о транспорте исполнителя для текущего заказа")
    private TransportShortDto executorTransport;

    @Schema(description = "Краткая информация о профиле владельца груза")
    private ProfileShortDto owner;

    @Schema(description = "Детали груза")
    private CargoDto cargo;

    @Schema(description = "Список откликов")
    private List<CargoOrderResponseDto> responses;

    // TODO: добавить возвращение фотографий
}
