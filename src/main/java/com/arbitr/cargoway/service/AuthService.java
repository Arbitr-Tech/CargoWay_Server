package com.arbitr.cargoway.service;

import com.arbitr.cargoway.dto.rq.SignInRequest;
import com.arbitr.cargoway.dto.rq.SignUpRequest;
import com.arbitr.cargoway.dto.rs.AuthenticationResponse;


public interface AuthService {
    AuthenticationResponse register(String profileType, SignUpRequest signUpRequest);
    AuthenticationResponse login(SignInRequest signInRequest);
}
