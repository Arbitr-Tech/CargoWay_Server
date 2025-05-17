package com.arbitr.cargoway.service.impl;

import com.arbitr.cargoway.dto.general.profile.CompanyDetails;
import com.arbitr.cargoway.dto.general.profile.ContactDataDetails;
import com.arbitr.cargoway.dto.general.profile.IndividualDetails;
import com.arbitr.cargoway.dto.rq.profile.ProfileUpdateRq;
import com.arbitr.cargoway.dto.rs.profile.ProfileRs;
import com.arbitr.cargoway.entity.Company;
import com.arbitr.cargoway.entity.ContactData;
import com.arbitr.cargoway.entity.Individual;
import com.arbitr.cargoway.entity.Profile;
import com.arbitr.cargoway.entity.enums.LegalType;
import com.arbitr.cargoway.entity.security.User;
import com.arbitr.cargoway.exception.NotFoundException;
import com.arbitr.cargoway.mapper.ProfileMapper;
import com.arbitr.cargoway.repository.ProfileRepository;
import com.arbitr.cargoway.service.AuthService;
import com.arbitr.cargoway.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final AuthService authService;
    private final ProfileRepository profileRepository;

    private final ProfileMapper profileMapper;

    @Override
    public ProfileRs getCurrentProfile() {
        User authenticatedUser = authService.getAuthenticatedUser();
        return profileMapper.toRsDto(authenticatedUser.getProfile());
    }

    @Override
    public ProfileRs getForeignProfile(UUID profileId) {
        Profile foreignProfile = this.getProfileById(profileId);
        return profileMapper.toRsDto(foreignProfile);
    }

    @Override
    public Profile getProfileById(UUID profileId) {
        return profileRepository.findById(profileId).orElseThrow(
                () -> new NotFoundException("Профиль с id=%s не был найден!".formatted(profileId))
        );
    }

    @Override
    public ProfileRs updateProfile(ProfileUpdateRq profileUpdateRq) {
        User user = authService.getAuthenticatedUser();
        Profile profile = getProfileOrThrow(user);

        updateContactData(profile, profileUpdateRq.getContactData());

        if (profile.getLegalType() == LegalType.COMPANY) {
            updateCompanyData(profile, profileUpdateRq.getCompany());
        }

        if (profile.getLegalType() == LegalType.INDIVIDUAL) {
            updateIndividualData(profile, profileUpdateRq.getIndividual());
        }

        profileRepository.save(profile);
        return profileMapper.toRsDto(profile);
    }

    @Override
    public Profile getAuthenticatedProfile() {
        User user = authService.getAuthenticatedUser();
        return getProfileOrThrow(user);
    }

    private Profile getProfileOrThrow(User user) {
        return Optional.ofNullable(user.getProfile())
                .orElseThrow(() -> new NotFoundException("Профиль пользователя не был найден!"));
    }

    private void updateContactData(Profile profile, ContactDataDetails contactDataDetails) {
        if (contactDataDetails == null) return;

        ContactData contactData = profile.getContactData();
        if (contactData == null) {
            contactData = new ContactData();
            contactData.setProfile(profile);
            profile.setContactData(contactData);
        }

        if (contactDataDetails.getPhoneNumber() != null) {
            contactData.setPhoneNumber(contactDataDetails.getPhoneNumber());
        }

        if (contactDataDetails.getTelegramLink() != null) {
            contactData.setTelegramLink(contactDataDetails.getTelegramLink());
        }

        if (contactDataDetails.getWhatsappLink() != null) {
            contactData.setWhatsappLink(contactDataDetails.getWhatsappLink());
        }
    }

    private void updateCompanyData(Profile profile, CompanyDetails companyDetails) {
        if (companyDetails == null) return;

        Company company = profile.getCompany();
        if (company == null) {
            company = new Company();
            company.setProfile(profile);
            profile.setCompany(company);
        }

        if (companyDetails.getName() != null) {
            company.setName(companyDetails.getName());
        }

        if (companyDetails.getInn() != null) {
            company.setInn(companyDetails.getInn());
        }

        if (companyDetails.getOgrn() != null) {
            company.setOgrn(companyDetails.getOgrn());
        }

        if (companyDetails.getBic() != null) {
            company.setBic(companyDetails.getBic());
        }

        if (companyDetails.getCorrespondentAccount() != null) {
            company.setCorrespondentAccount(companyDetails.getCorrespondentAccount());
        }

        if (companyDetails.getRegistrationDate() != null) {
            company.setRegistrationDate(companyDetails.getRegistrationDate());
        }
    }

    private void updateIndividualData(Profile profile, IndividualDetails individualDetails) {
        if (individualDetails == null) return;

        Individual individual = profile.getIndividual();
        if (individual == null) {
            individual = new Individual();
            individual.setProfile(profile);
            profile.setIndividual(individual);
        }

        if (individualDetails.getFullName() != null) {
            individual.setFullName(individualDetails.getFullName());
        }

        if (individualDetails.getPassportNumber() != null) {
            individual.setPassportNumber(individualDetails.getPassportNumber());
        }

        if (individualDetails.getIssueDate() != null) {
            individual.setIssueDate(individualDetails.getIssueDate());
        }

        if (individualDetails.getIssuedBy() != null) {
            individual.setIssuedBy(individualDetails.getIssuedBy());
        }

        if (individualDetails.getDepartmentCode() != null) {
            individual.setDepartmentCode(individualDetails.getDepartmentCode());
        }

        if (individualDetails.getRegistrationAddress() != null) {
            individual.setRegistrationAddress(individualDetails.getRegistrationAddress());
        }
    }

}
