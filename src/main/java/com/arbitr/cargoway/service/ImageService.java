package com.arbitr.cargoway.service;

import com.arbitr.cargoway.dto.Photo;
import com.arbitr.cargoway.dto.rs.ImageRef;
import com.arbitr.cargoway.entity.Image;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public interface ImageService {
    /**
     * Метод для сохранения файла в хранилище
     * @param inputStream - файл
     * @param fileSize - размер файла
     * @param fileName - имя файла
     */
    ImageRef saveImage(InputStream inputStream, Long fileSize, String fileName) ;

    List<Image> getImagesByIds(List<Photo> photos);
}
