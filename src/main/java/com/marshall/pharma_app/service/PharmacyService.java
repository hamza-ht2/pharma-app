package com.marshall.pharma_app.service;

import com.marshall.pharma_app.dto.request.pharmacy.CreatePharmacyRequest;
import com.marshall.pharma_app.dto.request.pharmacy.UpdatePharmacyRequest;
import com.marshall.pharma_app.dto.request.pharmacy.UpdatePharmacyRequestPharmacist;
import com.marshall.pharma_app.dto.response.pharmacy.PharmacyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PharmacyService {
    PharmacyResponse createPharmacy(CreatePharmacyRequest request);
    PharmacyResponse updatePharmacyInfos(UUID pharmacyId, UpdatePharmacyRequest request);
    PharmacyResponse getPharmacyById(UUID id);
    PharmacyResponse updateMyPharmacy(UUID id , UpdatePharmacyRequestPharmacist request, String userEmail);
    PharmacyResponse getByLicenseNumber(String licenseNumber);
    Page<PharmacyResponse> findPharmaciesByName(String name, Pageable pageable);
    Page<PharmacyResponse> findByOwnerId(UUID ownerId, Pageable pageable);
    Page<PharmacyResponse> findActive(Pageable pageable);
    Page<PharmacyResponse> getAllPharmacies(Pageable pageable);
    void deletePharmacy(UUID id);

}
