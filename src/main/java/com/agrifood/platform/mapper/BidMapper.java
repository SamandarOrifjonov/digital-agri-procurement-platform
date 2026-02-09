package com.agrifood.platform.mapper;

import com.agrifood.platform.domain.Bid;
import com.agrifood.platform.dto.BidRequest;
import com.agrifood.platform.dto.BidResponse;
import org.springframework.stereotype.Component;

@Component
public class BidMapper {
    
    public Bid toEntity(BidRequest request) {
        Bid bid = new Bid();
        bid.setSupplierId(request.getSupplierId());
        bid.setBidAmount(request.getBidAmount());
        bid.setCurrency(request.getCurrency());
        bid.setProposalDetails(request.getProposalDetails());
        return bid;
    }
    
    public BidResponse toResponse(Bid bid) {
        BidResponse response = new BidResponse();
        response.setId(bid.getId());
        response.setOpportunityId(bid.getOpportunity() != null ? bid.getOpportunity().getId() : null);
        response.setSupplierId(bid.getSupplierId());
        response.setBidAmount(bid.getBidAmount());
        response.setCurrency(bid.getCurrency());
        response.setProposalDetails(bid.getProposalDetails());
        response.setStatus(bid.getStatus() != null ? bid.getStatus().name() : null);
        response.setTransactionId(bid.getTransactionId());
        response.setSubmittedAt(bid.getSubmittedAt());
        return response;
    }
}
