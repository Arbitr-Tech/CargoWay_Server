package com.arbitr.cargoway.publisher;

import com.arbitr.cargoway.event.EmailDto;

/**
 * Интерфейс издателя для публикации сообщений электронной почты
 */
public interface EmailEventPublisher {
    /**
     * Метод для отправки сообщения в топик
     * @param emailDto - данные о сообщении
     */
    void sendEmailEvent(EmailDto emailDto);
}
