package com.arbitr.cargoway.controller;

import com.arbitr.cargoway.dto.rs.FileRs;
import com.arbitr.cargoway.service.ImageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/file/")
@RequiredArgsConstructor
@Tag(name = "File", description = "Для управления файлами")
public class FileController {
    private final ImageService imageService;

}
