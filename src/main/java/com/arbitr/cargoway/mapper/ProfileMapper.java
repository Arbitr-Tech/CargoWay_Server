package com.arbitr.cargoway.mapper;

import com.arbitr.cargoway.dto.CompanyDetails;
import com.arbitr.cargoway.dto.ContactDataDetails;
import com.arbitr.cargoway.dto.IndividualDetails;
import com.arbitr.cargoway.dto.rs.profile.ProfileRs;
import com.arbitr.cargoway.dto.rs.profile.ReviewRs;
import com.arbitr.cargoway.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ProfileMapper {

    @Mapping(source = "user", target = "userData")
    ProfileRs buildProfileRsFrom(Profile profile);

    @Mapping(source = "userData", target = "user")
    Profile buildProfileFrom(ProfileRs profileRs);

    Individual buildIndividualFrom(IndividualDetails individualDetails);

    IndividualDetails buildIndividualFrom(Individual individual);

    Company buildCompanyFrom(CompanyDetails companyDetails);

    CompanyDetails buildCompanyDetailsFrom(Company company);

    ContactData buildContactDataFrom(ContactDataDetails contactDataDetails);

//    // Преобразование Review в ReviewRs
//    @Named("mapReviews")
//    default List<ReviewRs> mapReviews(List<Review> reviews) {
//        if (reviews == null) return Collections.emptyList();
//        return reviews.stream()
//                .map(review -> new ReviewRs(
//                        review.getId(),
//                        review.getTitle(),
//                        review.getComment(),
//                        review.getRating(),
//                        review.getCreatedAt()
//                ))
//                .toList();
//    }
}
