package com.arbitr.cargoway.dto.general.cargo;

import com.arbitr.cargoway.entity.enums.VisibilityStatus;
import lombok.Getter;

import java.util.Set;

@Getter
public enum CargoCategoryDto {
    HISTORY(Set.of(VisibilityStatus.CANCELED, VisibilityStatus.COMPLETED)),
    INTERNAL(Set.of(VisibilityStatus.DRAFT, VisibilityStatus.PUBLISHED)),
    EXTERNAL(Set.of(VisibilityStatus.IN_PROGRESS, VisibilityStatus.BIDDING));

    private final Set<VisibilityStatus> visibleStatuses;

    CargoCategoryDto(Set<VisibilityStatus> visibleStatuses) {
        this.visibleStatuses = visibleStatuses;
    }

}
