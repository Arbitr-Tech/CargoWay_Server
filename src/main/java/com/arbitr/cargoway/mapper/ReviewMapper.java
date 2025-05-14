package com.arbitr.cargoway.mapper;

import com.arbitr.cargoway.dto.general.ReviewDto;
import com.arbitr.cargoway.dto.rs.review.ReviewRs;
import com.arbitr.cargoway.entity.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProfileMapper.class})
public interface ReviewMapper {
    ReviewRs toRsDto(Review review);
    Review toEntity(ReviewDto reviewDto);
}
