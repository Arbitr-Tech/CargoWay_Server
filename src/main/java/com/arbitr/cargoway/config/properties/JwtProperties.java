package com.arbitr.cargoway.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    /**
     * Секретный ключ для подписи JWT.
     * Значение загружается из переменной окружения JWT_SECRET_KEY.
     */
    private String secretKey;

    /**
     * Параметры access-токена, включая время его жизни.
     */
    private TokenProperties accessToken;

    /**
     * Параметры refresh-токена, включая время его жизни.
     */
    private TokenProperties refreshToken;

    /**
     * Вложенный класс, содержащий настройки токенов (время жизни).
     */
    @Data
    public static class TokenProperties {

        /**
         * Время жизни токена в миллисекундах.
         * Значение загружается из переменной окружения ACCESS_TOKEN_EXPIRATION или REFRESH_TOKEN_EXPIRATION.
         */
        private long expiration;
    }
}
