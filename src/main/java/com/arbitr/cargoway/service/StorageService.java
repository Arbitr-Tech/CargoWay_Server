package com.arbitr.cargoway.service;

import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * Сервис для операции над файлами в хранилище
 */
public interface StorageService {
    /**
     * Метод для загрузки файла в хранилище и получении его id
     * @param inputStream - поток данных файла
     * @param fileSize - размер файла
     * @param fileName - имя файла
     * @return - ссылка на файл
     */
    String uploadFile(InputStream inputStream, Long fileSize, String fileName);

    /**
     * Метод для удаления файла по его id
     * @param fileName - id файла
     */
    void deleteFile(String fileName);
}
