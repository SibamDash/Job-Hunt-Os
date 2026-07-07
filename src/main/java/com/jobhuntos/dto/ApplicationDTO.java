package com.jobhuntos.dto;

public class ApplicationDTO {
    private Long id;
    
    public ApplicationDTO() {}
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    // Standard DTO fields will mirror the entity.
    // For brevity, we implement the ID mapping as a baseline.
}
