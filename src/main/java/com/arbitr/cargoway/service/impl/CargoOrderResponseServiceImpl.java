package com.arbitr.cargoway.service.impl;

import com.arbitr.cargoway.entity.CargoOrder;
import com.arbitr.cargoway.entity.CargoOrderResponse;
import com.arbitr.cargoway.entity.Profile;
import com.arbitr.cargoway.entity.Transport;
import com.arbitr.cargoway.entity.enums.CargoOrderStatus;
import com.arbitr.cargoway.repository.CargoOrderResponseRepository;
import com.arbitr.cargoway.service.CargoOrderResponseService;
import com.arbitr.cargoway.service.CargoOrderService;
import com.arbitr.cargoway.service.ProfileService;
import com.arbitr.cargoway.service.TransportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CargoOrderResponseServiceImpl implements CargoOrderResponseService {
    private final ProfileService profileService;
    private final CargoOrderService cargoOrderService;
    private final TransportService transportService;
    private final CargoOrderResponseRepository cargoOrderResponseRepository;

    @Override
    public void makeResponse(UUID cargoOrderId, UUID transportId) {
        Profile profile = profileService.getAuthenticatedProfile();

        CargoOrder foundCargoOrder = cargoOrderService.getCargoOrderById(cargoOrderId);
        setBiddingStatusIfElse(foundCargoOrder);
        Transport foundTransport = transportService.getTransportByIdAndCurrentProfile(transportId);

        CargoOrderResponse newCargoOrderResponse = CargoOrderResponse.builder()
                .cargoOrder(foundCargoOrder)
                .transport(foundTransport)
                .responder(profile)
                .build();

        foundCargoOrder.getResponses().add(newCargoOrderResponse);
        cargoOrderResponseRepository.save(newCargoOrderResponse);
    }

    public void setBiddingStatusIfElse(CargoOrder cargoOrder) {
        if (!(cargoOrder.getVisibility() == CargoOrderStatus.BIDDING)) {
            cargoOrder.setVisibility(CargoOrderStatus.BIDDING);
        }
    }
}
