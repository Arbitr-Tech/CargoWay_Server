package com.arbitr.cargoway.dto.rq.cargo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FilterCargoRq {
    private Integer weight;
    private Integer volume;
    private String loadType;
    private String unloadType;
    private BigDecimal price;
    private LocalDate readyDate;
    private DimensionsDto dimensions;

    @Data
    public static class RouteDto {
        private String from;
        private String to;
    }

    @Data
    public static class DimensionsDto {
        private Integer length;
        private Integer width;
        private Integer height;
    }
}
