package com.arbitr.cargoway.service.impl;

import com.arbitr.cargoway.dto.rq.cargo.CargoCreateRq;
import com.arbitr.cargoway.dto.rq.cargo.CargoStatus;
import com.arbitr.cargoway.dto.rq.cargo.CargoUpdateRq;
import com.arbitr.cargoway.dto.rq.cargo.FilterCargoRq;
import com.arbitr.cargoway.dto.rs.cargo.CargoDetailsRs;
import com.arbitr.cargoway.entity.Cargo;
import com.arbitr.cargoway.entity.Image;
import com.arbitr.cargoway.entity.Profile;
import com.arbitr.cargoway.exception.NotFoundException;
import com.arbitr.cargoway.mapper.CargoMapper;
import com.arbitr.cargoway.repository.CargoRepository;
import com.arbitr.cargoway.service.CargoService;
import com.arbitr.cargoway.service.ImageService;
import com.arbitr.cargoway.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CargoServiceImpl implements CargoService {
    private final ProfileService profileService;
    private final ImageService imageService;

    private final CargoRepository cargoRepository;

    private final CargoMapper cargoMapper;

    @Override
    public CargoDetailsRs createNewCargo(CargoCreateRq cargoCreateRq) {
        Cargo newCargo = cargoMapper.buildCargoFrom(cargoCreateRq);

        List<Image> cargoImages = imageService.getImagesByIds(cargoCreateRq.getPhotos());

        Profile userProfile = profileService.getAuthenticatedProfile();
        newCargo.setProfile(userProfile);
        newCargo.setImages(cargoImages);

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
    public CargoDetailsRs updateCargo(UUID cargoId, CargoUpdateRq cargoUpdateRq) {
        Cargo foundCargo = foundCargoOrElseThrowNotFound(cargoId);

        if (cargoUpdateRq.getName() != null) {
            foundCargo.setName(cargoUpdateRq.getName());
        }
        if (cargoUpdateRq.getDescription() != null) {
            foundCargo.setDescription(cargoUpdateRq.getDescription());
        }
        if (cargoUpdateRq.getWeight() != null) {
            foundCargo.setWeight(cargoUpdateRq.getWeight());
        }
        if (cargoUpdateRq.getVolume() != null) {
            foundCargo.setVolume(cargoUpdateRq.getVolume());
        }
        if (cargoUpdateRq.getLoadType() != null) {
            foundCargo.setLoadType(cargoUpdateRq.getLoadType());
        }
        if (cargoUpdateRq.getUnloadType() != null) {
            foundCargo.setUnloadType(cargoUpdateRq.getUnloadType());
        }
        if (cargoUpdateRq.getBodyType() != null) {
            foundCargo.setBodyType(cargoUpdateRq.getBodyType());
        }
        if (cargoUpdateRq.getPrice() != null) {
            foundCargo.setPrice(cargoUpdateRq.getPrice());
        }
        if (cargoUpdateRq.getTypePay() != null) {
            foundCargo.setTypePay(cargoUpdateRq.getTypePay());
        }
        if (cargoUpdateRq.getReadyDate() != null) {
            foundCargo.setReadyDate(cargoUpdateRq.getReadyDate());
        }
        if (cargoUpdateRq.getDeliveryDate() != null) {
            foundCargo.setDeliveryDate(cargoUpdateRq.getDeliveryDate());
        }
        if (cargoUpdateRq.getStatus() != null) {
            foundCargo.setVisibility(cargoMapper.mapStatusToVisibility(cargoUpdateRq.getStatus()));
        }
        if (cargoUpdateRq.getPhotos() != null) {
            foundCargo.setImages(imageService.getImagesByIds(cargoUpdateRq.getPhotos()));
        }

        if (cargoUpdateRq.getDimensions() != null) {
            Cargo.Dimensions dimensions = foundCargo.getDimensions();
            if (dimensions == null) {
                dimensions = new Cargo.Dimensions();
            }
            if (cargoUpdateRq.getDimensions().getLength() != null) {
                dimensions.setLength(cargoUpdateRq.getDimensions().getLength());
            }
            if (cargoUpdateRq.getDimensions().getWidth() != null) {
                dimensions.setWidth(cargoUpdateRq.getDimensions().getWidth());
            }
            if (cargoUpdateRq.getDimensions().getHeight() != null) {
                dimensions.setHeight(cargoUpdateRq.getDimensions().getHeight());
            }
            foundCargo.setDimensions(dimensions);
        }

        if (cargoUpdateRq.getRoute() != null) {
            Cargo.Route route = foundCargo.getRoute();
            if (route == null) {
                route = new Cargo.Route();
            }
            if (cargoUpdateRq.getRoute().getFrom() != null) {
                route.setFrom(cargoUpdateRq.getRoute().getFrom());
            }
            if (cargoUpdateRq.getRoute().getTo() != null) {
                route.setTo(cargoUpdateRq.getRoute().getTo());
            }
            foundCargo.setRoute(route);
        }

        cargoRepository.save(foundCargo);

        return cargoMapper.buildCargoDetailsRs(foundCargo);
    }

    @Override
    public CargoDetailsRs changeCargoGlobalVisibility(UUID cargoId, CargoStatus status) {
        Cargo foundCargo = foundCargoOrElseThrowNotFound(cargoId);
        foundCargo.setVisibility(cargoMapper.mapStatusToVisibility(status));

        cargoRepository.save(foundCargo);
        return cargoMapper.buildCargoDetailsRs(foundCargo);
    }

    @Override
    public void deleteCargo(UUID cargoId) {
        cargoRepository.deleteCargoById(cargoId);
    }

    private Cargo foundCargoOrElseThrowNotFound(UUID cargoId) {
        return cargoRepository.findCargoById(cargoId)
                .orElseThrow(() -> new NotFoundException("Груз с id = %s не был найден!".formatted(cargoId)));
    }
}
