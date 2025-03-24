package com.arbitr.cargoway.service.impl;

import com.arbitr.cargoway.dto.rq.profile.ProfileUpdateRq;
import com.arbitr.cargoway.dto.rs.profile.ProfileRs;
import com.arbitr.cargoway.entity.Company;
import com.arbitr.cargoway.entity.ContactData;
import com.arbitr.cargoway.entity.Individual;
import com.arbitr.cargoway.entity.Profile;
import com.arbitr.cargoway.entity.security.User;
import com.arbitr.cargoway.mapper.ProfileMapper;
import com.arbitr.cargoway.repository.ProfileRepository;
import com.arbitr.cargoway.service.AuthService;
import com.arbitr.cargoway.service.ProfileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final AuthService authService;
    private final ProfileRepository profileRepository;

    private final ProfileMapper profileMapper;

    @Override
    public ProfileRs getProfile() {
        return profileMapper.buildProfileRsFrom(authService.getAuthenticatedUser().getProfile());
    }

    @Override
    @Transactional
    public ProfileRs updateProfile(ProfileUpdateRq profileUpdateRq) {
        User user = authService.getAuthenticatedUser();
        Profile profile = user.getProfile();

        if (profileUpdateRq.getContactData() != null) {
            ContactData newContactData = profileMapper.buildContactDataFrom(profileUpdateRq.getContactData());
            profile.setContactData(newContactData);
            newContactData.setProfile(profile);
        }
        if (profileUpdateRq.getCompany() != null) {
            Company newCompany = profileMapper.buildCompanyFrom(profileUpdateRq.getCompany());
            profile.setCompany(newCompany);
            newCompany.setProfile(profile);
        }
        if (profileUpdateRq.getIndividual() != null) {
            Individual newIndividual = profileMapper.buildIndividualFrom(profileUpdateRq.getIndividual());
            profile.setIndividual(newIndividual);
            newIndividual.setProfile(profile);
        }

        profileRepository.save(profile);

        return profileMapper.buildProfileRsFrom(profile);
    }
}
