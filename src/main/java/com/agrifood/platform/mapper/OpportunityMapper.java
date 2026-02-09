package com.agrifood.platform.mapper;

import com.agrifood.platform.domain.Opportunity;
import com.agrifood.platform.dto.OpportunityRequest;
import com.agrifood.platform.dto.OpportunityResponse;
import org.springframework.stereotype.Component;

@Component
public class OpportunityMapper {
    
    public Opportunity toEntity(OpportunityRequest request) {
        Opportunity opportunity = new Opportunity();
        opportunity.setTitle(request.getTitle());
        opportunity.setDescription(request.getDescription());
        opportunity.setBuyerId(request.getBuyerId());
        opportunity.setProductCategory(request.getProductCategory());
        opportunity.setRegion(request.getRegion());
        opportunity.setMinBudget(request.getMinBudget());
        opportunity.setMaxBudget(request.getMaxBudget());
        opportunity.setCurrency(request.getCurrency());
        opportunity.setSubmissionDeadline(request.getSubmissionDeadline());
        opportunity.setDeliveryStartDate(request.getDeliveryStartDate());
        opportunity.setDeliveryEndDate(request.getDeliveryEndDate());
        return opportunity;
    }
    
    public OpportunityResponse toResponse(Opportunity opportunity) {
        OpportunityResponse response = new OpportunityResponse();
        response.setId(opportunity.getId());
        response.setVersion(opportunity.getVersion());
        response.setTitle(opportunity.getTitle());
        response.setDescription(opportunity.getDescription());
        response.setBuyerId(opportunity.getBuyerId());
        response.setProductCategory(opportunity.getProductCategory());
        response.setRegion(opportunity.getRegion());
        response.setMinBudget(opportunity.getMinBudget());
        response.setMaxBudget(opportunity.getMaxBudget());
        response.setCurrency(opportunity.getCurrency());
        response.setSubmissionDeadline(opportunity.getSubmissionDeadline());
        response.setDeliveryStartDate(opportunity.getDeliveryStartDate());
        response.setDeliveryEndDate(opportunity.getDeliveryEndDate());
        response.setStatus(opportunity.getStatus() != null ? opportunity.getStatus().name() : null);
        response.setCreatedAt(opportunity.getCreatedAt());
        response.setPublishedAt(opportunity.getPublishedAt());
        return response;
    }
}
