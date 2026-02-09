package com.agrifood.platform.service;

import com.agrifood.platform.domain.Supplier;
import com.agrifood.platform.exception.BadRequestException;
import com.agrifood.platform.exception.ResourceNotFoundException;
import com.agrifood.platform.repository.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupplierServiceTest {
    
    @Mock
    private SupplierRepository supplierRepository;
    
    @InjectMocks
    private SupplierService supplierService;
    
    private Supplier testSupplier;
    
    @BeforeEach
    void setUp() {
        testSupplier = new Supplier();
        testSupplier.setId("sup-123");
        testSupplier.setName("Test Supplier");
        testSupplier.setEmail("test@supplier.com");
        testSupplier.setRegion("Tashkent");
    }
    
    @Test
    void registerSupplier_Success() {
        when(supplierRepository.findByEmail("test@supplier.com")).thenReturn(null);
        when(supplierRepository.save(any(Supplier.class))).thenReturn(testSupplier);
        
        Supplier result = supplierService.registerSupplier(testSupplier);
        
        assertNotNull(result);
        assertEquals("sup-123", result.getId());
        assertEquals("test@supplier.com", result.getEmail());
        verify(supplierRepository, times(1)).findByEmail("test@supplier.com");
        verify(supplierRepository, times(1)).save(testSupplier);
    }
    
    @Test
    void registerSupplier_EmailExists_ThrowsException() {
        Supplier existingSupplier = new Supplier();
        existingSupplier.setEmail("test@supplier.com");
        when(supplierRepository.findByEmail("test@supplier.com")).thenReturn(existingSupplier);
        
        assertThrows(BadRequestException.class, () -> {
            supplierService.registerSupplier(testSupplier);
        });
        
        verify(supplierRepository, times(1)).findByEmail("test@supplier.com");
        verify(supplierRepository, never()).save(any(Supplier.class));
    }
    
    @Test
    void getSupplierById_Success() {
        when(supplierRepository.findById("sup-123")).thenReturn(Optional.of(testSupplier));
        
        Supplier result = supplierService.getSupplierById("sup-123");
        
        assertNotNull(result);
        assertEquals("sup-123", result.getId());
        verify(supplierRepository, times(1)).findById("sup-123");
    }
    
    @Test
    void getSupplierById_NotFound_ThrowsException() {
        when(supplierRepository.findById("invalid-id")).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> {
            supplierService.getSupplierById("invalid-id");
        });
        
        verify(supplierRepository, times(1)).findById("invalid-id");
    }
    
    @Test
    void getAllSuppliers_Success() {
        List<Supplier> suppliers = Arrays.asList(testSupplier, new Supplier());
        when(supplierRepository.findAll()).thenReturn(suppliers);
        
        List<Supplier> result = supplierService.getAllSuppliers();
        
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(supplierRepository, times(1)).findAll();
    }
    
    @Test
    void getSuppliersByRegion_Success() {
        List<Supplier> suppliers = Arrays.asList(testSupplier);
        when(supplierRepository.findByRegion("Tashkent")).thenReturn(suppliers);
        
        List<Supplier> result = supplierService.getSuppliersByRegion("Tashkent");
        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Tashkent", result.get(0).getRegion());
        verify(supplierRepository, times(1)).findByRegion("Tashkent");
    }
    
    @Test
    void updateSupplier_Success() {
        Supplier updatedData = new Supplier();
        updatedData.setName("Updated Name");
        updatedData.setEmail("updated@supplier.com");
        updatedData.setPhone("+998901234567");
        
        when(supplierRepository.findById("sup-123")).thenReturn(Optional.of(testSupplier));
        when(supplierRepository.save(any(Supplier.class))).thenReturn(testSupplier);
        
        Supplier result = supplierService.updateSupplier("sup-123", updatedData);
        
        assertNotNull(result);
        verify(supplierRepository, times(1)).findById("sup-123");
        verify(supplierRepository, times(1)).save(any(Supplier.class));
    }
}
