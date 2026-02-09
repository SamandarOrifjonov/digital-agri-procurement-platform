package com.agrifood.platform.controller;

import com.agrifood.platform.domain.Opportunity;
import com.agrifood.platform.dto.OpportunityRequest;
import com.agrifood.platform.dto.OpportunityResponse;
import com.agrifood.platform.mapper.OpportunityMapper;
import com.agrifood.platform.service.OpportunityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/opportunities")
@Tag(name = "Opportunities", description = "Procurement opportunities management")
public class OpportunityController {
    
    private final OpportunityService opportunityService;
    private final OpportunityMapper opportunityMapper;
    
    public OpportunityController(OpportunityService opportunityService, OpportunityMapper opportunityMapper) {
        this.opportunityService = opportunityService;
        this.opportunityMapper = opportunityMapper;
    }
    
    @PostMapping
    @Operation(summary = "Create a new opportunity")
    public ResponseEntity<OpportunityResponse> createOpportunity(@Valid @RequestBody OpportunityRequest request) {
        Opportunity opportunity = opportunityMapper.toEntity(request);
        Opportunity created = opportunityService.createOpportunity(opportunity);
        return ResponseEntity.status(HttpStatus.CREATED).body(opportunityMapper.toResponse(created));
    }
    
    @PostMapping("/{id}/publish")
    @Operation(summary = "Publish an opportunity")
    public ResponseEntity<OpportunityResponse> publishOpportunity(@PathVariable String id) {
        Opportunity published = opportunityService.publishOpportunity(id);
        return ResponseEntity.ok(opportunityMapper.toResponse(published));
    }
    
    @GetMapping
    @Operation(summary = "Get all opportunities")
    public ResponseEntity<List<OpportunityResponse>> getAllOpportunities() {
        List<OpportunityResponse> opportunities = opportunityService.getAllOpportunities()
            .stream()
            .map(opportunityMapper::toResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(opportunities);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get opportunity by ID")
    public ResponseEntity<OpportunityResponse> getOpportunityById(@PathVariable String id) {
        Opportunity opportunity = opportunityService.getOpportunityById(id);
        return ResponseEntity.ok(opportunityMapper.toResponse(opportunity));
    }
    
    @GetMapping("/category/{category}")
    @Operation(summary = "Get opportunities by category")
    public ResponseEntity<List<OpportunityResponse>> getOpportunitiesByCategory(@PathVariable String category) {
        List<OpportunityResponse> opportunities = opportunityService.getOpportunitiesByCategory(category)
            .stream()
            .map(opportunityMapper::toResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(opportunities);
    }
    
    @GetMapping("/region/{region}")
    @Operation(summary = "Get opportunities by region")
    public ResponseEntity<List<OpportunityResponse>> getOpportunitiesByRegion(@PathVariable String region) {
        List<OpportunityResponse> opportunities = opportunityService.getOpportunitiesByRegion(region)
            .stream()
            .map(opportunityMapper::toResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(opportunities);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an opportunity")
    public ResponseEntity<Void> deleteOpportunity(@PathVariable String id) {
        opportunityService.deleteOpportunity(id);
        return ResponseEntity.noContent().build();
    }
}
