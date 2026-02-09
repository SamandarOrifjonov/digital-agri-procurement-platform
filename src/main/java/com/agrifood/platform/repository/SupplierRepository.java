package com.agrifood.platform.repository;

import com.agrifood.platform.domain.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, String> {
    List<Supplier> findByRegion(String region);
    Supplier findByEmail(String email);
}
