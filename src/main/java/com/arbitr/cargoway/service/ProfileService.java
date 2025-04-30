package com.arbitr.cargoway.service;

import com.arbitr.cargoway.dto.rq.profile.ProfileUpdateRq;
import com.arbitr.cargoway.dto.rs.profile.ProfileRs;
import com.arbitr.cargoway.entity.Profile;

public interface ProfileService {
    ProfileRs getProfile();

    ProfileRs updateProfile(ProfileUpdateRq profileUpdateRq);

    Profile getAuthenticatedProfile();
}
