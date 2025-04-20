package com.arbitr.cargoway.dto.rq;

import com.arbitr.cargoway.dto.general.profile.RoleDto;
import com.arbitr.cargoway.dto.general.profile.LegalTypeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(description = "Запрос на регистрацию")
public class SignUpRequest {

    @Schema(description = "Имя пользователя", example = "user123")
    @Size(min = 5, max = 50, message = "Имя пользователя должно содержать от 5 до 50 символов")
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String username;

    @Schema(description = "Адрес электронной почты", example = "user@example.com")
    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустым")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;

    @Schema(description = "Пароль", example = "SecurePass123!")
    @Size(min = 8, max = 255, message = "Длина пароля должна быть от 8 до 255 символов")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

    @Schema(description = "Тип компании", example = "INDIVIDUAL")
    private LegalTypeDto legalType;

    @Schema(description = "Роль пользователя в системе")
    private RoleDto role;
}
