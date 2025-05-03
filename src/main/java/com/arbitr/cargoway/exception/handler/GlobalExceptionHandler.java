package com.arbitr.cargoway.exception.handler;

import com.arbitr.cargoway.dto.rs.ErrorRs;
import com.arbitr.cargoway.exception.*;
import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ClaimJwtException.class)
    public ResponseEntity<ErrorRs> handleExpiredJwtException(ClaimJwtException e) {
        log.warn("JWT token expired ---: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ErrorRs.builder()
                        .message("Срок действия токена истек. Пожалуйста, авторизуйтесь снова")
                        .build());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorRs> handleAuthenticationException(AuthenticationException e) {
        log.warn("Authentication failed: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ErrorRs.builder()
                        .message("Ошибка аутентификации: " + e.getMessage())
                        .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorRs> handleResourceConflictException(ResourceConflictException e) {
        log.error(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorRs.builder()
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorRs> handleValidationException(MethodArgumentNotValidException e) {
        log.error("Validation failed: {}", e.getMessage());

        String errorMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorRs.builder()
                        .message(errorMessage)
                        .build());
    }

    @ExceptionHandler(TokenValidationException.class)
    public ResponseEntity<ErrorRs> handleTokenValidationException(TokenValidationException e) {
        log.error(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ErrorRs.builder()
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorRs> handleInvalidTokenException(InvalidTokenException e) {
        log.error(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ErrorRs.builder()
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorRs> handleValidationException(BadCredentialsException e) {
        log.error(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorRs.builder()
                        .message("Неверный логин или пароль. Попробуйте еще раз!")
                        .build());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorRs> handleValidationException(ValidationException e) {
        log.error(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorRs.builder()
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorRs> handleNotFoundException(NotFoundException e) {
        log.error(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorRs.builder()
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ErrorRs> handleFileLoadException(FileNotFoundException e) {
        log.error(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorRs.builder()
                        .message("Что-то пошло не так, при попытке загрузить файл")
                        .build());
    }

    @ExceptionHandler(FileRemoveException.class)
    public ResponseEntity<ErrorRs> handleFileRemoveException(FileRemoveException e) {
        log.error(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorRs.builder()
                        .message("Что-то пошло не так при попытке удалить файл")
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorRs> handleUnexpectedException(Exception e) {
        log.error(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorRs.builder()
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<ErrorRs> handleUnexpectedException(InternalServerError e) {
        log.error(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorRs.builder()
                        .message(e.getMessage())
                        .build());
    }
}
