package com.arbitr.cargoway.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "DTO для физ. лица")
public class IndividualDto {
    @Schema(description = "Полное имя", example = "Иван Иванов")
    @Size(max = 100, message = "Полное имя не должно превышать 100 символов")
    private String fullname;

    @Schema(description = "Номер паспорта", example = "123456")
    @Positive(message = "Номер паспорта должен быть положительным числом")
    private Integer passportNum;

    @Schema(description = "Серия паспорта", example = "7890")
    @Positive(message = "Серия паспорта должна быть положительным числом")
    private Integer passportSeries;

    @Schema(description = "Кем выдан паспорт", example = "УФМС России по г. Москве")
    @Size(max = 255, message = "Информация о выдаче паспорта не должна превышать 255 символов")
    private String whoGive;

    @Schema(description = "Номер телефона", example = "+7 (999) 123-45-67")
    @Pattern(regexp = "^\\+?\\(?\\d{1,4}\\)?[-\\s]?\\d{1,4}[-\\s]?\\d{1,4}[-\\s]?\\d{1,4}$",
            message = "Номер телефона должен быть в формате: +7 (999) 123-45-67 или 8 (999) 123-45-67")
    private String phoneNumber;

    @Schema(description = "Код подразделения", example = "770-001")
    @Pattern(regexp = "\\d{3}-\\d{3}", message = "Код подразделения должен быть в формате XXX-XXX")
    private String departmentCode;

//    @Schema(description = "Фотографии пасспорта")
//    private List<Photo> photos;
}
