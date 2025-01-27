package com.arbitr.cargoway.config;

import com.arbitr.cargoway.entity.User;
import io.jsonwebtoken.Claims;                // Для работы с данными внутри токена
import io.jsonwebtoken.Jwts;                    // Для создания, парсинга и валидации токенов
import io.jsonwebtoken.JwtException;            // Для обработки ошибок JWT
import io.jsonwebtoken.SignatureAlgorithm;      // Для указания алгоритма подписи
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms}")
    private long jwtExpirationMs;

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}

