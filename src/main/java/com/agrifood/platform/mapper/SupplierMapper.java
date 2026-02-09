package com.agrifood.platform.mapper;

import com.agrifood.platform.domain.Supplier;
import com.agrifood.platform.dto.SupplierRequest;
import com.agrifood.platform.dto.SupplierResponse;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {
    
    public Supplier toEntity(SupplierRequest request) {
        Supplier supplier = new Supplier();
        supplier.setName(request.getName());
        supplier.setEmail(request.getEmail());
        supplier.setPhone(request.getPhone());
        supplier.setRegion(request.getRegion());
        supplier.setAddress(request.getAddress());
        return supplier;
    }
    
    public SupplierResponse toResponse(Supplier supplier) {
        SupplierResponse response = new SupplierResponse();
        response.setId(supplier.getId());
        response.setName(supplier.getName());
        response.setEmail(supplier.getEmail());
        response.setPhone(supplier.getPhone());
        response.setRegion(supplier.getRegion());
        response.setAddress(supplier.getAddress());
        response.setQualificationStatus(supplier.getQualificationStatus() != null ? 
            supplier.getQualificationStatus().name() : null);
        response.setRegisteredAt(supplier.getRegisteredAt());
        response.setCertificationExpiryDate(supplier.getCertificationExpiryDate());
        response.setCertificationExpired(supplier.isCertificationExpired());
        return response;
    }
}
