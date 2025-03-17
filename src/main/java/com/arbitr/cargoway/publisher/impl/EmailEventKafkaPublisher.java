package com.arbitr.cargoway.publisher.impl;

import com.arbitr.cargoway.config.properties.KafkaCustomProperties;
import com.arbitr.cargoway.dto.internal.email.EmailDto;
import com.arbitr.cargoway.publisher.EmailEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailEventKafkaPublisher implements EmailEventPublisher {
    private final KafkaTemplate<String, EmailDto> kafkaTemplate;
    private final KafkaCustomProperties kafkaCustomProperties;

    @Override
    public void sendEmailEvent(EmailDto emailDto) {
        kafkaTemplate.send(kafkaCustomProperties.getEmailTopic(), emailDto);
    }
}
