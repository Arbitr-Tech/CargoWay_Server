package com.arbitr.cargoway.dto.general.profile;

import com.arbitr.cargoway.dto.general.Photo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Schema(description = "DTO для физ. лица")
public class IndividualDetails {
    @Schema(description = "Полное имя", example = "Иван Иванов")
    @Size(max = 255, message = "Полное имя не должно превышать 255 символов")
    private String fullName;

    @Schema(description = "Номер паспорта", example = "1234 567890")
    @Pattern(regexp = "\\d{4} \\d{6}", message = "Номер паспорта должен быть в формате 'XXXX XXXXXX'")
    private String passportNumber;

    @Schema(description = "Кем выдан паспорт", example = "УФМС России по г. Москве")
    @Size(max = 255, message = "Информация о выдаче паспорта не должна превышать 255 символов")
    private String issuedBy;

    @Schema(description = "Дата выдачи паспорта", example = "2020-01-15")
    @PastOrPresent(message = "Дата выдачи не может быть в будущем")
    private LocalDate issueDate;

    @Schema(description = "Код подразделения", example = "770-001")
    @Pattern(regexp = "\\d{3}-\\d{3}", message = "Код подразделения должен быть в формате XXX-XXX")
    private String departmentCode;

    @Schema(description = "Адрес регистрации", example = "г. Москва, ул. Ленина, д. 10")
    @Size(max = 500, message = "Адрес регистрации не должен превышать 500 символов")
    private String registrationAddress;

    @Schema(description = "Фотографии паспорта")
    private List<Photo> photos;
}
