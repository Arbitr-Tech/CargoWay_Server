package com.arbitr.cargoway.mapper;

import com.arbitr.cargoway.dto.CompanyDto;
import com.arbitr.cargoway.dto.IndividualDto;
import com.arbitr.cargoway.dto.rq.CargoWayRole;
import com.arbitr.cargoway.dto.rq.SignUpRequest;
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

    Individual buildIndividualFrom(IndividualDto individualDto);

    Company buildCompanyFrom(CompanyDto companyDto);

    @Named("mapRole")
    default String mapRole(CargoWayRole role) {
        return role.name();
    }
}
