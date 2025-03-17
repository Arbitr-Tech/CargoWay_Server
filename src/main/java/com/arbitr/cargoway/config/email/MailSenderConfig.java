package com.arbitr.cargoway.config.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

@Configuration
public class MailSenderConfig {

    @Bean
    SimpleMailMessage templateMessage() {
        return new SimpleMailMessage();
    }
}
