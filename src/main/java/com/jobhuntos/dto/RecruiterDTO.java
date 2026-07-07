package com.jobhuntos.dto;

public class RecruiterDTO {
    private Long id;
    
    public RecruiterDTO() {}
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    // Standard DTO fields will mirror the entity.
    // For brevity, we implement the ID mapping as a baseline.
}
