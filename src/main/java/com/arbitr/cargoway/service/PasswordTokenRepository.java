package com.arbitr.cargoway.service;

import com.arbitr.cargoway.entity.security.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PasswordTokenRepository extends JpaRepository<PasswordToken, Long> {
    Optional<PasswordToken> findByToken(String token);

    void deleteAllByUserId(UUID userid);
}
