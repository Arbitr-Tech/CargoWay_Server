package com.arbitr.cargoway.service.impl;

import com.arbitr.cargoway.dto.CompanyDetails;
import com.arbitr.cargoway.dto.ContactDataDetails;
import com.arbitr.cargoway.dto.IndividualDetails;
import com.arbitr.cargoway.dto.rq.profile.ProfileUpdateRq;
import com.arbitr.cargoway.dto.rs.profile.ProfileRs;
import com.arbitr.cargoway.entity.Company;
import com.arbitr.cargoway.entity.ContactData;
import com.arbitr.cargoway.entity.Individual;
import com.arbitr.cargoway.entity.Profile;
import com.arbitr.cargoway.entity.enums.LegalType;
import com.arbitr.cargoway.entity.security.User;
import com.arbitr.cargoway.exception.BadRequestException;
import com.arbitr.cargoway.exception.NotFoundException;
import com.arbitr.cargoway.mapper.ProfileMapper;
import com.arbitr.cargoway.repository.ProfileRepository;
import com.arbitr.cargoway.service.AuthService;
import com.arbitr.cargoway.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final AuthService authService;
    private final ProfileRepository profileRepository;

    private final ProfileMapper profileMapper;

    @Override
    public ProfileRs getProfile() {
        User authenticatedUser = authService.getAuthenticatedUser();
        return profileMapper.buildProfileRsFrom(authenticatedUser.getProfile());
    }

    @Override
    public ProfileRs updateProfile(ProfileUpdateRq profileUpdateRq) {
        User user = authService.getAuthenticatedUser();
        Profile profile = getProfileOrThrow(user);

        updateContactData(profile, profileUpdateRq.getContactData());
        updateCompanyData(profile, profileUpdateRq.getCompany());
        updateIndividualData(profile, profileUpdateRq.getIndividual());

        profileRepository.save(profile);
        return profileMapper.buildProfileRsFrom(profile);
    }

    private Profile getProfileOrThrow(User user) {
        return Optional.ofNullable(user.getProfile())
                .orElseThrow(() -> new NotFoundException("Профиль пользователя не был найден!"));
    }

    private void updateContactData(Profile profile, ContactDataDetails contactDataDetails) {
        if (contactDataDetails == null) return;

        ContactData newContactData = profileMapper.buildContactDataFrom(contactDataDetails);
        ContactData existingContactData = Objects.requireNonNullElse(profile.getContactData(), new ContactData());

        profileMapper.updateContactData(existingContactData, newContactData);
    }

    private void updateCompanyData(Profile profile, CompanyDetails companyDetails) {
        if (companyDetails == null) return;

        if (profile.getLegalType() != LegalType.COMPANY) {
            throw new BadRequestException("Невозможно обновить данные о профиле компании, " +
                    "так как профиль имеет другую правовую форму");
        }

        Company newCompanyData = profileMapper.buildCompanyFrom(companyDetails);
        profileMapper.updateCompany(profile.getCompany(), newCompanyData);
    }

    private void updateIndividualData(Profile profile, IndividualDetails individualDetails) {
        if (individualDetails == null) return;

        if (profile.getLegalType() != LegalType.INDIVIDUAL) {
            throw new BadRequestException("Невозможно обновить данные о профиле физ. лица, " +
                    "так как профиль имеет другую правовую форму");
        }

        Individual newIndividualData = profileMapper.buildIndividualFrom(individualDetails);
        Individual existingIndividualData = Objects.requireNonNullElse(profile.getIndividual(), new Individual());

        profileMapper.updateIndividual(existingIndividualData, newIndividualData);
    }

}
