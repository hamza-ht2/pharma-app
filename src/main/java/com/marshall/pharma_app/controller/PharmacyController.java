package com.marshall.pharma_app.controller;

import com.marshall.pharma_app.dto.request.pharmacy.CreatePharmacyRequest;
import com.marshall.pharma_app.dto.request.pharmacy.UpdatePharmacyRequest;
import com.marshall.pharma_app.dto.request.pharmacy.UpdatePharmacyRequestPharmacist;
import com.marshall.pharma_app.dto.response.pharmacy.PharmacyResponse;
import com.marshall.pharma_app.service.PharmacyService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/pharmacies")
public class PharmacyController {
    private final PharmacyService pharmacyService;

    public PharmacyController(PharmacyService pharmacyService) {
        this.pharmacyService = pharmacyService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PharmacyResponse> createPharmacy(@Valid @RequestBody CreatePharmacyRequest request){
        PharmacyResponse created = pharmacyService.createPharmacy(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<PharmacyResponse>> getAll(@PageableDefault(size = 10)Pageable pageable){
        return ResponseEntity.ok(pharmacyService.getAllPharmacies(pageable));
    }
    @GetMapping("/active")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<PharmacyResponse>> getActivePharmacies(@PageableDefault(size = 10)Pageable pageable){
        return ResponseEntity.ok(pharmacyService.findActive(pageable));
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PharmacyResponse> getById(@PathVariable UUID id){
        return ResponseEntity.ok(pharmacyService.getPharmacyById(id));
    }
    @GetMapping("/license/{licenseNumber}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PharmacyResponse> getByLicenseNumber(@PathVariable String licenseNumber){
        return ResponseEntity.ok(pharmacyService.getByLicenseNumber(licenseNumber));
    }
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<PharmacyResponse>> searchByName(@RequestParam String name, @PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(pharmacyService.findPharmaciesByName(name,pageable));
    }
    @GetMapping("/owner/{ownerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<PharmacyResponse>> getByOwnerId(@PathVariable UUID ownerId, @PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(pharmacyService.findByOwnerId(ownerId, pageable));
    }
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PharmacyResponse> updatePharmacy(@PathVariable UUID id, @Valid @RequestBody UpdatePharmacyRequest request){
        PharmacyResponse updated = pharmacyService.updatePharmacyInfos(id,request);
        return ResponseEntity.ok(updated);
    }
    @PatchMapping("/my-pharmacy/{pharmacyId}")
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<PharmacyResponse> updateMyPharma(@PathVariable UUID pharmacyId, @Valid @RequestBody UpdatePharmacyRequestPharmacist requestPharmacist, @AuthenticationPrincipal UserDetails userDetails){
        PharmacyResponse pharmacyResponse = pharmacyService.updateMyPharmacy(pharmacyId,requestPharmacist,userDetails.getUsername());
        return ResponseEntity.ok(pharmacyResponse);
    }
    @DeleteMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePharmacy(@PathVariable UUID id){
        pharmacyService.deletePharmacy(id);
        return ResponseEntity.noContent().build();
    }
}
