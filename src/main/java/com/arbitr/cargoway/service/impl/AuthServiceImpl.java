package com.arbitr.cargoway.service.impl;

import com.arbitr.cargoway.config.JwtProvider;
import com.arbitr.cargoway.dto.rq.SignUpRequest;
import com.arbitr.cargoway.dto.rs.JwtAuthenticationResponse;
import com.arbitr.cargoway.entity.Company;
import com.arbitr.cargoway.entity.Individual;
import com.arbitr.cargoway.entity.Profile;
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
    private final JwtProvider jwtProvider;
    private final UserMapper userMapper;

    @Override
    public JwtAuthenticationResponse register(String profileType, SignUpRequest signUpRequest) {
        if (profileType.equals("individual")) {
            return this.registerIndividual(signUpRequest);
        }

        if (profileType.equals("company")) {
            return this.registerCompany(signUpRequest);
        }

        throw new BadRequestException("Указан неверный тип профиля!");
    }


    private JwtAuthenticationResponse registerCompany(SignUpRequest signUpRequest) {
        User newUser = userMapper.buildUserFrom(signUpRequest);
        newUser.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        Company newCompany = userMapper.buildCompanyFrom(signUpRequest.getCompany());

        Profile newProfile = new Profile();

        newProfile.setUser(newUser);
        newProfile.setCompany(newCompany);

        newUser.setProfile(newProfile);
        newCompany.setProfile(newProfile);

        userRepository.save(newUser);
        profileRepository.save(newProfile);
        companyRepository.save(newCompany);

        String token = jwtProvider.generateToken(newUser);
        return new JwtAuthenticationResponse(token);
    }

    private JwtAuthenticationResponse registerIndividual(SignUpRequest signUpRequest) {
        User newUser = userMapper.buildUserFrom(signUpRequest);
        newUser.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        Individual newIndividual = userMapper.buildIndividualFrom(signUpRequest.getIndividual());

        Profile newProfile = new Profile();

        newProfile.setUser(newUser);
        newProfile.setIndividual(newIndividual);

        newUser.setProfile(newProfile);
        newIndividual.setProfile(newProfile);

        userRepository.save(newUser);
        profileRepository.save(newProfile);
        individualRepository.save(newIndividual);

        String token = jwtProvider.generateToken(newUser);
        return new JwtAuthenticationResponse(token);
    }

}
