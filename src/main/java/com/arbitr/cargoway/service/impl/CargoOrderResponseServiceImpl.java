package com.arbitr.cargoway.service.impl;

import com.arbitr.cargoway.dto.rs.cargo.CargoOrderRs;
import com.arbitr.cargoway.entity.CargoOrder;
import com.arbitr.cargoway.entity.CargoOrderResponse;
import com.arbitr.cargoway.entity.Profile;
import com.arbitr.cargoway.entity.Transport;
import com.arbitr.cargoway.entity.enums.CargoOrderStatus;
import com.arbitr.cargoway.exception.NotFoundException;
import com.arbitr.cargoway.exception.ResourceConflictException;
import com.arbitr.cargoway.mapper.CargoOrderMapper;
import com.arbitr.cargoway.repository.CargoOrderRepository;
import com.arbitr.cargoway.repository.CargoOrderResponseRepository;
import com.arbitr.cargoway.service.CargoOrderResponseService;
import com.arbitr.cargoway.service.CargoOrderService;
import com.arbitr.cargoway.service.ProfileService;
import com.arbitr.cargoway.service.TransportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CargoOrderResponseServiceImpl implements CargoOrderResponseService {
    private final ProfileService profileService;
    private final CargoOrderService cargoOrderService;
    private final TransportService transportService;
    private final CargoOrderResponseRepository cargoOrderResponseRepository;
    private final CargoOrderRepository cargoOrderRepository;
    private final CargoOrderMapper cargoOrderMapper;

    @Override
    public void makeResponse(UUID cargoOrderId, UUID transportId) {
        Profile currentProfile = profileService.getAuthenticatedProfile();

        List<CargoOrderResponse> currentProfileCargoOrderResponseList = cargoOrderResponseRepository.
                findCargoOrderResponsesByCargoOrder_IdAndResponder_Id(cargoOrderId, currentProfile.getId());

        if (currentProfileCargoOrderResponseList.size() == 1) {
            throw new ResourceConflictException("Запрос для данного заказа от текущего профиля был сделан! Id заказа=%s"
                    .formatted(cargoOrderId)
            );
        }

        CargoOrder foundCargoOrder = cargoOrderService.getCargoOrderById(cargoOrderId);
        cargoOrderService.setStatusToCargoOrder(foundCargoOrder, CargoOrderStatus.BIDDING);
        Transport foundTransport = transportService.getTransportByIdAndCurrentProfile(transportId);

        CargoOrderResponse newCargoOrderResponse = CargoOrderResponse.builder()
                .cargoOrder(foundCargoOrder)
                .transport(foundTransport)
                .responder(currentProfile)
                .build();

        foundCargoOrder.getResponses().add(newCargoOrderResponse);
        cargoOrderResponseRepository.save(newCargoOrderResponse);
    }

    @Override
    public void cancelResponse(UUID cargoOrderId) {
        Profile currentProfile = profileService.getAuthenticatedProfile();

        CargoOrderResponse existingCargoOrderResponse = cargoOrderResponseRepository
                .findCargoOrderResponseByCargoOrder_IdAndResponder_Id(cargoOrderId, currentProfile.getId())
                .orElseThrow(
                        () -> new NotFoundException("У заказа с id=%s не был найден отклик от текущего профиля с id=%s"
                                .formatted(cargoOrderId, currentProfile.getId()))
                );

        cargoOrderResponseRepository.delete(existingCargoOrderResponse);
    }

    @Override
    public CargoOrderRs startExecutionCargoOrder(UUID cargoOrderId, UUID responseId) {
        CargoOrderResponse existingCargoOrderResponse = this.getCargoOrderResponse(cargoOrderId, responseId);

        Profile newExecutor = existingCargoOrderResponse.getResponder();
        Transport newTransport = existingCargoOrderResponse.getTransport();
        CargoOrder currentCargoOrder = existingCargoOrderResponse.getCargoOrder();

        cargoOrderService.setStatusToCargoOrder(currentCargoOrder, CargoOrderStatus.IN_PROGRESS);
        currentCargoOrder.setExecutor(newExecutor);
        currentCargoOrder.setExecutorTransport(newTransport);
        currentCargoOrder.setStartExecution(LocalDateTime.now());

        cargoOrderRepository.save(currentCargoOrder);

        return cargoOrderMapper.toRsDto(currentCargoOrder);
    }

    private CargoOrderResponse getCargoOrderResponse(UUID cargoOrderId, UUID responseId) {
        return cargoOrderResponseRepository.findCargoOrderResponseByIdAndCargoOrder_Id(responseId, cargoOrderId)
                .orElseThrow(
                        () -> new NotFoundException(
                                "Не был найден отклик или заказ! id заказа = %s id отклика = %s"
                                        .formatted(cargoOrderId, responseId))
                );
    }
}
