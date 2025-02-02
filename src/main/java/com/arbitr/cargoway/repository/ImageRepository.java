package com.arbitr.cargoway.repository;

import com.arbitr.cargoway.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {
    Image findImagesById(UUID id);

    List<Image> findImagesByIdIn(List<UUID> ids);
}
