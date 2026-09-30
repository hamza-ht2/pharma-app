package com.marshall.pharma_app.mapper;

import com.marshall.pharma_app.dto.request.pharmacy.CreatePharmacyRequest;
import com.marshall.pharma_app.dto.request.pharmacy.UpdatePharmacyRequest;
import com.marshall.pharma_app.dto.request.pharmacy.UpdatePharmacyRequestPharmacist;
import com.marshall.pharma_app.dto.response.pharmacy.PharmacyResponse;
import com.marshall.pharma_app.models.Pharmacy;
import com.marshall.pharma_app.models.User;

public class PharmacyMapper {
    public static PharmacyResponse toResponse(Pharmacy pharmacy){
        if (pharmacy == null){
            return null;
        }
        return new PharmacyResponse(
                pharmacy.getId(),
                pharmacy.getName(),
                pharmacy.getLicenseNumber(), pharmacy.getAddress(), pharmacy.getCity(),
                pharmacy.getPhone(), pharmacy.getEmail(),
                pharmacy.getOwner() != null ? pharmacy.getOwner().getId() : null,
                pharmacy.getOwner() != null ? pharmacy.getOwner().getFullName() : null,
                pharmacy.isActive(), pharmacy.getCreatedAt() , pharmacy.getUpdatedAt()
        );
    }

    public static Pharmacy toEntity(CreatePharmacyRequest request, User owner){
        return Pharmacy.builder()
                .name(request.getName())
                .licenseNumber(request.getLicenseNumber())
                .address(request.getAddress())
                .city(request.getCity())
                .phone(request.getPhone())
                .email(request.getEmail().toLowerCase())
                .owner(owner)
                .active(request.getActive() != null ? request.getActive() : true)
                .build();
    }

    public static void updateEntity(UpdatePharmacyRequest request, Pharmacy pharmacy, User owner){
        if (request.getName() != null) pharmacy.setName(request.getName());
        if (request.getLicenseNumber() != null) pharmacy.setLicenseNumber(request.getLicenseNumber());
        if (request.getAddress() != null) pharmacy.setAddress(request.getAddress());
        if (request.getCity() != null) pharmacy.setCity(request.getCity());
        if (request.getPhone() != null) pharmacy.setPhone(request.getPhone());
        if (request.getEmail() != null) pharmacy.setEmail(request.getEmail().toLowerCase());
        if (request.getActive() != null) pharmacy.setActive(request.getActive());
        if (owner != null) pharmacy.setOwner(owner);
    }

    public static void updateEntityByPharmacist(UpdatePharmacyRequestPharmacist request, Pharmacy pharmacy){
        if (request.getAddress() != null) pharmacy.setAddress(request.getAddress());
        if (request.getCity() != null) pharmacy.setCity(request.getCity());
        if (request.getPhone() != null) pharmacy.setPhone(request.getPhone());
        if (request.getEmail() != null) pharmacy.setEmail(request.getEmail().toLowerCase());
    }
}