package com.arbitr.cargoway.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "DTO для компании")
public class CompanyDto {
    @Schema(description = "Название компании", example = "ООО 'Рога и Копыта'")
    @Size(max = 255, message = "Название компании не должно превышать 255 символов")
    private String name;

    @Schema(description = "ИНН", example = "7707083893")
    @Pattern(regexp = "\\d{10}|\\d{12}", message = "ИНН должен содержать 10 или 12 цифр")
    private String inn;

    @Schema(description = "ОГРН", example = "1027700132195")
    @Pattern(regexp = "\\d{13}", message = "ОГРН должен содержать 13 цифр")
    private String ogrn;

    @Schema(description = "БИК", example = "044525225")
    @Pattern(regexp = "\\d{9}", message = "БИК должен содержать 9 цифр")
    private String bic;

    @Schema(description = "Корреспондентский счет", example = "30101810400000000225")
    @Pattern(regexp = "\\d{20}", message = "Корреспондентский счет должен содержать 20 цифр")
    private String correspondentAccount;

    @Schema(description = "Дата регистрации", example = "2023-01-01")
    @Past(message = "Дата регистрации должна быть в прошлом")
    private LocalDate registrationDate;
}
