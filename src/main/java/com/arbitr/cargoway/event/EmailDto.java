package com.arbitr.cargoway.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO-класс для передачи данных о сообщение почты
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {
    private String toEmail;
    private String subject;
    private String body;
}

