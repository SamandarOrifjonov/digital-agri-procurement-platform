package com.agrifood.platform.repository;

import com.agrifood.platform.domain.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, String> {
    List<Bid> findBySupplierId(String supplierId);
    
    @Query("SELECT b FROM Bid b WHERE b.opportunity.id = :opportunityId")
    List<Bid> findByOpportunityId(@Param("opportunityId") String opportunityId);
}
