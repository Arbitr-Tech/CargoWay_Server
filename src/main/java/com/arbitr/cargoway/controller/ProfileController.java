package com.arbitr.cargoway.controller;

import com.arbitr.cargoway.dto.rq.profile.ProfileUpdateRq;
import com.arbitr.cargoway.dto.rs.profile.ProfileRs;
import com.arbitr.cargoway.service.ProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile/")
@RequiredArgsConstructor
@Tag(name = "Profile", description = "Управление профилем пользователя")
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping
    public ProfileRs getProfile() {
        return profileService.getProfile();
    }

    @PatchMapping
    public ProfileRs updateProfile(@RequestBody ProfileUpdateRq profileUpdateRq) {
        return profileService.updateProfile(profileUpdateRq);
    }
}
