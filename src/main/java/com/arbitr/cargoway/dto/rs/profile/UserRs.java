package com.arbitr.cargoway.dto.rs.profile;

import com.arbitr.cargoway.dto.general.profile.RoleDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO-класс с информацией об общих данных пользователя")
public class UserRs {
    @Schema(description = "Логин пользователя")
    private String username;

    @Schema(description = "Электронная почта")
    private String email;

    @Schema(description = "Роль профиля")
    private RoleDto role;
}
