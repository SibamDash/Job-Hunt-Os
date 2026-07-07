package com.jobhuntos.mapper;

import com.jobhuntos.model.Application;
import com.jobhuntos.dto.ApplicationDTO;

public class ApplicationMapper {
    
    public static ApplicationDTO toDTO(Application entity) {
        if (entity == null) return null;
        ApplicationDTO dto = new ApplicationDTO();
        dto.setId(entity.getId());
        // Map other fields here
        return dto;
    }
    
    public static Application toEntity(ApplicationDTO dto) {
        if (dto == null) return null;
        Application entity = new Application();
        entity.setId(dto.getId());
        // Map other fields here
        return entity;
    }
}
