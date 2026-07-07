package com.jobhuntos.mapper;

import com.jobhuntos.model.Interview;
import com.jobhuntos.dto.InterviewDTO;

public class InterviewMapper {
    
    public static InterviewDTO toDTO(Interview entity) {
        if (entity == null) return null;
        InterviewDTO dto = new InterviewDTO();
        dto.setId(entity.getId());
        // Map other fields here
        return dto;
    }
    
    public static Interview toEntity(InterviewDTO dto) {
        if (dto == null) return null;
        Interview entity = new Interview();
        entity.setId(dto.getId());
        // Map other fields here
        return entity;
    }
}
