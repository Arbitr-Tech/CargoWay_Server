package com.arbitr.cargoway.service.impl;

import com.arbitr.cargoway.exception.FileRemoveException;
import com.arbitr.cargoway.exception.FileUploadException;
import com.arbitr.cargoway.exception.InternalServerError;
import com.arbitr.cargoway.service.StorageService;
import com.arbitr.cargoway.config.properties.MinioProperties;
import io.minio.*;
import io.minio.errors.MinioException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioStorageServiceImpl implements StorageService {
    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    @PostConstruct
    public void setUpBuckets() {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
                log.info("Бакет '%s' создан.".formatted(minioProperties.getBucketName()));
            } else {
                log.info("Бакет '%s' уже существует.".formatted(minioProperties.getBucketName()));
            }

            String policyJson = """
                    {
                        "Version": "2012-10-17",
                        "Statement": [
                            {
                                "Effect": "Allow",
                                "Principal": "*",
                                "Action": ["s3:GetObject"],
                                "Resource": ["arn:aws:s3:::%s/*"]
                            }
                        ]
                    }
                    """.formatted(minioProperties.getBucketName());

            minioClient.setBucketPolicy(
                    SetBucketPolicyArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .config(policyJson)
                            .build());

            log.info("Бакет '%s' теперь публичный для чтения.".formatted(minioProperties.getBucketName()));

        } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new InternalServerError(e.getMessage());
        }
    }


    @Override
    public String uploadFile(InputStream inputStream, Long fileSize, String fileName) {
        try (inputStream) {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .object(fileName)
                    .stream(inputStream, fileSize, -1)
                    .build());
        } catch (Exception e) {
            throw new FileUploadException(e.getMessage());
        }
        return "%s/%s".formatted(minioProperties.getBucketName(), fileName);
    }

    @Override
    public void deleteFile(String fileName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .object(fileName)
                            .build()
            );
        } catch (Exception e) {
            throw new FileRemoveException(e.getMessage());
        }
    }
}

