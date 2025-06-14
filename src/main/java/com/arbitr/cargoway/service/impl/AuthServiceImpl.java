package com.arbitr.cargoway.service.impl;

import com.arbitr.cargoway.config.properties.JwtProperties;
import com.arbitr.cargoway.config.security.JwtService;
import com.arbitr.cargoway.event.EmailDto;
import com.arbitr.cargoway.dto.rq.SignInRequest;
import com.arbitr.cargoway.dto.rq.SignUpRequest;
import com.arbitr.cargoway.entity.Profile;
import com.arbitr.cargoway.entity.enums.LegalType;
import com.arbitr.cargoway.entity.security.User;
import com.arbitr.cargoway.exception.InvalidTokenException;
import com.arbitr.cargoway.exception.NotFoundException;
import com.arbitr.cargoway.exception.TokenValidationException;
import com.arbitr.cargoway.mapper.UserMapper;
import com.arbitr.cargoway.publisher.EmailEventPublisher;
import com.arbitr.cargoway.repository.*;
import com.arbitr.cargoway.service.AuthService;
import com.arbitr.cargoway.entity.security.Token;
import com.arbitr.cargoway.entity.security.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;
    private final EmailEventPublisher emailEventPublisher;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public void register(SignUpRequest signUpRequest, HttpServletResponse response) {
        User user = userMapper.buildUserFrom(signUpRequest);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        Profile.ProfileBuilder profileBuilder = Profile.builder();
        profileBuilder.user(user);
        profileBuilder.legalType(LegalType.valueOf(signUpRequest.getLegalType().name()));
        Profile profile = profileBuilder.build();

        user.setProfile(profile);

        userRepository.save(user);

        emailEventPublisher.sendEmailEvent(
                EmailDto.builder()
                        .toEmail(user.getEmail())
                        .subject("CargoWay: успешная регистрация")
                        .body("Уважаемый %s, вы успешно зарегистрировались на сервисе CargoWay!".formatted(user.getUsername()))
                        .build()
        );

        setupAuthResponse(user, response);
    }

    @Override
    public void login(SignInRequest signInRequest, HttpServletResponse response) {
        User user = userRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new NotFoundException("Участник с почтой %s не был найден!".formatted(signInRequest.getEmail())));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        signInRequest.getPassword()
                )
        );

        String accessTokenValue = jwtService.generateToken(user);
        String refreshTokenValue = jwtService.generateRefreshToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, accessTokenValue);

        setTokenInCookie(response, jwtProperties.getAccessToken().getName(), accessTokenValue);
        setTokenInCookie(response, jwtProperties.getRefreshToken().getName(), refreshTokenValue);
    }

    private void setupAuthResponse(User user, HttpServletResponse response) {
        String accessTokenValue = jwtService.generateToken(user);
        String refreshTokenValue = jwtService.generateRefreshToken(user);

        revokeAllUserTokens(user);

        saveUserToken(user, accessTokenValue);

        setTokenInCookie(response, jwtProperties.getAccessToken().getName(), accessTokenValue);
        setTokenInCookie(response, jwtProperties.getRefreshToken().getName(), refreshTokenValue);
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

    private void setTokenInCookie(HttpServletResponse response, String tokenName, String tokenValue) {
        Cookie cookie = new Cookie(tokenName, tokenValue);

        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(jwtProperties.getMaxAge());

        response.addCookie(cookie);
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshTokenValue = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshTokenValue = cookie.getValue();
                    break;
                }
            }
        }

        if (refreshTokenValue == null) {
            throw new InvalidTokenException("Refresh токен отсутствует в куки!");
        }

        String userName = jwtService.extractUsername(refreshTokenValue);

        if (userName == null) {
            throw new InvalidTokenException("Токен недействителен или поврежден!");
        }

        User foundUser = userRepository.findByUsername(userName)
                .orElseThrow(() -> new NotFoundException("Пользователь с именем %s не был найден!".formatted(userName)));

        if (!jwtService.isTokenValid(refreshTokenValue, foundUser)) {
            throw new TokenValidationException("Токен недействителен для пользователя: %s.".formatted(userName));
        }

        String accessTokenValue = jwtService.generateToken(foundUser);
        refreshTokenValue = jwtService.generateRefreshToken(foundUser);

        revokeAllUserTokens(foundUser);
        saveUserToken(foundUser, accessTokenValue);

        setTokenInCookie(response, jwtProperties.getAccessToken().getName(), accessTokenValue);
        setTokenInCookie(response, jwtProperties.getRefreshToken().getName(), refreshTokenValue);

    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new RuntimeException("Произошла ошибка! Пользователь не аутентифицирован!");
        }

        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Пользователь не был найден! Попробуйте еще раз."));
    }
}

