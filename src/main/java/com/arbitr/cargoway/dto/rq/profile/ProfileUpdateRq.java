package com.arbitr.cargoway.dto.rq.profile;

import com.arbitr.cargoway.dto.general.profile.ContactDataDetails;
import com.arbitr.cargoway.dto.general.profile.CompanyDetails;
import com.arbitr.cargoway.dto.general.profile.IndividualDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос на обновление профиля пользователя")
public class ProfileUpdateRq {

    @Schema(description = "Контактные данные для связи")
    private ContactDataDetails contactData;

    @Schema(description = "Данные по юр. лицу (если профиль юр. лица)")
    private CompanyDetails company;

    @Schema(description = "Данные по физ. лицу (если профиль физ. лица)")
    private IndividualDetails individual;
}
