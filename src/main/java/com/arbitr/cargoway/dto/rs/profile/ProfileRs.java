package com.arbitr.cargoway.dto.rs.profile;

import com.arbitr.cargoway.dto.CompanyDetails;
import com.arbitr.cargoway.dto.ContactDataDetails;
import com.arbitr.cargoway.dto.IndividualDetails;
import com.arbitr.cargoway.dto.LegalTypeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO-класс с информацией о профиле пользователя")
public class ProfileRs {
    @Schema(description = "Правовая форма профиля (физ. или юр. лицо)")
    LegalTypeDto legalType;

    @Schema(description = "Рейтинг пользователей")
    private Double userRating;

    @Schema(description = "Рейтинг системы")
    private Double systemRating;

    @Schema(description = "Статус профиля")
    private Boolean activated;

    @Schema(description = "Данные учетной записи")
    UserRs userData;

    @Schema(description = "Данные по юр. лицу")
    CompanyDetails company;

    @Schema(description = "Данные по физ. лицу")
    IndividualDetails individual;

    @Schema(description = "Контактные данные для связи")
    ContactDataDetails contactData;

    @Schema(description = "Список отзывов")
    List<ReviewRs> reviews;
}
