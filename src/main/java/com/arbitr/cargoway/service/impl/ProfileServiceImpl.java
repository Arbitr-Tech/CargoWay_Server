package com.arbitr.cargoway.service.impl;

import com.arbitr.cargoway.dto.rs.UserProfileRs;
import com.arbitr.cargoway.entity.Profile;
import com.arbitr.cargoway.entity.security.User;
import com.arbitr.cargoway.exception.NotFoundException;
import com.arbitr.cargoway.repository.UserRepository;
import com.arbitr.cargoway.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final UserRepository userRepository;

    @Override
    public UserProfileRs getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new RuntimeException("Произошла ошибка! Пользователь не аутентифицирован!");
        }

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Пользователь не был найден! Попробуйте еще раз."));

        return UserProfileRs.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    @Override
    public Profile getAuthenticatedProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new RuntimeException("Произошла ошибка! Пользователь не аутентифицирован!");
        }

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Пользователь не был найден! Попробуйте еще раз."));

        return user.getProfile();
    }
}
