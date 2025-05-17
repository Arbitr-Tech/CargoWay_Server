package com.arbitr.cargoway.service;

import com.arbitr.cargoway.dto.rq.profile.ProfileUpdateRq;
import com.arbitr.cargoway.dto.rs.profile.ProfileRs;
import com.arbitr.cargoway.entity.Profile;

import java.util.UUID;

public interface ProfileService {
    ProfileRs getCurrentProfile();

    ProfileRs getForeignProfile(UUID profileId);

    Profile getProfileById(UUID profileId);

    ProfileRs updateProfile(ProfileUpdateRq profileUpdateRq);

    Profile getAuthenticatedProfile();
}
