package com.arbitr.cargoway.service.impl;

import com.arbitr.cargoway.dto.rs.FileRs;
import com.arbitr.cargoway.entity.Image;
import com.arbitr.cargoway.exception.NotFoundException;
import com.arbitr.cargoway.mapper.ImageMapper;
import com.arbitr.cargoway.repository.ImageRepository;
import com.arbitr.cargoway.service.ImageService;
import com.arbitr.cargoway.service.StorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final StorageService storageService;
    private final ImageRepository imageRepository;

    private final ImageMapper imageMapper;

    @Override
    public FileRs saveImage(InputStream inputStream, Long fileSize, String originalFileName, String contentType) {
        String newFileName = this.buildFileName(originalFileName);

        String pathToFile = storageService.uploadFile(inputStream, fileSize, newFileName, contentType);

        Image image = Image.builder()
                .imagePath(pathToFile)
                .imageName(newFileName)
                .build();

        imageRepository.save(image);

        return imageMapper.toRsDto(image);
    }

    @Transactional
    @Override
    public void deleteImage(UUID imageId) {
        Image existingImage = this.findImageById(imageId);
        storageService.deleteFile(existingImage.getImageName());

        imageRepository.delete(existingImage);
    }

    @Override
    public List<Image> findImagesByIds(List<UUID> imageIds) {
        return imageRepository.findImagesByIdIn(imageIds);
    }

    private String buildFileName(String originalFileName) {
        String randomUuid = UUID.randomUUID().toString();
        return "%s_%s".formatted(originalFileName, randomUuid);
    }

    private Image findImageById(UUID imageId) {
        return imageRepository.findImageById(imageId).orElseThrow(
                () -> new NotFoundException("Изображение с id=%s не было найдено!".formatted(imageId))
        );
    }
}
