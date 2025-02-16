package com.arbitr.cargoway.mapper;

import com.arbitr.cargoway.dto.rq.transaport.TransportCreateRq;
import com.arbitr.cargoway.dto.rs.transport.TransportDetailsRs;
import com.arbitr.cargoway.entity.Transport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TransportMapper extends GeneralMapper{

    @Mapping(source = "status", target = "visibility", qualifiedByName = "mapStatusToVisibility")
    Transport buildTransportFrom(TransportCreateRq transportCreateRq);

    @Mapping(source = "visibility", target = "status", qualifiedByName = "mapVisibilityToStatus")
    @Mapping(source = "images", target = "photos", qualifiedByName = "mapImagesToImageRef")
    TransportDetailsRs buildTransportDetailsRs(Transport transport);
}