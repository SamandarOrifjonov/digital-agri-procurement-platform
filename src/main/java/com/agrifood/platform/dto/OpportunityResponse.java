package com.agrifood.platform.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class OpportunityResponse {
    private String id;
    private Long version;
    private String title;
    private String description;
    private String buyerId;
    private String productCategory;
    private String region;
    private BigDecimal minBudget;
    private BigDecimal maxBudget;
    private String currency;
    private Instant submissionDeadline;
    private Instant deliveryStartDate;
    private Instant deliveryEndDate;
    private String status;
    private Instant createdAt;
    private Instant publishedAt;
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getBuyerId() { return buyerId; }
    public void setBuyerId(String buyerId) { this.buyerId = buyerId; }
    
    public String getProductCategory() { return productCategory; }
    public void setProductCategory(String productCategory) { this.productCategory = productCategory; }
    
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    
    public BigDecimal getMinBudget() { return minBudget; }
    public void setMinBudget(BigDecimal minBudget) { this.minBudget = minBudget; }
    
    public BigDecimal getMaxBudget() { return maxBudget; }
    public void setMaxBudget(BigDecimal maxBudget) { this.maxBudget = maxBudget; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    public Instant getSubmissionDeadline() { return submissionDeadline; }
    public void setSubmissionDeadline(Instant submissionDeadline) { this.submissionDeadline = submissionDeadline; }
    
    public Instant getDeliveryStartDate() { return deliveryStartDate; }
    public void setDeliveryStartDate(Instant deliveryStartDate) { this.deliveryStartDate = deliveryStartDate; }
    
    public Instant getDeliveryEndDate() { return deliveryEndDate; }
    public void setDeliveryEndDate(Instant deliveryEndDate) { this.deliveryEndDate = deliveryEndDate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    
    public Instant getPublishedAt() { return publishedAt; }
    public void setPublishedAt(Instant publishedAt) { this.publishedAt = publishedAt; }
}
