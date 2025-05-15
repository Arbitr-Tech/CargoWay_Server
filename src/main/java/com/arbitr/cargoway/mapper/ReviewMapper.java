package com.arbitr.cargoway.mapper;

import com.arbitr.cargoway.dto.general.ReviewDto;
import com.arbitr.cargoway.dto.rs.review.ReviewRs;
import com.arbitr.cargoway.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProfileMapper.class})
public interface ReviewMapper {
    @Mapping(source = "commentator", target = "commentator", qualifiedByName = "toProfileShortDto")
    ReviewRs toRsDto(Review review);
    Review toEntity(ReviewDto reviewDto);
}
