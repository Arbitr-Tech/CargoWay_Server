package com.arbitr.cargoway.mapper;

import com.arbitr.cargoway.dto.rs.FileRs;
import com.arbitr.cargoway.entity.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;

@Mapper
public interface GeneralMapper {
    @Named("mapImagesToImageRef")
    default List<FileRs> mapImagesToImageRef(List<Image> images) {
        if (images == null || images.isEmpty()) {
            return Collections.emptyList();
        }
        return images.stream()
                .map(image -> FileRs.builder()
                        .id(image.getId())
                        .path(image.getImagePath())
                        .build())
                .toList();
    }
}
