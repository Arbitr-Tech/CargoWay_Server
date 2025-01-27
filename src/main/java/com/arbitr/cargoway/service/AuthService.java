package com.arbitr.cargoway.service;

import com.arbitr.cargoway.dto.rq.SignUpRequest;
import com.arbitr.cargoway.dto.rs.JwtAuthenticationResponse;


public interface AuthService {
    JwtAuthenticationResponse register(String profileType, SignUpRequest signUpRequest);
}
