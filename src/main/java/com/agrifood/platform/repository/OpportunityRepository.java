package com.agrifood.platform.repository;

import com.agrifood.platform.domain.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, String> {
    List<Opportunity> findByBuyerId(String buyerId);
    List<Opportunity> findByProductCategory(String productCategory);
    List<Opportunity> findByRegion(String region);
}
