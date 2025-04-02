package com.arbitr.cargoway.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "mail")
public class MailProperties {

    private String domain;

    private String notificationSender;

    private int maxRetries;

    private int retryInterval;

}

