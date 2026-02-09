package com.agrifood.platform.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "bids")
public class Bid {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "opportunity_id")
    private Opportunity opportunity;
    
    private String supplierId;
    private BigDecimal bidAmount;
    private String currency;
    private String proposalDetails;
    
    @Enumerated(EnumType.STRING)
    private BidStatus status;
    
    private String transactionId;
    private Instant submittedAt;
    
    // Constructors
    public Bid() {
        this.submittedAt = Instant.now();
        this.status = BidStatus.SUBMITTED;
        this.transactionId = "TXN-" + java.util.UUID.randomUUID().toString();
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public Opportunity getOpportunity() { return opportunity; }
    public void setOpportunity(Opportunity opportunity) { this.opportunity = opportunity; }
    
    public String getSupplierId() { return supplierId; }
    public void setSupplierId(String supplierId) { this.supplierId = supplierId; }
    
    public BigDecimal getBidAmount() { return bidAmount; }
    public void setBidAmount(BigDecimal bidAmount) { this.bidAmount = bidAmount; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    public String getProposalDetails() { return proposalDetails; }
    public void setProposalDetails(String proposalDetails) { this.proposalDetails = proposalDetails; }
    
    public BidStatus getStatus() { return status; }
    public void setStatus(BidStatus status) { this.status = status; }
    
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    
    public Instant getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(Instant submittedAt) { this.submittedAt = submittedAt; }
}
