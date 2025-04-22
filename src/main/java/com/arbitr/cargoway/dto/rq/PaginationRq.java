package com.arbitr.cargoway.dto.rq;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class PaginationRq {
    @Min(1)
    private int pageNumber;

    @Min(1)
    private int pageSize;

    public int getPageNumber() {
        return pageNumber - 1;
    }
}
