package com.arbitr.cargoway.service;

import com.arbitr.cargoway.dto.rs.UserProfileRs;
import com.arbitr.cargoway.entity.Profile;

public interface ProfileService {
    UserProfileRs getUserProfile();
    Profile getAuthenticatedProfile();
}
