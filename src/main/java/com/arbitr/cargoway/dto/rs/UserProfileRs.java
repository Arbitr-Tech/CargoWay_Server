package com.arbitr.cargoway.dto.rs;

import com.arbitr.cargoway.dto.CargoWayRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO-класс с информацией о профиле пользователя")
public class UserProfileRs {
    @Schema(description = "Имя пользователя", example = "user123")
    private String username;

    @Schema(description = "Электронная почта пользователя", example = "user123@example.com")
    private String email;

    @Schema(description = "Роль пользователя", example = "ADMIN")
    private String role;
}
