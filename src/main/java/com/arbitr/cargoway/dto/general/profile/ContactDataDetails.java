package com.arbitr.cargoway.dto.general.profile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO-класс с информацией о доступных контактах")
public class ContactDataDetails {
    @Schema(description = "Ссылка на профиль в telegram")
    private String telegramLink;

    @Schema(description = "Ссылка на профиль в whatsapp")
    private String whatsappLink;

    @Schema(description = "Номер телефона")
    private String phoneNumber;
}
