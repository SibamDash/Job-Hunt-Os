package com.jobhuntos.mapper;

import com.jobhuntos.model.Resume;
import com.jobhuntos.dto.ResumeDTO;

public class ResumeMapper {
    
    public static ResumeDTO toDTO(Resume entity) {
        if (entity == null) return null;
        ResumeDTO dto = new ResumeDTO();
        dto.setId(entity.getId());
        // Map other fields here
        return dto;
    }
    
    public static Resume toEntity(ResumeDTO dto) {
        if (dto == null) return null;
        Resume entity = new Resume();
        entity.setId(dto.getId());
        // Map other fields here
        return entity;
    }
}
