package com.arbitr.cargoway.mapper;

import com.arbitr.cargoway.dto.CompanyDetails;
import com.arbitr.cargoway.dto.IndividualDetails;
import com.arbitr.cargoway.dto.CargoWayRole;
import com.arbitr.cargoway.dto.rq.SignUpRequest;
import com.arbitr.cargoway.dto.rs.profile.UserRs;
import com.arbitr.cargoway.entity.Company;
import com.arbitr.cargoway.entity.Individual;
import com.arbitr.cargoway.entity.security.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface UserMapper {
    @Mapping(source = "role", target = "role", qualifiedByName = "mapRole")
    User buildUserFrom(SignUpRequest signUpRequest);

    Individual buildIndividualFrom(IndividualDetails individualDetails);

    Company buildCompanyFrom(CompanyDetails companyDetails);

    User buildUserFrom(UserRs userRs);

    UserRs buildUserRsFrom(User user);

    @Named("mapRole")
    default String mapRole(CargoWayRole role) {
        return role.name();
    }
}
