package com.agrifood.platform.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;

public class OpportunityRequest {
    
    @NotBlank(message = "Title is required")
    @Size(max = 500, message = "Title must not exceed 500 characters")
    private String title;
    
    @NotBlank(message = "Description is required")
    private String description;
    
    @NotBlank(message = "Buyer ID is required")
    private String buyerId;
    
    @NotBlank(message = "Product category is required")
    private String productCategory;
    
    private String region;
    
    @DecimalMin(value = "0.0", inclusive = false, message = "Minimum budget must be greater than 0")
    private BigDecimal minBudget;
    
    @DecimalMin(value = "0.0", inclusive = false, message = "Maximum budget must be greater than 0")
    private BigDecimal maxBudget;
    
    @Size(max = 10, message = "Currency code must not exceed 10 characters")
    private String currency;
    
    @NotNull(message = "Submission deadline is required")
    @Future(message = "Submission deadline must be in the future")
    private Instant submissionDeadline;
    
    private Instant deliveryStartDate;
    private Instant deliveryEndDate;
    
    // Getters and Setters
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
}
