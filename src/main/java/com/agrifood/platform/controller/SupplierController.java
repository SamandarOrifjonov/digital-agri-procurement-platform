package com.agrifood.platform.controller;

import com.agrifood.platform.domain.Supplier;
import com.agrifood.platform.dto.SupplierRequest;
import com.agrifood.platform.dto.SupplierResponse;
import com.agrifood.platform.mapper.SupplierMapper;
import com.agrifood.platform.service.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/suppliers")
@Tag(name = "Suppliers", description = "Supplier registration and management")
public class SupplierController {
    
    private final SupplierService supplierService;
    private final SupplierMapper supplierMapper;
    
    public SupplierController(SupplierService supplierService, SupplierMapper supplierMapper) {
        this.supplierService = supplierService;
        this.supplierMapper = supplierMapper;
    }
    
    @PostMapping
    @Operation(summary = "Register a new supplier")
    public ResponseEntity<SupplierResponse> registerSupplier(@Valid @RequestBody SupplierRequest request) {
        Supplier supplier = supplierMapper.toEntity(request);
        Supplier registered = supplierService.registerSupplier(supplier);
        return ResponseEntity.status(HttpStatus.CREATED).body(supplierMapper.toResponse(registered));
    }
    
    @GetMapping
    @Operation(summary = "Get all suppliers")
    public ResponseEntity<List<SupplierResponse>> getAllSuppliers() {
        List<SupplierResponse> suppliers = supplierService.getAllSuppliers()
            .stream()
            .map(supplierMapper::toResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(suppliers);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get supplier by ID")
    public ResponseEntity<SupplierResponse> getSupplierById(@PathVariable String id) {
        Supplier supplier = supplierService.getSupplierById(id);
        return ResponseEntity.ok(supplierMapper.toResponse(supplier));
    }
    
    @GetMapping("/region/{region}")
    @Operation(summary = "Get suppliers by region")
    public ResponseEntity<List<SupplierResponse>> getSuppliersByRegion(@PathVariable String region) {
        List<SupplierResponse> suppliers = supplierService.getSuppliersByRegion(region)
            .stream()
            .map(supplierMapper::toResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(suppliers);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update a supplier")
    public ResponseEntity<SupplierResponse> updateSupplier(
            @PathVariable String id, 
            @Valid @RequestBody SupplierRequest request) {
        Supplier supplier = supplierMapper.toEntity(request);
        Supplier updated = supplierService.updateSupplier(id, supplier);
        return ResponseEntity.ok(supplierMapper.toResponse(updated));
    }
}
