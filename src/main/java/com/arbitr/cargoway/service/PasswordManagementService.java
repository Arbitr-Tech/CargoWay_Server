package com.arbitr.cargoway.service;

import com.arbitr.cargoway.dto.rq.auth.RecoveryEmailRq;
import com.arbitr.cargoway.dto.rq.auth.ResetPasswordRq;

public interface PasswordManagementService {
    void recoveryUserPassword(RecoveryEmailRq recoveryEmailRq);
    void resetPassword(String recoveryToken, ResetPasswordRq resetPasswordRq);
}