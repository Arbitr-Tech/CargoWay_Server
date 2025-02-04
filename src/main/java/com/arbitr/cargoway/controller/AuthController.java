package com.arbitr.cargoway.controller;

import com.arbitr.cargoway.dto.rq.SignInRequest;
import com.arbitr.cargoway.dto.rq.SignUpRequest;
import com.arbitr.cargoway.dto.rs.AuthenticationResponse;
import com.arbitr.cargoway.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Управление аутентификацией и регистрацией пользователей")
public class AuthController {
    private final AuthService authService;

    @Operation(
            summary = "Регистрация пользователя",
            description = "Регистрация нового пользователя с указанием типа профиля (индивидуальный или компания)."
    )
    @PostMapping("register/")
    public AuthenticationResponse register(
            @Parameter(description = "Тип профиля пользователя (individual или company)", required = true)
            @RequestParam("profile_type") String profileType,
            @RequestBody @Valid SignUpRequest signUpRequest,
            HttpServletResponse response
    ) {

        return authService.register(profileType, signUpRequest, response);
    }

    @Operation(
            summary = "Авторизация пользователя",
            description = "Авторизация пользователя по почте и паролю"
    )
    @PostMapping("login/")
    public AuthenticationResponse login(
        @RequestBody @Valid SignInRequest signInRequest,
        HttpServletResponse response
    ) {
        return authService.login(signInRequest, response);
    }

    @Operation(
            summary = "Обновление токена доступа",
            description = "Обновление токена доступа с помощью refresh-токена"
    )
    @PostMapping("refresh-token/")
    AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return authService.refreshToken(request, response);
    }
}
