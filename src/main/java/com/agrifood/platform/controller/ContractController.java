package com.agrifood.platform.controller;

import com.agrifood.platform.domain.Contract;
import com.agrifood.platform.service.ContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/contracts")
@Tag(name = "Contracts", description = "Contract management")
public class ContractController {
    
    private final ContractService contractService;
    
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }
    
    @PostMapping
    @Operation(summary = "Create a new contract")
    public ResponseEntity<Contract> createContract(@RequestBody Contract contract) {
        Contract created = contractService.createContract(contract);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @GetMapping
    @Operation(summary = "Get all contracts")
    public ResponseEntity<List<Contract>> getAllContracts() {
        return ResponseEntity.ok(contractService.getAllContracts());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get contract by ID")
    public ResponseEntity<Contract> getContractById(@PathVariable String id) {
        return ResponseEntity.ok(contractService.getContractById(id));
    }
    
    @GetMapping("/buyer/{buyerId}")
    @Operation(summary = "Get contracts by buyer")
    public ResponseEntity<List<Contract>> getContractsByBuyer(@PathVariable String buyerId) {
        return ResponseEntity.ok(contractService.getContractsByBuyer(buyerId));
    }
    
    @GetMapping("/supplier/{supplierId}")
    @Operation(summary = "Get contracts by supplier")
    public ResponseEntity<List<Contract>> getContractsBySupplier(@PathVariable String supplierId) {
        return ResponseEntity.ok(contractService.getContractsBySupplier(supplierId));
    }
}
