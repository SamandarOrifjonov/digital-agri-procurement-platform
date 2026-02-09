package com.agrifood.platform.controller;

import com.agrifood.platform.domain.Opportunity;
import com.agrifood.platform.dto.BidRequest;
import com.agrifood.platform.repository.BidRepository;
import com.agrifood.platform.repository.OpportunityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class BidControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private BidRepository bidRepository;
    
    @Autowired
    private OpportunityRepository opportunityRepository;
    
    private Opportunity testOpportunity;
    
    @BeforeEach
    void setUp() {
        bidRepository.deleteAll();
        opportunityRepository.deleteAll();
        
        // Create test opportunity
        testOpportunity = new Opportunity();
        testOpportunity.setTitle("Test Opportunity");
        testOpportunity.setBuyerId("buyer-1");
        testOpportunity.setProductCategory("GRAINS");
        testOpportunity.setSubmissionDeadline(Instant.now().plus(30, ChronoUnit.DAYS));
        testOpportunity = opportunityRepository.save(testOpportunity);
    }
    
    @Test
    void submitBid_ValidRequest_ReturnsCreated() throws Exception {
        BidRequest request = new BidRequest();
        request.setOpportunityId(testOpportunity.getId());
        request.setSupplierId("sup-456");
        request.setBidAmount(new BigDecimal("75000"));
        request.setCurrency("UZS");
        request.setProposalDetails("We can deliver high quality wheat");
        
        mockMvc.perform(post("/api/bids")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.opportunityId").value(testOpportunity.getId()))
                .andExpect(jsonPath("$.supplierId").value("sup-456"))
                .andExpect(jsonPath("$.bidAmount").value(75000))
                .andExpect(jsonPath("$.status").value("SUBMITTED"))
                .andExpect(jsonPath("$.transactionId").exists());
    }
    
    @Test
    void submitBid_InvalidRequest_ReturnsBadRequest() throws Exception {
        BidRequest request = new BidRequest();
        // Missing required fields
        
        mockMvc.perform(post("/api/bids")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Failed"));
    }
    
    @Test
    void submitBid_OpportunityNotFound_ReturnsNotFound() throws Exception {
        BidRequest request = new BidRequest();
        request.setOpportunityId("invalid-id");
        request.setSupplierId("sup-456");
        request.setBidAmount(new BigDecimal("75000"));
        request.setProposalDetails("Test proposal");
        
        mockMvc.perform(post("/api/bids")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(containsString("Opportunity not found")));
    }
    
    @Test
    void submitBid_DeadlinePassed_ReturnsUnprocessableEntity() throws Exception {
        // Create opportunity with past deadline
        Opportunity pastOpportunity = new Opportunity();
        pastOpportunity.setTitle("Past Opportunity");
        pastOpportunity.setBuyerId("buyer-1");
        pastOpportunity.setProductCategory("GRAINS");
        pastOpportunity.setSubmissionDeadline(Instant.now().minus(1, ChronoUnit.DAYS));
        pastOpportunity = opportunityRepository.save(pastOpportunity);
        
        BidRequest request = new BidRequest();
        request.setOpportunityId(pastOpportunity.getId());
        request.setSupplierId("sup-456");
        request.setBidAmount(new BigDecimal("75000"));
        request.setProposalDetails("Test proposal");
        
        mockMvc.perform(post("/api/bids")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.error").value("Business Rule Violation"))
                .andExpect(jsonPath("$.message").value(containsString("deadline has passed")));
    }
    
    @Test
    void getAllBids_ReturnsEmptyList() throws Exception {
        mockMvc.perform(get("/api/bids"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
    
    @Test
    void getBidById_NotFound_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/bids/invalid-id"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"));
    }
}
