package com.agrifood.platform.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "suppliers")
public class Supplier {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    private String name;
    private String email;
    private String phone;
    private String region;
    private String address;
    
    @Enumerated(EnumType.STRING)
    private QualificationStatus qualificationStatus;
    
    private Instant registeredAt;
    private Instant certificationExpiryDate;
    
    // Constructors
    public Supplier() {
        this.registeredAt = Instant.now();
        this.qualificationStatus = QualificationStatus.PENDING;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public QualificationStatus getQualificationStatus() { return qualificationStatus; }
    public void setQualificationStatus(QualificationStatus qualificationStatus) { 
        this.qualificationStatus = qualificationStatus; 
    }
    
    public Instant getRegisteredAt() { return registeredAt; }
    public void setRegisteredAt(Instant registeredAt) { this.registeredAt = registeredAt; }
    
    public Instant getCertificationExpiryDate() { return certificationExpiryDate; }
    public void setCertificationExpiryDate(Instant certificationExpiryDate) { 
        this.certificationExpiryDate = certificationExpiryDate; 
    }
    
    // Business methods
    public boolean isCertificationExpired() {
        return certificationExpiryDate != null && Instant.now().isAfter(certificationExpiryDate);
    }
}
