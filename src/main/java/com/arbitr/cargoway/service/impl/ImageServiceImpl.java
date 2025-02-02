package com.arbitr.cargoway.service.impl;

import com.arbitr.cargoway.dto.rs.ImageRef;
import com.arbitr.cargoway.entity.Image;
import com.arbitr.cargoway.mapper.ImageMapper;
import com.arbitr.cargoway.repository.ImageRepository;
import com.arbitr.cargoway.service.ImageService;
import com.arbitr.cargoway.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final StorageService storageService;
    private final ImageRepository imageRepository;

    private final ImageMapper imageMapper;

    @Override
    public ImageRef saveImage(InputStream inputStream, Long fileSize, String fileName) {
        Image image = new Image();
        String pathToFile = storageService.uploadFile(inputStream, fileSize, image.getId().toString());
        image.setImagePath(pathToFile);
        image.setImageName(fileName);
        imageRepository.save(image);
        return imageMapper.buildImageRef(image);
    }
}
