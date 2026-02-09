package com.agrifood.platform.mapper;

import com.agrifood.platform.domain.Opportunity;
import com.agrifood.platform.dto.OpportunityRequest;
import com.agrifood.platform.dto.OpportunityResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class OpportunityMapperTest {
    
    private OpportunityMapper mapper;
    
    @BeforeEach
    void setUp() {
        mapper = new OpportunityMapper();
    }
    
    @Test
    void toEntity_ValidRequest_ReturnsEntity() {
        OpportunityRequest request = new OpportunityRequest();
        request.setTitle("Test Opportunity");
        request.setDescription("Test Description");
        request.setBuyerId("buyer-123");
        request.setProductCategory("GRAINS");
        request.setRegion("Tashkent");
        request.setMinBudget(new BigDecimal("50000"));
        request.setMaxBudget(new BigDecimal("100000"));
        request.setCurrency("UZS");
        Instant deadline = Instant.now().plus(30, ChronoUnit.DAYS);
        request.setSubmissionDeadline(deadline);
        
        Opportunity entity = mapper.toEntity(request);
        
        assertNotNull(entity);
        assertEquals("Test Opportunity", entity.getTitle());
        assertEquals("Test Description", entity.getDescription());
        assertEquals("buyer-123", entity.getBuyerId());
        assertEquals("GRAINS", entity.getProductCategory());
        assertEquals("Tashkent", entity.getRegion());
        assertEquals(new BigDecimal("50000"), entity.getMinBudget());
        assertEquals(new BigDecimal("100000"), entity.getMaxBudget());
        assertEquals("UZS", entity.getCurrency());
        assertEquals(deadline, entity.getSubmissionDeadline());
    }
    
    @Test
    void toResponse_ValidEntity_ReturnsResponse() {
        Opportunity entity = new Opportunity();
        entity.setId("opp-123");
        entity.setTitle("Test Opportunity");
        entity.setDescription("Test Description");
        entity.setBuyerId("buyer-123");
        entity.setProductCategory("GRAINS");
        entity.setRegion("Tashkent");
        entity.setMinBudget(new BigDecimal("50000"));
        entity.setMaxBudget(new BigDecimal("100000"));
        entity.setCurrency("UZS");
        Instant deadline = Instant.now().plus(30, ChronoUnit.DAYS);
        entity.setSubmissionDeadline(deadline);
        
        OpportunityResponse response = mapper.toResponse(entity);
        
        assertNotNull(response);
        assertEquals("opp-123", response.getId());
        assertEquals("Test Opportunity", response.getTitle());
        assertEquals("Test Description", response.getDescription());
        assertEquals("buyer-123", response.getBuyerId());
        assertEquals("GRAINS", response.getProductCategory());
        assertEquals("Tashkent", response.getRegion());
        assertEquals(new BigDecimal("50000"), response.getMinBudget());
        assertEquals(new BigDecimal("100000"), response.getMaxBudget());
        assertEquals("UZS", response.getCurrency());
        assertEquals(deadline, response.getSubmissionDeadline());
        assertEquals("DRAFT", response.getStatus());
    }
}
