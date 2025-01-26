package com.arbitr.cargoway.service.impl;

import com.arbitr.cargoway.dto.CompanyDto;
import com.arbitr.cargoway.dto.IndividualDto;
import com.arbitr.cargoway.dto.rq.SignUpRequest;
import com.arbitr.cargoway.dto.rs.JwtAuthenticationResponse;
import com.arbitr.cargoway.entity.Individual;
import com.arbitr.cargoway.entity.User;
import com.arbitr.cargoway.exception.BadRequestException;
import com.arbitr.cargoway.mapper.UserMapper;
import com.arbitr.cargoway.repository.CompanyRepository;
import com.arbitr.cargoway.repository.IndividualRepository;
import com.arbitr.cargoway.repository.ProfileRepository;
import com.arbitr.cargoway.repository.UserRepository;
import com.arbitr.cargoway.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final CompanyRepository companyRepository;
    private final IndividualRepository individualRepository;

    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public JwtAuthenticationResponse register(String profileType, SignUpRequest signUpRequest) {
        if (profileType.equals("individual")) {
            return this.registerIndividual(signUpRequest.getIndividual());
        }

        if (profileType.equals("company")) {
            return this.registerCompany(signUpRequest.getCompany());
        }

        throw new BadRequestException("Указан неверный тип профиля!");
    }


    private JwtAuthenticationResponse registerCompany(CompanyDto companyDto) {
        User newUser = userMapper.buildUserFrom(signUpRequest);
        newUser.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        profile.setUser(user);
        profile.setCompany(company);

        user.setProfile(profile);
        company.setProfile(profile);

        userRepository.save(user);
        companyRepository.save(company);
        profileRepository.save(profile);
    }


    private JwtAuthenticationResponse registerIndividual(IndividualDto individualDto) {
        User newUser = userMapper.buildUserFrom(signUpRequest);
        newUser.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        Individual individual = userMapper.buildIndividualFrom(signUpRequest.getIndividual());
    }

}
