package com.arbitr.cargoway.controller;

import com.arbitr.cargoway.dto.rs.ImageRef;
import com.arbitr.cargoway.exception.FileUploadException;
import com.arbitr.cargoway.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/file/")
@Tag(name = "FileController", description = "Контроллер для загрузки изображений")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @Operation(summary = "Загрузка файлов")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ImageRef addLotPhoto(MultipartFile file) {
        try {
            return imageService.saveImage(file.getInputStream(), file.getSize(), file.getOriginalFilename());
        } catch (IOException e) {
            throw new FileUploadException(e.getMessage());
        }
    }
}
