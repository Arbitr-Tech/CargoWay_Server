package com.arbitr.cargoway.controller;

import com.arbitr.cargoway.dto.rq.SignInRequest;
import com.arbitr.cargoway.dto.rq.SignUpRequest;
import com.arbitr.cargoway.dto.rs.AuthenticationResponse;
import com.arbitr.cargoway.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Управление аутентификацией и регистрацией пользователей")
public class AuthController {
    private final AuthService authService;

    @PostMapping("register/")
    @Operation(
            summary = "Регистрация пользователя",
            description = "Регистрация нового пользователя с указанием типа профиля (индивидуальный или компания)."
    )
    public AuthenticationResponse register(
            @Parameter(description = "Тип профиля пользователя (individual или company)", required = true)
            @RequestParam("profile_type") String profileType,
            @RequestBody @Valid SignUpRequest signUpRequest) {

        return authService.register(profileType, signUpRequest);
    }

    @PostMapping("login/")
    @Operation(
            summary = "Авторизация пользователя",
            description = "Авторизация пользователя по почте и паролю"
    )
    public AuthenticationResponse login(
        @RequestBody @Valid SignInRequest signInRequest
    ) {
        return authService.login(signInRequest);
    }
}
