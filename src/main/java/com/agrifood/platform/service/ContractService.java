package com.agrifood.platform.service;

import com.agrifood.platform.domain.Contract;
import com.agrifood.platform.exception.ResourceNotFoundException;
import com.agrifood.platform.repository.ContractRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ContractService {
    
    private static final Logger log = LoggerFactory.getLogger(ContractService.class);
    
    private final ContractRepository contractRepository;
    
    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }
    
    public Contract createContract(Contract contract) {
            log.info("Creating new contract");
            Contract saved = contractRepository.save(contract);
            log.info("Contract created with ID: {}", saved.getId());
            return saved;
        }
    
    public List<Contract> getAllContracts() {
            log.debug("Fetching all contracts");
            return contractRepository.findAll();
        }
    
    public Contract getContractById(String id) {
            log.debug("Fetching contract with ID: {}", id);
            return contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract", "id", id));
        }
    
    public List<Contract> getContractsByBuyer(String buyerId) {
            log.debug("Fetching contracts for buyer: {}", buyerId);
            return contractRepository.findByBuyerId(buyerId);
        }
    
    public List<Contract> getContractsBySupplier(String supplierId) {
            log.debug("Fetching contracts for supplier: {}", supplierId);
            return contractRepository.findBySupplierId(supplierId);
        }
}
