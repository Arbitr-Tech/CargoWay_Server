package com.arbitr.cargoway.service.impl;

import com.arbitr.cargoway.exception.FileRemoveException;
import com.arbitr.cargoway.exception.FileUploadException;
import com.arbitr.cargoway.exception.InternalServerError;
import com.arbitr.cargoway.service.StorageService;
import com.arbitr.cargoway.config.properties.MinioProperties;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class MinioStorageServiceImpl implements StorageService {
    private final MinioClient minioClient;
    private final String minioBucketName;

    @Autowired
    public MinioStorageServiceImpl(MinioProperties minioProperties) {
        minioBucketName = minioProperties.getBucketName();
        minioClient = MinioClient.builder()
                .endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }

    @Override
    public String uploadFile(InputStream inputStream, Long fileSize, String fileName) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minioBucketName)
                    .object(fileName)
                    .stream(inputStream, fileSize, -1)
                    .build());
        } catch (Exception e) {
            throw new FileUploadException(e.getMessage());
        }
        return "%s/%s".formatted(minioBucketName, fileName);
    }

    @Override
    public void deleteFile(String fileName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(minioBucketName)
                            .object(fileName)
                            .build()
            );
        } catch (Exception e) {
            throw new FileRemoveException(e.getMessage());
        }
    }

    @PreDestroy
    private void closeMinioClient() {
        try {
            minioClient.close();
        } catch (Exception e) {
            throw new InternalServerError("Внутренняя ошибка сервера");
        }
    }
}

