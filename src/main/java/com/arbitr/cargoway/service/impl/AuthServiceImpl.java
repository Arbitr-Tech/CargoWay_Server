package com.arbitr.cargoway.service.impl;

import com.arbitr.cargoway.config.security.JwtService;
import com.arbitr.cargoway.dto.rq.SignInRequest;
import com.arbitr.cargoway.dto.rq.SignUpRequest;
import com.arbitr.cargoway.dto.rs.AuthenticationResponse;
import com.arbitr.cargoway.entity.Company;
import com.arbitr.cargoway.entity.Individual;
import com.arbitr.cargoway.entity.Profile;
import com.arbitr.cargoway.entity.security.User;
import com.arbitr.cargoway.exception.BadRequestException;
import com.arbitr.cargoway.exception.NotFoundException;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public AuthenticationResponse register(String profileType, SignUpRequest signUpRequest) {
        return switch (profileType.toLowerCase()) {
            case "individual" -> registerIndividual(signUpRequest);
            case "company" -> registerCompany(signUpRequest);
            default -> throw new BadRequestException("Указан неверный тип профиля!");
        };
    }

    @Override
    public AuthenticationResponse login(SignInRequest signInRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getEmail(),
                        signInRequest.getPassword()
                )
        );

        User user = userRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new NotFoundException("Участник с почтой %s не был найден!".formatted(signInRequest.getEmail())));

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return new AuthenticationResponse(jwtToken, refreshToken);
    }

    private AuthenticationResponse registerCompany(SignUpRequest signUpRequest) {
        User newUser = createUser(signUpRequest);
        Company newCompany = userMapper.buildCompanyFrom(signUpRequest.getCompany());
        Profile newProfile = createProfile(newUser, newCompany, null);

        saveEntities(newUser, newProfile, newCompany, null);

        return generateAuthResponse(newUser);
    }

    private AuthenticationResponse registerIndividual(SignUpRequest signUpRequest) {
        User newUser = createUser(signUpRequest);
        Individual newIndividual = userMapper.buildIndividualFrom(signUpRequest.getIndividual());
        Profile newProfile = createProfile(newUser, null, newIndividual);

        saveEntities(newUser, newProfile, null, newIndividual);

        return generateAuthResponse(newUser);
    }

    private User createUser(SignUpRequest signUpRequest) {
        User user = userMapper.buildUserFrom(signUpRequest);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        return user;
    }

    private Profile createProfile(User user, Company company, Individual individual) {
        Profile profile = new Profile();
        profile.setUser(user);
        if (company != null) {
            profile.setCompany(company);
            company.setProfile(profile);
        }
        if (individual != null) {
            profile.setIndividual(individual);
            individual.setProfile(profile);
        }
        user.setProfile(profile);
        return profile;
    }

    private void saveEntities(User user, Profile profile, Company company, Individual individual) {
        profileRepository.save(profile);
        if (company != null) companyRepository.save(company);
        if (individual != null) individualRepository.save(individual);
        userRepository.save(user);
    }

    private AuthenticationResponse generateAuthResponse(User user) {
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        return new AuthenticationResponse(accessToken, refreshToken);
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
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
        if (validUserTokens.isEmpty()) return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        final String refreshToken = authHeader.substring(7);
        final String userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = userRepository.findByEmail(userEmail).orElseThrow();
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

