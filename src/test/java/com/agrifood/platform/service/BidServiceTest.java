package com.agrifood.platform.service;

import com.agrifood.platform.domain.Bid;
import com.agrifood.platform.domain.Opportunity;
import com.agrifood.platform.exception.BusinessException;
import com.agrifood.platform.exception.ResourceNotFoundException;
import com.agrifood.platform.repository.BidRepository;
import com.agrifood.platform.repository.OpportunityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BidServiceTest {
    
    @Mock
    private BidRepository bidRepository;
    
    @Mock
    private OpportunityRepository opportunityRepository;
    
    @InjectMocks
    private BidService bidService;
    
    private Bid testBid;
    private Opportunity testOpportunity;
    
    @BeforeEach
    void setUp() {
        testOpportunity = new Opportunity();
        testOpportunity.setId("opp-123");
        testOpportunity.setTitle("Test Opportunity");
        testOpportunity.setSubmissionDeadline(Instant.now().plus(30, ChronoUnit.DAYS));
        
        testBid = new Bid();
        testBid.setId("bid-123");
        testBid.setSupplierId("sup-456");
        testBid.setBidAmount(new BigDecimal("50000"));
        testBid.setOpportunity(testOpportunity);
    }
    
    @Test
    void submitBid_Success() {
        when(opportunityRepository.findById("opp-123")).thenReturn(Optional.of(testOpportunity));
        when(bidRepository.save(any(Bid.class))).thenReturn(testBid);
        
        Bid result = bidService.submitBid(testBid, "opp-123");
        
        assertNotNull(result);
        assertEquals("bid-123", result.getId());
        assertEquals(testOpportunity, result.getOpportunity());
        verify(opportunityRepository, times(1)).findById("opp-123");
        verify(bidRepository, times(1)).save(testBid);
    }
    
    @Test
    void submitBid_OpportunityNotFound_ThrowsException() {
        when(opportunityRepository.findById("invalid-id")).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> {
            bidService.submitBid(testBid, "invalid-id");
        });
        
        verify(opportunityRepository, times(1)).findById("invalid-id");
        verify(bidRepository, never()).save(any(Bid.class));
    }
    
    @Test
    void submitBid_DeadlinePassed_ThrowsException() {
        testOpportunity.setSubmissionDeadline(Instant.now().minus(1, ChronoUnit.DAYS));
        when(opportunityRepository.findById("opp-123")).thenReturn(Optional.of(testOpportunity));
        
        assertThrows(BusinessException.class, () -> {
            bidService.submitBid(testBid, "opp-123");
        });
        
        verify(opportunityRepository, times(1)).findById("opp-123");
        verify(bidRepository, never()).save(any(Bid.class));
    }
    
    @Test
    void getBidById_Success() {
        when(bidRepository.findById("bid-123")).thenReturn(Optional.of(testBid));
        
        Bid result = bidService.getBidById("bid-123");
        
        assertNotNull(result);
        assertEquals("bid-123", result.getId());
        verify(bidRepository, times(1)).findById("bid-123");
    }
    
    @Test
    void getBidById_NotFound_ThrowsException() {
        when(bidRepository.findById("invalid-id")).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> {
            bidService.getBidById("invalid-id");
        });
        
        verify(bidRepository, times(1)).findById("invalid-id");
    }
    
    @Test
    void getAllBids_Success() {
        List<Bid> bids = Arrays.asList(testBid, new Bid());
        when(bidRepository.findAll()).thenReturn(bids);
        
        List<Bid> result = bidService.getAllBids();
        
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(bidRepository, times(1)).findAll();
    }
    
    @Test
    void getBidsBySupplier_Success() {
        List<Bid> bids = Arrays.asList(testBid);
        when(bidRepository.findBySupplierId("sup-456")).thenReturn(bids);
        
        List<Bid> result = bidService.getBidsBySupplier("sup-456");
        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("sup-456", result.get(0).getSupplierId());
        verify(bidRepository, times(1)).findBySupplierId("sup-456");
    }
    
    @Test
    void getBidsByOpportunity_Success() {
        List<Bid> bids = Arrays.asList(testBid);
        when(bidRepository.findByOpportunityId("opp-123")).thenReturn(bids);
        
        List<Bid> result = bidService.getBidsByOpportunity("opp-123");
        
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bidRepository, times(1)).findByOpportunityId("opp-123");
    }
    
    @Test
    void deleteBid_Success() {
        when(bidRepository.existsById("bid-123")).thenReturn(true);
        doNothing().when(bidRepository).deleteById("bid-123");
        
        bidService.deleteBid("bid-123");
        
        verify(bidRepository, times(1)).existsById("bid-123");
        verify(bidRepository, times(1)).deleteById("bid-123");
    }
    
    @Test
    void deleteBid_NotFound_ThrowsException() {
        when(bidRepository.existsById("invalid-id")).thenReturn(false);
        
        assertThrows(ResourceNotFoundException.class, () -> {
            bidService.deleteBid("invalid-id");
        });
        
        verify(bidRepository, times(1)).existsById("invalid-id");
        verify(bidRepository, never()).deleteById(anyString());
    }
}
