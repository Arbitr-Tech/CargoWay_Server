package com.arbitr.cargoway.mapper;

import com.arbitr.cargoway.dto.general.profile.ProfileShortDto;
import com.arbitr.cargoway.dto.rs.profile.ProfileRs;
import com.arbitr.cargoway.dto.rs.profile.ReviewRs;
import com.arbitr.cargoway.entity.*;
import com.arbitr.cargoway.entity.enums.LegalType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ProfileMapper {

    @Mapping(source = "reviews", target = "reviews", qualifiedByName = "mapReviewToReviewRs")
    @Mapping(source = "user", target = "userData")
    ProfileRs buildProfileRsFrom(Profile profile);

    @Named("toProfileShortDto")
    default ProfileShortDto toProfileShortDto(Profile profile) {
        if (profile == null) {
            return null;
        }

        LegalType profileLegalType = profile.getLegalType();

        String profileName;
        if (profileLegalType == LegalType.COMPANY) {
            profileName = profile.getCompany().getName();
        } else {
            profileName = profile.getIndividual().getFullName();
        }

        return ProfileShortDto.builder()
                .id(profile.getId())
                .profileName(profileName)
                .systemRating(profile.getSystemRating())
                .userRating(profile.getUserRating())
                .build();
    }

    @Named("mapReviewToReviewRs")
    default List<ReviewRs> mapReviews(List<Review> reviews) {
        if (reviews == null) return Collections.emptyList();
        return reviews.stream()
                .map(review -> ReviewRs.builder()
                        .id(review.getId())
                        .title(review.getTitle())
                        .comment(review.getComment())
                        .rating(review.getRating())
                        .build())
                .toList();
    }
}
