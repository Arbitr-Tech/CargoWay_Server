package com.arbitr.cargoway.service.impl;

import com.arbitr.cargoway.dto.rq.profile.ProfileUpdateRq;
import com.arbitr.cargoway.dto.rs.profile.ProfileRs;
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
            profile.setContactData(profileMapper.buildContactDataFrom(profileUpdateRq.getContactData()));
        }
        if (profileUpdateRq.getCompany() != null) {
            profile.setCompany(profileMapper.buildCompanyFrom(profileUpdateRq.getCompany()));
        }
        if (profileUpdateRq.getIndividual() != null) {
            profile.setIndividual(profileMapper.buildIndividualFrom(profileUpdateRq.getIndividual()));
        }

        profileRepository.save(profile);

        return profileMapper.buildProfileRsFrom(profile);
    }
}
