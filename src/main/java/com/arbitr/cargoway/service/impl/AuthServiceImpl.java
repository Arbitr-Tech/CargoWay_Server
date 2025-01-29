package com.arbitr.cargoway.service.impl;

import com.arbitr.cargoway.config.JwtService;
import com.arbitr.cargoway.dto.rq.SignInRequest;
import com.arbitr.cargoway.dto.rq.SignUpRequest;
import com.arbitr.cargoway.dto.rs.AuthenticationResponse;
import com.arbitr.cargoway.entity.Company;
import com.arbitr.cargoway.entity.Individual;
import com.arbitr.cargoway.entity.Profile;
import com.arbitr.cargoway.entity.security.User;
import com.arbitr.cargoway.exception.BadRequestException;
import com.arbitr.cargoway.mapper.UserMapper;
import com.arbitr.cargoway.repository.*;
import com.arbitr.cargoway.service.AuthService;
import com.arbitr.cargoway.entity.security.Token;
import com.arbitr.cargoway.entity.security.TokenType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final CompanyRepository companyRepository;
    private final IndividualRepository individualRepository;
    private final TokenRepository tokenRepository;

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    @Override
    public AuthenticationResponse register(String profileType, SignUpRequest signUpRequest) {
        if (profileType.equals("individual")) {
            return this.registerIndividual(signUpRequest);
        }

        if (profileType.equals("company")) {
            return this.registerCompany(signUpRequest);
        }

        throw new BadRequestException("Указан неверный тип профиля!");
    }

    @Override
    public AuthenticationResponse login(SignInRequest signInRequest) {
        return null;
    }


    private AuthenticationResponse registerCompany(SignUpRequest signUpRequest) {
        User newUser = userMapper.buildUserFrom(signUpRequest);
        newUser.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        Company newCompany = userMapper.buildCompanyFrom(signUpRequest.getCompany());

        Profile newProfile = new Profile();

        newProfile.setUser(newUser);
        newProfile.setCompany(newCompany);

        newUser.setProfile(newProfile);
        newCompany.setProfile(newProfile);

        userRepository.save(newUser);
        profileRepository.save(newProfile);
        companyRepository.save(newCompany);

        String token = jwtService.generateToken(newUser);
        return new AuthenticationResponse(token);
    }

    private AuthenticationResponse registerIndividual(SignUpRequest signUpRequest) {
        User newUser = userMapper.buildUserFrom(signUpRequest);
        newUser.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        Individual newIndividual = userMapper.buildIndividualFrom(signUpRequest.getIndividual());

        Profile newProfile = new Profile();

        newProfile.setUser(newUser);
        newProfile.setIndividual(newIndividual);

        newUser.setProfile(newProfile);
        newIndividual.setProfile(newProfile);

        userRepository.save(newUser);
        profileRepository.save(newProfile);
        individualRepository.save(newIndividual);

        String token = jwtService.generateToken(newUser);
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
