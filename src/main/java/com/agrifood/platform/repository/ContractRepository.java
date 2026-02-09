package com.agrifood.platform.repository;

import com.agrifood.platform.domain.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, String> {
    List<Contract> findByBuyerId(String buyerId);
    List<Contract> findBySupplierId(String supplierId);
}
