package com.agrifood.platform.service;

import com.agrifood.platform.domain.Supplier;
import com.agrifood.platform.exception.BadRequestException;
import com.agrifood.platform.exception.ResourceNotFoundException;
import com.agrifood.platform.repository.SupplierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class SupplierService {

    private static final Logger log = LoggerFactory.getLogger(SupplierService.class);

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public Supplier registerSupplier(Supplier supplier) {
        log.info("Registering new supplier: {}", supplier.getEmail());

        Supplier existing = supplierRepository.findByEmail(supplier.getEmail());
        if (existing != null) {
            throw new BadRequestException("Supplier with email " + supplier.getEmail() + " already exists");
        }

        Supplier saved = supplierRepository.save(supplier);
        log.info("Supplier registered with ID: {}", saved.getId());
        return saved;
    }

    public List<Supplier> getAllSuppliers() {
        log.debug("Fetching all suppliers");
        return supplierRepository.findAll();
    }

    public Supplier getSupplierById(String id) {
        log.debug("Fetching supplier with ID: {}", id);
        return supplierRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Supplier", "id", id));
    }

    public List<Supplier> getSuppliersByRegion(String region) {
        log.debug("Fetching suppliers by region: {}", region);
        return supplierRepository.findByRegion(region);
    }

    public Supplier updateSupplier(String id, Supplier updatedSupplier) {
        log.info("Updating supplier with ID: {}", id);
        Supplier supplier = getSupplierById(id);
        supplier.setName(updatedSupplier.getName());
        supplier.setEmail(updatedSupplier.getEmail());
        supplier.setPhone(updatedSupplier.getPhone());
        supplier.setRegion(updatedSupplier.getRegion());
        supplier.setAddress(updatedSupplier.getAddress());
        Supplier updated = supplierRepository.save(supplier);
        log.info("Supplier updated successfully");
        return updated;
    }
}
