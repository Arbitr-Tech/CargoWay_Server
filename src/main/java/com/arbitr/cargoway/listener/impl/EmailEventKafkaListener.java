package com.arbitr.cargoway.listener.impl;

import com.arbitr.cargoway.event.EmailDto;
import com.arbitr.cargoway.listener.EmailEventListener;
import com.arbitr.cargoway.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailEventKafkaListener implements EmailEventListener {
    private final EmailSenderService emailSenderService;

    @KafkaListener(
            id = "EmailListener",
            topics = "#{@kafkaCustomProperties.emailTopic}",
            groupId = "#{@kafkaCustomProperties.emailTopicProcessorGroup}",
            containerFactory = "emailKafkaListenerContainerFactory"
    )
    @Override
    public void handleEmailEvent(EmailDto emailDto) {
        emailSenderService.sendEmail(emailDto);
    }
}
