package com.arbitr.cargoway.dto.general.cargo;

import com.arbitr.cargoway.entity.enums.CargoOrderStatus;
import lombok.Getter;

import java.util.Set;

@Getter
public enum VisibilityCategory {
    HISTORY(Set.of(CargoOrderStatus.CANCELED, CargoOrderStatus.COMPLETED)),
    INTERNAL(Set.of(CargoOrderStatus.DRAFT, CargoOrderStatus.PUBLISHED)),
    EXTERNAL(Set.of(CargoOrderStatus.IN_PROGRESS, CargoOrderStatus.BIDDING));

    private final Set<CargoOrderStatus> visibleStatuses;

    VisibilityCategory(Set<CargoOrderStatus> visibleStatuses) {
        this.visibleStatuses = visibleStatuses;
    }

}
