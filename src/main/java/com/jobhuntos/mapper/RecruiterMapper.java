package com.jobhuntos.mapper;

import com.jobhuntos.model.Recruiter;
import com.jobhuntos.dto.RecruiterDTO;

public class RecruiterMapper {
    
    public static RecruiterDTO toDTO(Recruiter entity) {
        if (entity == null) return null;
        RecruiterDTO dto = new RecruiterDTO();
        dto.setId(entity.getId());
        // Map other fields here
        return dto;
    }
    
    public static Recruiter toEntity(RecruiterDTO dto) {
        if (dto == null) return null;
        Recruiter entity = new Recruiter();
        entity.setId(dto.getId());
        // Map other fields here
        return entity;
    }
}
