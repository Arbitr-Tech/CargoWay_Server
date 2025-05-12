package com.arbitr.cargoway.dto.general.cargo;

import com.arbitr.cargoway.entity.enums.CargoOrderStatus;
import lombok.Getter;

import java.util.Set;

@Getter
public enum CarrierVisibilityCategory {
    ACTIVE(Set.of(CargoOrderStatus.IN_PROGRESS)),
    WAITING(Set.of(CargoOrderStatus.BIDDING)),
    HISTORY(Set.of(CargoOrderStatus.CANCELED, CargoOrderStatus.COMPLETED));

    private final Set<CargoOrderStatus> visibleStatuses;

    CarrierVisibilityCategory(Set<CargoOrderStatus> visibleStatuses) {
        this.visibleStatuses = visibleStatuses;
    }
}
