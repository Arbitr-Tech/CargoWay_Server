package com.arbitr.cargoway.service.impl;

import com.arbitr.cargoway.event.EmailDto;
import com.arbitr.cargoway.dto.rq.auth.RecoveryEmailRq;
import com.arbitr.cargoway.dto.rq.auth.ResetPasswordRq;
import com.arbitr.cargoway.entity.security.PasswordToken;
import com.arbitr.cargoway.entity.security.User;
import com.arbitr.cargoway.exception.NotFoundException;
import com.arbitr.cargoway.publisher.EmailEventPublisher;
import com.arbitr.cargoway.repository.UserRepository;
import com.arbitr.cargoway.service.PasswordManagementService;
import com.arbitr.cargoway.service.PasswordTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class PasswordManagementServiceImpl implements PasswordManagementService {
    private final UserRepository userRepository;
    private final PasswordTokenRepository passwordTokenRepository;

    private final EmailEventPublisher emailEventPublisher;
    private final PasswordEncoder passwordEncoder;

    @Value("${frontend.recovery-password.url}")
    private String recoveryUrl;

    @Value("${frontend.recovery-password.expiration-minutes}")
    private int recoveryExpirationMinutes;

    @Transactional
    @Override
    public void recoveryUserPassword(RecoveryEmailRq recoveryEmailRq) {
        User foundUser = userRepository.findByEmail(recoveryEmailRq.getEmail())
                .orElseThrow(() -> new NotFoundException("Участник с почтой %s не был найден!".formatted(recoveryEmailRq.getEmail())));

        deleteAllRecoveryTokensBy(foundUser);
        String token = savePasswordToken(generateSecureToken(), foundUser);
        String recoveryLink = createRecoveryLink(token);

        emailEventPublisher.sendEmailEvent(
                EmailDto.builder()
                        .toEmail(foundUser.getEmail())
                        .subject("Восстановление пароля")
                        .body("Чтобы перейти на страницу восстановления пароля, нажмите на ссылку: %s".formatted(recoveryLink))
                        .build()
        );
    }

    private void deleteAllRecoveryTokensBy(User user) {
        passwordTokenRepository.deleteAllByUserId(user.getId());
    }

    private String createRecoveryLink(String recoveryToken) {
        return UriComponentsBuilder.fromHttpUrl(recoveryUrl)
                .queryParam("token", recoveryToken)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUriString();
    }

    private String generateSecureToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String savePasswordToken(String recoveryToken, User user) {
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(recoveryExpirationMinutes);

        PasswordToken newPasswordToken = PasswordToken.builder()
                .expiresAt(expirationTime)
                .token(recoveryToken)
                .build();

        newPasswordToken.setUser(user);
        passwordTokenRepository.save(newPasswordToken);

        return newPasswordToken.getToken();
    }

    @Transactional
    @Override
    public void resetPassword(String recoveryToken, ResetPasswordRq resetPasswordRq) {
        PasswordToken foundPasswordToken = passwordTokenRepository.findByToken(recoveryToken)
                .orElseThrow(() -> new NotFoundException("Недействительная ссылка! Запросите новую ссылку."));

        if (!isValidRecoveryToken(foundPasswordToken)) {
            throw new NotFoundException("Ошибочная ссылка или срок действия истек! Запросите новую.");
        }

        User user = foundPasswordToken.getUser();
        user.setPassword(passwordEncoder.encode(resetPasswordRq.getNewPassword()));

        userRepository.save(user);

        emailEventPublisher.sendEmailEvent(
                EmailDto.builder()
                        .toEmail(user.getEmail())
                        .subject("Пароль успешно изменен!")
                        .body("Если вы ничего не делали, тогда обратитесь в службу поддержки для выяснений обстоятельств.")
                        .build()
        );
    }

    private boolean isValidRecoveryToken(PasswordToken passwordToken) {
        return LocalDateTime.now().isBefore(passwordToken.getExpiresAt());
    }
}