package com.arbitr.cargoway.dto.rq.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO-класс для передачи нового пароля")
public class ResetPasswordRq {
    @Schema(description = "Новый пароль")
    private String newPassword;
}
