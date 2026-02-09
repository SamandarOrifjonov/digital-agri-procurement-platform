package com.agrifood.platform.service;

import com.agrifood.platform.domain.Bid;
import com.agrifood.platform.domain.Opportunity;
import com.agrifood.platform.exception.BusinessException;
import com.agrifood.platform.exception.ResourceNotFoundException;
import com.agrifood.platform.repository.BidRepository;
import com.agrifood.platform.repository.OpportunityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class BidService {
    
    private static final Logger log = LoggerFactory.getLogger(BidService.class);
    
    private final BidRepository bidRepository;
    private final OpportunityRepository opportunityRepository;
    
    public BidService(BidRepository bidRepository, OpportunityRepository opportunityRepository) {
        this.bidRepository = bidRepository;
        this.opportunityRepository = opportunityRepository;
    }
    
    public Bid submitBid(Bid bid, String opportunityId) {
        log.info("Submitting bid for opportunity: {}", opportunityId);
        
        Opportunity opportunity = opportunityRepository.findById(opportunityId)
            .orElseThrow(() -> new ResourceNotFoundException("Opportunity", "id", opportunityId));
        
        if (opportunity.isDeadlinePassed()) {
            throw new BusinessException("Cannot submit bid - submission deadline has passed");
        }
        
        bid.setOpportunity(opportunity);
        Bid savedBid = bidRepository.save(bid);
        log.info("Bid submitted successfully with ID: {}", savedBid.getId());
        return savedBid;
    }
    
    public List<Bid> getAllBids() {
        log.debug("Fetching all bids");
        return bidRepository.findAll();
    }
    
    public Bid getBidById(String id) {
        log.debug("Fetching bid with ID: {}", id);
        return bidRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Bid", "id", id));
    }
    
    public List<Bid> getBidsBySupplier(String supplierId) {
        log.debug("Fetching bids for supplier: {}", supplierId);
        return bidRepository.findBySupplierId(supplierId);
    }
    
    public List<Bid> getBidsByOpportunity(String opportunityId) {
        log.debug("Fetching bids for opportunity: {}", opportunityId);
        return bidRepository.findByOpportunityId(opportunityId);
    }
    
    public void deleteBid(String id) {
        log.info("Deleting bid with ID: {}", id);
        if (!bidRepository.existsById(id)) {
            throw new ResourceNotFoundException("Bid", "id", id);
        }
        bidRepository.deleteById(id);
        log.info("Bid deleted successfully");
    }
}
