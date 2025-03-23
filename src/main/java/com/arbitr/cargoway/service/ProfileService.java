package com.arbitr.cargoway.service;

import com.arbitr.cargoway.dto.rq.profile.ProfileUpdateRq;
import com.arbitr.cargoway.dto.rs.profile.ProfileRs;

public interface ProfileService {
    ProfileRs getProfile();

    ProfileRs updateProfile(ProfileUpdateRq profileUpdateRq);
}
