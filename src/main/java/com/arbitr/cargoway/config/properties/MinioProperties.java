package com.arbitr.cargoway.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {
    /**
     * URL-адрес MinIO сервера
     */
    private String endpoint;

    /**
     * Ключ доступа для аутентификации
     */
    private String accessKey;

    /**
     * Секретный ключ для аутентификации
     */
    private String secretKey;

    /**
     * Имя корзины для хранения объектов
     */
    private String bucketName;
}
