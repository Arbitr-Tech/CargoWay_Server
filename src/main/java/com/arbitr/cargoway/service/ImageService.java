package com.arbitr.cargoway.service;

import com.arbitr.cargoway.dto.rs.FileRs;
import com.arbitr.cargoway.entity.Image;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
public interface ImageService {
    /**
     * Метод для сохранения файла в хранилище
     * @param inputStream - файл
     * @param fileSize - размер файла
     * @param originalFileName - имя файла
     */
    FileRs saveImage(InputStream inputStream, Long fileSize, String originalFileName, String contentType);
    void deleteImage(UUID imageId);
    List<Image> findImagesByIds(List<UUID> imageIds);

}
