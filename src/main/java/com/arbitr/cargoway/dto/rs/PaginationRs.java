package com.arbitr.cargoway.dto.rs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationRs<T> {
    List<T> content;
    int pageNumber;
    int pageSize;
    int totalPages;
}
