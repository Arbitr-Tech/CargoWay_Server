package com.arbitr.cargoway.dto.general;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO водителя с информацией о правах")
public class DriverDto {
    @NotBlank(message = "ФИО водителя не может быть пустым")
    @Schema(description = "ФИО водителя")
    private String fullName;

    @NotBlank(message = "Категория прав не может быть пустой")
    @Pattern(regexp = "^(A|A1|B|B1|BE|C|C1|CE|C1E|D|D1|DE|D1E|M|Tb|Tm)(,(A|A1|B|B1|BE|C|C1|CE|C1E|D|D1|DE|D1E|M|Tb|Tm))*$",
            message = "Недопустимая категория прав")
    @Schema(description = "Категория прав")
    private String licenseCategory;

    @NotBlank(message = "Номер прав не может быть пустым")
    @Pattern(regexp = "^[0-9]{4}\\s[0-9]{6}$", message = "Номер прав должен соответствовать формату: 4 цифры, пробел, 6 цифр")
    @Schema(description = "Номер прав")
    private String licenseNumber;

    @NotNull(message = "Дата выдачи прав не может быть пустой")
    @PastOrPresent(message = "Дата выдачи прав не может быть в будущем")
    @Schema(description = "Дата выдачи прав")
    private LocalDate issueDate;

    @NotNull(message = "Дата окончания действия прав не может быть пустой")
    @FutureOrPresent(message = "Дата окончания действия прав не может быть в прошлом")
    @Schema(description = "Дата окончания действия прав")
    private LocalDate expirationDate;
}
