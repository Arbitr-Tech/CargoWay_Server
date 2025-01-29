package com.arbitr.cargoway.mapper;

import com.arbitr.cargoway.dto.CompanyDto;
import com.arbitr.cargoway.dto.IndividualDto;
import com.arbitr.cargoway.dto.rq.SignUpRequest;
import com.arbitr.cargoway.entity.Company;
import com.arbitr.cargoway.entity.Individual;
import com.arbitr.cargoway.entity.security.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User buildUserFrom(SignUpRequest signUpRequest);

    Individual buildIndividualFrom(IndividualDto individualDto);

    Company buildCompanyFrom(CompanyDto companyDto);
}
