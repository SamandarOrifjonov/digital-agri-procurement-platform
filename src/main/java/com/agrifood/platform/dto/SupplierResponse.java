package com.agrifood.platform.dto;

import java.time.Instant;

public class SupplierResponse {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String region;
    private String address;
    private String qualificationStatus;
    private Instant registeredAt;
    private Instant certificationExpiryDate;
    private boolean certificationExpired;
    
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
    
    public String getQualificationStatus() { return qualificationStatus; }
    public void setQualificationStatus(String qualificationStatus) { this.qualificationStatus = qualificationStatus; }
    
    public Instant getRegisteredAt() { return registeredAt; }
    public void setRegisteredAt(Instant registeredAt) { this.registeredAt = registeredAt; }
    
    public Instant getCertificationExpiryDate() { return certificationExpiryDate; }
    public void setCertificationExpiryDate(Instant certificationExpiryDate) { this.certificationExpiryDate = certificationExpiryDate; }
    
    public boolean isCertificationExpired() { return certificationExpired; }
    public void setCertificationExpired(boolean certificationExpired) { this.certificationExpired = certificationExpired; }
}
