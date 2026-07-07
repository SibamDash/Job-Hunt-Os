package com.jobhuntos.mapper;

import com.jobhuntos.model.Company;
import com.jobhuntos.dto.CompanyDTO;

public class CompanyMapper {
    
    public static CompanyDTO toDTO(Company entity) {
        if (entity == null) return null;
        CompanyDTO dto = new CompanyDTO();
        dto.setId(entity.getId());
        // Map other fields here
        return dto;
    }
    
    public static Company toEntity(CompanyDTO dto) {
        if (dto == null) return null;
        Company entity = new Company();
        entity.setId(dto.getId());
        // Map other fields here
        return entity;
    }
}
