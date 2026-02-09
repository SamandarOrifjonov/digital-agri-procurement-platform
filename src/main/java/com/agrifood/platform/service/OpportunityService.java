package com.agrifood.platform.service;

import com.agrifood.platform.domain.Opportunity;
import com.agrifood.platform.exception.BusinessException;
import com.agrifood.platform.exception.ResourceNotFoundException;
import com.agrifood.platform.repository.OpportunityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class OpportunityService {
    
    private static final Logger log = LoggerFactory.getLogger(OpportunityService.class);
    
    private final OpportunityRepository opportunityRepository;
    
    public OpportunityService(OpportunityRepository opportunityRepository) {
        this.opportunityRepository = opportunityRepository;
    }
    
    public Opportunity createOpportunity(Opportunity opportunity) {
        log.info("Creating new opportunity: {}", opportunity.getTitle());
        Opportunity saved = opportunityRepository.save(opportunity);
        log.info("Opportunity created with ID: {}", saved.getId());
        return saved;
    }
    
    public Opportunity publishOpportunity(String id) {
        log.info("Publishing opportunity with ID: {}", id);
        Opportunity opportunity = opportunityRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Opportunity", "id", id));
        
        if (opportunity.isDeadlinePassed()) {
            throw new BusinessException("Cannot publish opportunity - submission deadline has already passed");
        }
        
        opportunity.publish();
        Opportunity published = opportunityRepository.save(opportunity);
        log.info("Opportunity published successfully");
        return published;
    }
    
    public List<Opportunity> getAllOpportunities() {
        log.debug("Fetching all opportunities");
        return opportunityRepository.findAll();
    }
    
    public Opportunity getOpportunityById(String id) {
        log.debug("Fetching opportunity with ID: {}", id);
        return opportunityRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Opportunity", "id", id));
    }
    
    public List<Opportunity> getOpportunitiesByCategory(String category) {
        log.debug("Fetching opportunities by category: {}", category);
        return opportunityRepository.findByProductCategory(category);
    }
    
    public List<Opportunity> getOpportunitiesByRegion(String region) {
        log.debug("Fetching opportunities by region: {}", region);
        return opportunityRepository.findByRegion(region);
    }
    
    public void deleteOpportunity(String id) {
        log.info("Deleting opportunity with ID: {}", id);
        if (!opportunityRepository.existsById(id)) {
            throw new ResourceNotFoundException("Opportunity", "id", id);
        }
        opportunityRepository.deleteById(id);
        log.info("Opportunity deleted successfully");
    }
}
