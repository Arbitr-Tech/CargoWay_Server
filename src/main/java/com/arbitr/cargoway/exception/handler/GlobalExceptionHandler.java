package com.arbitr.cargoway.exception.handler;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ws.academy.auction.api.dto.rs.ErrorRs;
import ws.academy.auction.core.exception.FileRemoveException;
import ws.academy.auction.core.exception.InternalServerError;
import ws.academy.auction.core.exception.NotFoundException;

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
