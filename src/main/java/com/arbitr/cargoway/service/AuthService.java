package com.arbitr.cargoway.service;

import com.arbitr.cargoway.dto.rq.SignInRequest;
import com.arbitr.cargoway.dto.rq.SignUpRequest;
import com.arbitr.cargoway.entity.security.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    void register(SignUpRequest signUpRequest, HttpServletResponse response);
    void login(SignInRequest signInRequest, HttpServletResponse response);
    void refreshToken(HttpServletRequest request, HttpServletResponse response);
    User getAuthenticatedUser();
}
