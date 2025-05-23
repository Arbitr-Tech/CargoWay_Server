package com.arbitr.cargoway.dto.rs.profile;

import com.arbitr.cargoway.dto.general.profile.IndividualDto;
import com.arbitr.cargoway.dto.rs.FileRs;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "DTO-класс с данными физ лица для возврата")
public class IndividualRs extends IndividualDto {
    private List<FileRs> images;
}
