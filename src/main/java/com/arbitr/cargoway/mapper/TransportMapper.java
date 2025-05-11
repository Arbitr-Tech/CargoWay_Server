package com.arbitr.cargoway.mapper;


import com.arbitr.cargoway.dto.general.EmbeddedTrailerDto;
import com.arbitr.cargoway.dto.rq.transaport.TransportCreateRq;
import com.arbitr.cargoway.dto.rs.transport.TransportRs;
import com.arbitr.cargoway.dto.rs.transport.TransportShortDto;
import com.arbitr.cargoway.entity.Transport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(uses = {TrailerMapper.class, DriverMapper.class})
public interface TransportMapper extends GeneralMapper {
    default Transport toEntity(TransportCreateRq transportCreateRq) {
        Transport newTransport = Transport.builder()
                .brand(transportCreateRq.getBrand())
                .model(transportCreateRq.getModel())
                .manufactureYear(transportCreateRq.getManufactureYear())
                .transportNumber(transportCreateRq.getTransportNumber())
                .build();

        if (transportCreateRq.getEmbeddedTrailerDetails() != null) {
            EmbeddedTrailerDto embeddedTrailerDto = transportCreateRq.getEmbeddedTrailerDetails();
            Transport.TrailerDetails embeddedTrailerDetails = Transport.TrailerDetails.builder()
                    .liftingCapacity(embeddedTrailerDto.getLiftingCapacity())
                    .bodyType(embeddedTrailerDto.getBodyType())
                    .loadType(embeddedTrailerDto.getLoadType())
                    .unloadType(embeddedTrailerDto.getUnloadType())
                    .width(embeddedTrailerDto.getWidth())
                    .height(embeddedTrailerDto.getHeight())
                    .length(embeddedTrailerDto.getLength())
                    .volume(embeddedTrailerDto.getVolume())
                    .build();
            newTransport.setEmbeddedTrailerDetails(embeddedTrailerDetails);
        }

        return newTransport;
    }

    @Mapping(source = "embeddedTrailerDetails", target = "embeddedTrailer", qualifiedByName = "toTrailerDetails")
    TransportRs toRsDto(Transport transport);

    TransportShortDto toTransportShortDto(Transport transport);

    @Named("toTrailerDetails")
    EmbeddedTrailerDto toTrailerDetails(Transport.TrailerDetails trailerDetails);
}