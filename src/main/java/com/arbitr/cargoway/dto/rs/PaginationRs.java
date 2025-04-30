package com.arbitr.cargoway.dto.rs;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaginationRs<T> {
    List<T> content;
    int pageNumber;
    int pageSize;
    int totalPages;

    public static <T> PaginationRs<T> of(List<T> content, int pageNumber, int pageSize, int totalPages) {
        int realPageNumber = pageNumber + 1;
        return new PaginationRs<>(content, realPageNumber, pageSize, totalPages);
    }
}
