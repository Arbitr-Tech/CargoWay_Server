package com.arbitr.cargoway.mapper;

import com.arbitr.cargoway.dto.CompanyDetails;
import com.arbitr.cargoway.dto.ContactDataDetails;
import com.arbitr.cargoway.dto.IndividualDetails;
import com.arbitr.cargoway.dto.rs.profile.ProfileRs;
import com.arbitr.cargoway.dto.rs.profile.ReviewRs;
import com.arbitr.cargoway.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ProfileMapper {

    @Mapping(source = "reviews", target = "reviews", qualifiedByName = "mapReviewToReviewRs")
    @Mapping(source = "user", target = "userData")
    ProfileRs buildProfileRsFrom(Profile profile);

    Individual buildIndividualFrom(IndividualDetails individualDetails);

    IndividualDetails buildIndividualFrom(Individual individual);

    Company buildCompanyFrom(CompanyDetails companyDetails);

    CompanyDetails buildCompanyDetailsFrom(Company company);

    ContactData buildContactDataFrom(ContactDataDetails contactDataDetails);

    void updateContactData(@MappingTarget ContactData target, ContactData source);

    void updateIndividual(@MappingTarget Individual target, Individual source);

    void updateCompany(@MappingTarget Company target, Company source);

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
