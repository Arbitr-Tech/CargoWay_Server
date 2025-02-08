package com.arbitr.cargoway.mapper;

import com.arbitr.cargoway.dto.rq.cargo.CargoCreateRq;
import com.arbitr.cargoway.dto.rq.cargo.CargoStatus;
import com.arbitr.cargoway.dto.rs.ImageRef;
import com.arbitr.cargoway.dto.rs.cargo.CargoDetailsRs;
import com.arbitr.cargoway.entity.Cargo;
import com.arbitr.cargoway.entity.Image;
import com.arbitr.cargoway.entity.enums.VisibilityStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;

@Mapper
public interface CargoMapper {
    @Mapping(source = "status", target = "visibility", qualifiedByName = "mapStatusToVisibility")
    Cargo buildCargoFrom(CargoCreateRq cargoCreateRq);

    @Named("mapStatusToVisibility")
    default VisibilityStatus mapStatusToVisibility(CargoStatus cargoStatus) {
        return VisibilityStatus.valueOf(cargoStatus.name());
    }

    @Named("mapVisibilityToStatus")
    default CargoStatus mapStatusToVisibility(VisibilityStatus cargoStatus) {
        return CargoStatus.valueOf(cargoStatus.name());
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

    @Mapping(source = "visibility", target = "status", qualifiedByName = "mapVisibilityToStatus")
    @Mapping(source = "images", target = "photos", qualifiedByName = "mapImagesToImageRef")
    CargoDetailsRs buildCargoDetailsRs(Cargo cargo);
}
