package com.marshall.pharma_app.service.impl;

import com.marshall.pharma_app.dto.request.pharmacy.CreatePharmacyRequest;
import com.marshall.pharma_app.dto.request.pharmacy.UpdatePharmacyRequest;
import com.marshall.pharma_app.dto.request.pharmacy.UpdatePharmacyRequestPharmacist;
import com.marshall.pharma_app.dto.response.pharmacy.PharmacyResponse;
import com.marshall.pharma_app.exceptions.BusinessRuleException;
import com.marshall.pharma_app.exceptions.DuplicateResourceException;
import com.marshall.pharma_app.exceptions.ResourceNotFoundException;
import com.marshall.pharma_app.mapper.PharmacyMapper;
import com.marshall.pharma_app.models.Pharmacy;
import com.marshall.pharma_app.models.User;
import com.marshall.pharma_app.models.enums.Role;
import com.marshall.pharma_app.repositories.PharmacyRepository;
import com.marshall.pharma_app.repositories.UserRepository;
import com.marshall.pharma_app.service.PharmacyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PharmacyServiceImpl implements PharmacyService {
    private final PharmacyRepository pharmacyRepository;
    private final UserRepository userRepository;

    public PharmacyServiceImpl(PharmacyRepository pharmacyRepository, UserRepository userRepository) {
        this.pharmacyRepository = pharmacyRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public PharmacyResponse createPharmacy(CreatePharmacyRequest request) {
        if (pharmacyRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("email already in use");
        }
        if (pharmacyRepository.existsByLicenseNumber(request.getLicenseNumber())) {
            throw new DuplicateResourceException("license number already in use");
        }
        if (pharmacyRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateResourceException("phone already in use");
        }

        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("user not found with id :" + request.getOwnerId()));
        if (owner.getRole() != Role.PHARMACIST) {
            throw new BusinessRuleException("owner must be a pharmacist");
        }

        Pharmacy pharmacy = PharmacyMapper.toEntity(request, owner);
        Pharmacy saved = pharmacyRepository.save(pharmacy);
        return PharmacyMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public PharmacyResponse updatePharmacyInfos(UUID pharmacyId, UpdatePharmacyRequest request) {
        Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId)
                .orElseThrow(() -> new ResourceNotFoundException("pharmacy not found with id :" + pharmacyId));

        User owner = null;
        if (request.getOwnerId() != null) {
            owner = userRepository.findById(request.getOwnerId())
                    .orElseThrow(() -> new ResourceNotFoundException("user not found with id :" + request.getOwnerId()));
            if (owner.getRole() != Role.PHARMACIST) {
                throw new BusinessRuleException("owner must be a pharmacist");
            }
        }

        PharmacyMapper.updateEntity(request, pharmacy, owner);
        Pharmacy saved = pharmacyRepository.save(pharmacy);
        return PharmacyMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PharmacyResponse getPharmacyById(UUID id) {
        Pharmacy pharmacy = pharmacyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("pharmacy not found with id :" + id));
        return PharmacyMapper.toResponse(pharmacy);
    }

    @Override
    @Transactional(readOnly = true)
    public PharmacyResponse getByLicenseNumber(String licenseNumber) {
        Pharmacy pharmacy = pharmacyRepository.findByLicenseNumber(licenseNumber)
                .orElseThrow(() -> new ResourceNotFoundException("pharmacy not found with license number :" + licenseNumber));
        return PharmacyMapper.toResponse(pharmacy);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PharmacyResponse> findPharmaciesByName(String name, Pageable pageable) {
        return pharmacyRepository.findByNameContainingIgnoreCase(name, pageable).map(PharmacyMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PharmacyResponse> findByOwnerId(UUID ownerId, Pageable pageable) {
        return pharmacyRepository.findByOwnerId(ownerId, pageable).map(PharmacyMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PharmacyResponse> findActive(Pageable pageable) {
        return pharmacyRepository.findByActiveTrue(pageable).map(PharmacyMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PharmacyResponse> getAllPharmacies(Pageable pageable) {
        return pharmacyRepository.findAll(pageable).map(PharmacyMapper::toResponse);
    }

    @Override
    @Transactional
    public void deletePharmacy(UUID id) {
        Pharmacy pharmacy = pharmacyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("pharmacy not found with id :" + id));
        if (!pharmacy.isActive()) {
            throw new BusinessRuleException("pharmacy is already inactive");
        }
        pharmacy.setActive(false);
        pharmacyRepository.save(pharmacy);
    }

    @Override
    @Transactional
    public PharmacyResponse updateMyPharmacy(UUID id, UpdatePharmacyRequestPharmacist request, String userEmail) {
        Pharmacy pharmacy = pharmacyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("pharmacy not found with id :" + id));

        User owner = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with email :" + userEmail));

        if (!pharmacy.getOwner().getId().equals(owner.getId())) {
            throw new BusinessRuleException("you don't have permission to update this pharmacy");
        }

        PharmacyMapper.updateEntityByPharmacist(request, pharmacy);
        Pharmacy saved = pharmacyRepository.save(pharmacy);
        return PharmacyMapper.toResponse(saved);
    }
}