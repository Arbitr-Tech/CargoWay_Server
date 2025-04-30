package com.arbitr.cargoway.mapper;

import com.arbitr.cargoway.dto.rq.cargo.RecordStatus;
import com.arbitr.cargoway.dto.rs.ImageRef;
import com.arbitr.cargoway.entity.Image;
import com.arbitr.cargoway.entity.enums.CargoOrderStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;

@Mapper
public interface GeneralMapper {
    @Named("mapStatusToVisibility")
    default CargoOrderStatus mapStatusToVisibility(RecordStatus recordStatus) {
        return CargoOrderStatus.valueOf(recordStatus.name());
    }

    @Named("mapVisibilityToStatus")
    default RecordStatus mapStatusToVisibility(CargoOrderStatus transportStatus) {
        return RecordStatus.valueOf(transportStatus.name());
    }

    @Named("mapImagesToImageRef")
    default List<ImageRef> mapImagesToImageRef(List<Image> images) {
        if (images == null || images.isEmpty()) {
            return Collections.emptyList();
        }
        return images.stream()
                .map(image -> ImageRef.builder()
                        .guid(image.getId())
                        .path(image.getImagePath())
                        .build())
                .toList();
    }
}
