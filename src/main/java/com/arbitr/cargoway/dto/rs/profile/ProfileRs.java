package com.arbitr.cargoway.dto.rs.profile;

import com.arbitr.cargoway.dto.general.profile.CompanyDetails;
import com.arbitr.cargoway.dto.general.profile.ContactDataDetails;
import com.arbitr.cargoway.dto.general.profile.LegalTypeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO-класс с информацией о профиле пользователя")
public class ProfileRs {
    @Schema(description = "id профиля")
    private UUID id;

    @Schema(description = "Правовая форма профиля (физ. или юр. лицо)")
    private LegalTypeDto legalType;

    @Schema(description = "Рейтинг пользователей")
    private Double userRating;

    @Schema(description = "Рейтинг системы")
    private Double systemRating;

    @Schema(description = "Статус профиля")
    private Boolean activated;

    @Schema(description = "Данные учетной записи")
    private UserRs userData;

    @Schema(description = "Данные по юр. лицу")
    private CompanyDetails company;

    @Schema(description = "Данные по физ. лицу")
    private IndividualRs individual;

    @Schema(description = "Контактные данные для связи")
    private ContactDataDetails contactData;
}
