package com.arbitr.cargoway.mapper;

import com.arbitr.cargoway.dto.general.TrailerDto;
import com.arbitr.cargoway.dto.rs.trailer.TrailerRs;
import com.arbitr.cargoway.dto.rs.trailer.TrailerShortInfoRs;
import com.arbitr.cargoway.entity.Trailer;
import org.mapstruct.Mapper;

@Mapper
public interface TrailerMapper {
    Trailer toEntity(TrailerDto trailerDto);
    TrailerRs toRsDto(Trailer trailer);
    TrailerShortInfoRs toShortRsDto(Trailer trailer);
}
