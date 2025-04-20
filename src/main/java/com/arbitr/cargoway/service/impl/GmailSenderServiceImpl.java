package com.arbitr.cargoway.service.impl;

import com.arbitr.cargoway.event.EmailDto;
import com.arbitr.cargoway.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GmailSenderServiceImpl implements EmailSenderService {
    private final MailSender mailSender;
    private final SimpleMailMessage message;

    @Override
    public void sendEmail(EmailDto emailDto) {
        message.setTo(emailDto.getToEmail());
        message.setSubject(emailDto.getSubject());
        message.setText(emailDto.getBody());

        mailSender.send(message);
    }
}
