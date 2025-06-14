package com.arbitr.cargoway.controller;

import com.arbitr.cargoway.dto.rq.SignInRequest;
import com.arbitr.cargoway.dto.rq.SignUpRequest;
import com.arbitr.cargoway.dto.rq.auth.RecoveryEmailRq;
import com.arbitr.cargoway.dto.rq.auth.ResetPasswordRq;
import com.arbitr.cargoway.service.AuthService;
import com.arbitr.cargoway.service.PasswordManagementService;
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
    private final PasswordManagementService passwordManagementService;

    @PostMapping("register/")
    public void register(@RequestBody @Valid SignUpRequest signUpRequest,
                                           HttpServletResponse response) {
        authService.register(signUpRequest, response);
    }

    @PostMapping("login/")
    public void login(@RequestBody @Valid SignInRequest signInRequest, HttpServletResponse response) {
        authService.login(signInRequest, response);
    }

    @PostMapping("refresh-token/")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        authService.refreshToken(request, response);
    }

    @PostMapping("password-recovery/")
    public void passwordRecovery(@RequestBody @Valid RecoveryEmailRq recoveryEmailRq) {
        passwordManagementService.recoveryUserPassword(recoveryEmailRq);
    }

    @PostMapping("reset-password/")
    public void resetPassword(@RequestParam("token") String resetToken,
                              @RequestBody @Valid ResetPasswordRq resetPasswordRq) {
        passwordManagementService.resetPassword(resetToken, resetPasswordRq);
    }
}
