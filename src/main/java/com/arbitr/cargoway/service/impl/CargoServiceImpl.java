package com.arbitr.cargoway.service.impl;

import com.arbitr.cargoway.dto.rq.cargo.CargoCreateRq;
import com.arbitr.cargoway.dto.rq.cargo.FilterCargoRq;
import com.arbitr.cargoway.dto.rs.cargo.CargoDetailsRs;
import com.arbitr.cargoway.entity.Cargo;
import com.arbitr.cargoway.entity.Profile;
import com.arbitr.cargoway.entity.security.User;
import com.arbitr.cargoway.exception.NotFoundException;
import com.arbitr.cargoway.mapper.CargoMapper;
import com.arbitr.cargoway.repository.CargoRepository;
import com.arbitr.cargoway.service.CargoService;
import com.arbitr.cargoway.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CargoServiceImpl implements CargoService {
    private final ProfileService profileService;

    private final CargoRepository cargoRepository;

    private final CargoMapper cargoMapper;

    @Override
    public CargoDetailsRs createNewCargo(CargoCreateRq cargoCreateRq) {
        Cargo newCargo = cargoMapper.buildCargoFrom(cargoCreateRq);

        Profile userProfile = profileService.getAuthenticatedProfile();
        newCargo.setProfile(userProfile);

        cargoRepository.save(newCargo);

        return cargoMapper.buildCargoDetailsRs(newCargo);
    }

    @Override
    public CargoDetailsRs getCargo(UUID cargoId) {
        Cargo foundCargo = foundCargoOrElseThrowNotFound(cargoId);

        return cargoMapper.buildCargoDetailsRs(foundCargo);
    }

    @Override
    public List<CargoDetailsRs> searchCargos(FilterCargoRq filterCargoRq) {
        return List.of();
    }

    @Override
    public List<CargoDetailsRs> getLastCargos(int number) {
        Pageable pageRequest = PageRequest.of(0, number, Sort.by("id").descending());
        Page<Cargo> cargosPage = cargoRepository.findAll(pageRequest);
        return cargosPage.getContent().stream()
                .map(cargoMapper::buildCargoDetailsRs)
                .toList();
    }

    @Override
    public CargoDetailsRs updateCargo(UUID cargoId, CargoCreateRq cargoCreateRq) {
        return null;
    }

    @Override
    public CargoDetailsRs changeCargoGlobalVisibility(UUID cargoId, String status) {
        return null;
    }

    @Override
    public void deleteCargo(UUID cargoId) {

    }

    private Cargo foundCargoOrElseThrowNotFound(UUID cargoId) {
        return cargoRepository.findCargoById(cargoId)
                .orElseThrow(() -> new NotFoundException("Груз с id = %s не был найден!".formatted(cargoId)));
    }
}
