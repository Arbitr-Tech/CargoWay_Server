package com.arbitr.cargoway.controller;

import com.arbitr.cargoway.dto.rs.UserProfileRs;
import com.arbitr.cargoway.service.ProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profile/")
@RequiredArgsConstructor
@Tag(name = "Profile", description = "Управление профилем пользователя")
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping
    public UserProfileRs getUserProfile() {
        return profileService.getUserProfile();
    }
}
