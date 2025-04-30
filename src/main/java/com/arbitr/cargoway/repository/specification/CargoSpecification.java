package com.arbitr.cargoway.repository.specification;

import com.arbitr.cargoway.dto.rq.cargo.FilterCargoRq;
import com.arbitr.cargoway.entity.Cargo;
import com.arbitr.cargoway.entity.CargoOrder;
import com.arbitr.cargoway.entity.enums.CargoOrderStatus;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CargoSpecification {
    public static Specification<Cargo> withFilter(FilterCargoRq filter, Set<CargoOrderStatus> allowedStatuses) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Join<Cargo, CargoOrder> cargoOrderJoin = root.join("cargoOrder", JoinType.INNER);

            if (allowedStatuses != null && !allowedStatuses.isEmpty()) {
                predicates.add(cargoOrderJoin.get("visibility").in(allowedStatuses));
            }

            if (filter.getWeightFrom() != null || filter.getWeightTo() != null) {
                predicates.add(criteriaBuilder.between(
                        root.get("weight"),
                        filter.getWeightFrom() != null ? filter.getWeightFrom() : 0,
                        filter.getWeightTo() != null ? filter.getWeightTo() : Integer.MAX_VALUE
                ));
            }

            // Фильтр по объему
            if (filter.getVolumeFrom() != null || filter.getVolumeTo() != null) {
                predicates.add(criteriaBuilder.between(
                        root.get("volume"),
                        filter.getVolumeFrom() != null ? filter.getVolumeFrom() : 0,
                        filter.getVolumeTo() != null ? filter.getVolumeTo() : Integer.MAX_VALUE
                ));
            }

            // Фильтр по типу загрузки/выгрузки
            if (filter.getLoadType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("loadType"), filter.getLoadType()));
            }
            if (filter.getUnloadType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("unloadType"), filter.getUnloadType()));
            }

            // Фильтр по цене
            if (filter.getPriceFrom() != null || filter.getPriceTo() != null) {
                predicates.add(criteriaBuilder.between(
                        root.get("price"),
                        filter.getPriceFrom() != null ? filter.getPriceFrom() : BigDecimal.ZERO,
                        filter.getPriceTo() != null ? filter.getPriceTo() : new BigDecimal(Long.MAX_VALUE)
                ));
            }

            // Фильтр по типу кузова
            if (filter.getBodyType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("bodyType"), filter.getBodyType()));
            }

            // Фильтр по дате готовности
            if (filter.getReadyDate() != null) {
                predicates.add(criteriaBuilder.equal(root.get("readyDate"), filter.getReadyDate()));
            }

            // Фильтр по маршруту
            if (filter.getRoute() != null) {
                if (filter.getRoute().getFrom() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("from"), filter.getRoute().getFrom()));
                }
                if (filter.getRoute().getTo() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("to"), filter.getRoute().getTo()));
                }
            }

            // Фильтр по габаритам
            if (filter.getDimensions() != null) {
                if (filter.getDimensions().getLength() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("length"), filter.getDimensions().getLength()));
                }
                if (filter.getDimensions().getWidth() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("width"), filter.getDimensions().getWidth()));
                }
                if (filter.getDimensions().getHeight() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("height"), filter.getDimensions().getHeight()));
                }
            }

            if (query != null && query.getOrderList().isEmpty()) {
                query.orderBy(criteriaBuilder.desc(cargoOrderJoin.get("orderUpdatedAt")));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
