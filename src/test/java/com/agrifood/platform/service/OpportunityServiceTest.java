package com.agrifood.platform.service;

import com.agrifood.platform.domain.Opportunity;
import com.agrifood.platform.exception.BusinessException;
import com.agrifood.platform.exception.ResourceNotFoundException;
import com.agrifood.platform.repository.OpportunityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OpportunityServiceTest {
    
    @Mock
    private OpportunityRepository opportunityRepository;
    
    @InjectMocks
    private OpportunityService opportunityService;
    
    private Opportunity testOpportunity;
    
    @BeforeEach
    void setUp() {
        testOpportunity = new Opportunity();
        testOpportunity.setId("opp-123");
        testOpportunity.setTitle("Test Opportunity");
        testOpportunity.setBuyerId("buyer-1");
        testOpportunity.setProductCategory("GRAINS");
        testOpportunity.setSubmissionDeadline(Instant.now().plus(30, ChronoUnit.DAYS));
    }
    
    @Test
    void createOpportunity_Success() {
        when(opportunityRepository.save(any(Opportunity.class))).thenReturn(testOpportunity);
        
        Opportunity result = opportunityService.createOpportunity(testOpportunity);
        
        assertNotNull(result);
        assertEquals("opp-123", result.getId());
        verify(opportunityRepository, times(1)).save(testOpportunity);
    }
    
    @Test
    void publishOpportunity_Success() {
        when(opportunityRepository.findById("opp-123")).thenReturn(Optional.of(testOpportunity));
        when(opportunityRepository.save(any(Opportunity.class))).thenReturn(testOpportunity);
        
        Opportunity result = opportunityService.publishOpportunity("opp-123");
        
        assertNotNull(result);
        assertNotNull(result.getPublishedAt());
        verify(opportunityRepository, times(1)).findById("opp-123");
        verify(opportunityRepository, times(1)).save(any(Opportunity.class));
    }
    
    @Test
    void publishOpportunity_DeadlinePassed_ThrowsException() {
        testOpportunity.setSubmissionDeadline(Instant.now().minus(1, ChronoUnit.DAYS));
        when(opportunityRepository.findById("opp-123")).thenReturn(Optional.of(testOpportunity));
        
        assertThrows(BusinessException.class, () -> {
            opportunityService.publishOpportunity("opp-123");
        });
        
        verify(opportunityRepository, times(1)).findById("opp-123");
        verify(opportunityRepository, never()).save(any(Opportunity.class));
    }
    
    @Test
    void getOpportunityById_Success() {
        when(opportunityRepository.findById("opp-123")).thenReturn(Optional.of(testOpportunity));
        
        Opportunity result = opportunityService.getOpportunityById("opp-123");
        
        assertNotNull(result);
        assertEquals("opp-123", result.getId());
        verify(opportunityRepository, times(1)).findById("opp-123");
    }
    
    @Test
    void getOpportunityById_NotFound_ThrowsException() {
        when(opportunityRepository.findById("invalid-id")).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> {
            opportunityService.getOpportunityById("invalid-id");
        });
        
        verify(opportunityRepository, times(1)).findById("invalid-id");
    }
    
    @Test
    void getAllOpportunities_Success() {
        List<Opportunity> opportunities = Arrays.asList(testOpportunity, new Opportunity());
        when(opportunityRepository.findAll()).thenReturn(opportunities);
        
        List<Opportunity> result = opportunityService.getAllOpportunities();
        
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(opportunityRepository, times(1)).findAll();
    }
    
    @Test
    void getOpportunitiesByCategory_Success() {
        List<Opportunity> opportunities = Arrays.asList(testOpportunity);
        when(opportunityRepository.findByProductCategory("GRAINS")).thenReturn(opportunities);
        
        List<Opportunity> result = opportunityService.getOpportunitiesByCategory("GRAINS");
        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("GRAINS", result.get(0).getProductCategory());
        verify(opportunityRepository, times(1)).findByProductCategory("GRAINS");
    }
    
    @Test
    void deleteOpportunity_Success() {
        when(opportunityRepository.existsById("opp-123")).thenReturn(true);
        doNothing().when(opportunityRepository).deleteById("opp-123");
        
        opportunityService.deleteOpportunity("opp-123");
        
        verify(opportunityRepository, times(1)).existsById("opp-123");
        verify(opportunityRepository, times(1)).deleteById("opp-123");
    }
    
    @Test
    void deleteOpportunity_NotFound_ThrowsException() {
        when(opportunityRepository.existsById("invalid-id")).thenReturn(false);
        
        assertThrows(ResourceNotFoundException.class, () -> {
            opportunityService.deleteOpportunity("invalid-id");
        });
        
        verify(opportunityRepository, times(1)).existsById("invalid-id");
        verify(opportunityRepository, never()).deleteById(anyString());
    }
}
