package com.agrifood.platform.controller;

import com.agrifood.platform.dto.OpportunityRequest;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class OpportunityControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private OpportunityRepository opportunityRepository;
    
    @BeforeEach
    void setUp() {
        opportunityRepository.deleteAll();
    }
    
    @Test
    void createOpportunity_ValidRequest_ReturnsCreated() throws Exception {
        OpportunityRequest request = new OpportunityRequest();
        request.setTitle("Wheat Purchase");
        request.setDescription("Need 100 tons of wheat");
        request.setBuyerId("buyer-123");
        request.setProductCategory("GRAINS");
        request.setRegion("Tashkent");
        request.setMinBudget(new BigDecimal("50000"));
        request.setMaxBudget(new BigDecimal("100000"));
        request.setCurrency("UZS");
        request.setSubmissionDeadline(Instant.now().plus(30, ChronoUnit.DAYS));
        
        mockMvc.perform(post("/api/opportunities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Wheat Purchase"))
                .andExpect(jsonPath("$.buyerId").value("buyer-123"))
                .andExpect(jsonPath("$.status").value("DRAFT"));
    }
    
    @Test
    void createOpportunity_InvalidRequest_ReturnsBadRequest() throws Exception {
        OpportunityRequest request = new OpportunityRequest();
        // Missing required fields
        
        mockMvc.perform(post("/api/opportunities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Failed"));
    }
    
    @Test
    void createOpportunity_PastDeadline_ReturnsBadRequest() throws Exception {
        OpportunityRequest request = new OpportunityRequest();
        request.setTitle("Wheat Purchase");
        request.setDescription("Need 100 tons of wheat");
        request.setBuyerId("buyer-123");
        request.setProductCategory("GRAINS");
        request.setSubmissionDeadline(Instant.now().minus(1, ChronoUnit.DAYS)); // Past date
        
        mockMvc.perform(post("/api/opportunities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Failed"));
    }
    
    @Test
    void getAllOpportunities_ReturnsEmptyList() throws Exception {
        mockMvc.perform(get("/api/opportunities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
    
    @Test
    void getOpportunityById_NotFound_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/opportunities/invalid-id"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value(containsString("Opportunity not found")));
    }
}
