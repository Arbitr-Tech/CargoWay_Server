package com.arbitr.cargoway.exception.handler;

import com.arbitr.cargoway.dto.rs.ErrorRs;
import com.arbitr.cargoway.exception.*;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;

@Slf4j
@RestControllerAdvice
@SuppressWarnings("rawtypes")
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorRs> handleValidationException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorRs.builder()
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(TokenValidationException.class)
    public ResponseEntity<ErrorRs> handleTokenValidationException(TokenValidationException e) {
        log.error(e.getMessage(), e);

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ErrorRs.builder()
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorRs> handleInvalidTokenException(InvalidTokenException e) {
        log.error(e.getMessage(), e);

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ErrorRs.builder()
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorRs> handleValidationException(BadCredentialsException e) {
        log.error(e.getMessage(), e);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorRs.builder()
                        .message("Неверный логин или пароль. Попробуйте еще раз!")
                        .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorRs> handleValidationException(ValidationException e) {
        log.error(e.getMessage(), e);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorRs.builder()
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorRs> handleNotFoundException(NotFoundException e) {
        log.error(e.getMessage(), e);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorRs.builder()
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ErrorRs> handleFileLoadException(FileNotFoundException e) {
        log.error(e.getMessage(), e);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorRs.builder()
                        .message("Что-то пошло не так, при попытке загрузить файл")
                        .build());
    }

    @ExceptionHandler(FileRemoveException.class)
    public ResponseEntity<ErrorRs> handleFileRemoveException(FileRemoveException e) {
        log.error(e.getMessage(), e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorRs.builder()
                        .message("Что-то пошло не так при попытке удалить файл")
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorRs> handleUnexpectedException(Exception e) {
        log.error(e.getMessage(), e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorRs.builder()
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<ErrorRs> handleUnexpectedException(InternalServerError e) {
        log.error(e.getMessage(), e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorRs.builder()
                        .message(e.getMessage())
                        .build());
    }
}
