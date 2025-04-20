package com.arbitr.cargoway.service;

import com.arbitr.cargoway.event.EmailDto;

/**
 * Сервис для отправки сообщения по электронной почте
 */
public interface EmailSenderService {
    /**
     * Метод для отправки сообщения
     * @param emailDto - данные сообщения
     */
    void sendEmail(EmailDto emailDto);
}
