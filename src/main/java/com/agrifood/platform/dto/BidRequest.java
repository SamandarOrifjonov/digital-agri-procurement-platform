package com.agrifood.platform.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class BidRequest {
    
    @NotBlank(message = "Opportunity ID is required")
    private String opportunityId;
    
    @NotBlank(message = "Supplier ID is required")
    private String supplierId;
    
    @NotNull(message = "Bid amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Bid amount must be greater than 0")
    private BigDecimal bidAmount;
    
    @Size(max = 10, message = "Currency code must not exceed 10 characters")
    private String currency;
    
    @NotBlank(message = "Proposal details are required")
    private String proposalDetails;
    
    // Getters and Setters
    public String getOpportunityId() { return opportunityId; }
    public void setOpportunityId(String opportunityId) { this.opportunityId = opportunityId; }
    
    public String getSupplierId() { return supplierId; }
    public void setSupplierId(String supplierId) { this.supplierId = supplierId; }
    
    public BigDecimal getBidAmount() { return bidAmount; }
    public void setBidAmount(BigDecimal bidAmount) { this.bidAmount = bidAmount; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    public String getProposalDetails() { return proposalDetails; }
    public void setProposalDetails(String proposalDetails) { this.proposalDetails = proposalDetails; }
}
