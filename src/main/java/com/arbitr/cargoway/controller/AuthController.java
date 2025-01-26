package com.arbitr.cargoway.controller;

import com.arbitr.cargoway.dto.rq.SignUpRequest;
import com.arbitr.cargoway.dto.rs.JwtAuthenticationResponse;
import com.arbitr.cargoway.exception.BadRequestException;
import com.arbitr.cargoway.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public JwtAuthenticationResponse register(@RequestParam("profile_type") String profileType, @RequestBody @Valid SignUpRequest signUpRequest) {
        return authService.register(profileType, signUpRequest);
    }
}
