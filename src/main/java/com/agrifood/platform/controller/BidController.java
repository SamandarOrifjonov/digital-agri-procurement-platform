package com.agrifood.platform.controller;

import com.agrifood.platform.domain.Bid;
import com.agrifood.platform.dto.BidRequest;
import com.agrifood.platform.dto.BidResponse;
import com.agrifood.platform.mapper.BidMapper;
import com.agrifood.platform.service.BidService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bids")
@Tag(name = "Bids", description = "Bid submission and management")
public class BidController {
    
    private final BidService bidService;
    private final BidMapper bidMapper;
    
    public BidController(BidService bidService, BidMapper bidMapper) {
        this.bidService = bidService;
        this.bidMapper = bidMapper;
    }
    
    @PostMapping
    @Operation(summary = "Submit a new bid")
    public ResponseEntity<BidResponse> submitBid(@Valid @RequestBody BidRequest request) {
        Bid bid = bidMapper.toEntity(request);
        Bid saved = bidService.submitBid(bid, request.getOpportunityId());
        return ResponseEntity.status(HttpStatus.CREATED).body(bidMapper.toResponse(saved));
    }
    
    @GetMapping
    @Operation(summary = "Get all bids")
    public ResponseEntity<List<BidResponse>> getAllBids() {
        List<BidResponse> bids = bidService.getAllBids()
            .stream()
            .map(bidMapper::toResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(bids);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get bid by ID")
    public ResponseEntity<BidResponse> getBidById(@PathVariable String id) {
        Bid bid = bidService.getBidById(id);
        return ResponseEntity.ok(bidMapper.toResponse(bid));
    }
    
    @GetMapping("/supplier/{supplierId}")
    @Operation(summary = "Get bids by supplier")
    public ResponseEntity<List<BidResponse>> getBidsBySupplier(@PathVariable String supplierId) {
        List<BidResponse> bids = bidService.getBidsBySupplier(supplierId)
            .stream()
            .map(bidMapper::toResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(bids);
    }
    
    @GetMapping("/opportunity/{opportunityId}")
    @Operation(summary = "Get bids by opportunity")
    public ResponseEntity<List<BidResponse>> getBidsByOpportunity(@PathVariable String opportunityId) {
        List<BidResponse> bids = bidService.getBidsByOpportunity(opportunityId)
            .stream()
            .map(bidMapper::toResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(bids);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a bid")
    public ResponseEntity<Void> deleteBid(@PathVariable String id) {
        bidService.deleteBid(id);
        return ResponseEntity.noContent().build();
    }
}
