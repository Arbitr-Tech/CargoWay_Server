package com.arbitr.cargoway.service.impl;

import com.arbitr.cargoway.dto.rs.FileRs;
import com.arbitr.cargoway.entity.Image;
import com.arbitr.cargoway.mapper.ImageMapper;
import com.arbitr.cargoway.repository.ImageRepository;
import com.arbitr.cargoway.service.ImageService;
import com.arbitr.cargoway.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final StorageService storageService;
    private final ImageRepository imageRepository;

    private final ImageMapper imageMapper;

    @Override
    public FileRs saveImage(InputStream inputStream, Long fileSize, String fileName, String contentType) {
        String pathToFile = storageService.uploadFile(inputStream, fileSize, UUID.randomUUID().toString(), contentType);

        Image image = Image.builder()
                .imagePath(pathToFile)
                .imageName(fileName)
                .build();

        imageRepository.save(image);

        return imageMapper.toRsDto(image);
    }
}
