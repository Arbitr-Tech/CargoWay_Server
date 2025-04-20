package com.arbitr.cargoway.listener;

import com.arbitr.cargoway.event.EmailDto;

/**
 * Интерфейс для прослушивания событий о сообщениях электронной почты
 */
public interface EmailEventListener {
    /**
     * Метод для получения сообщений и их обработке
     * @param emailDto - данные о сообщении
     */
    void handleEmailEvent(EmailDto emailDto);
}

