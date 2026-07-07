package com.jobhuntos.mapper;

import com.jobhuntos.model.Document;
import com.jobhuntos.dto.DocumentDTO;

public class DocumentMapper {
    
    public static DocumentDTO toDTO(Document entity) {
        if (entity == null) return null;
        DocumentDTO dto = new DocumentDTO();
        dto.setId(entity.getId());
        // Map other fields here
        return dto;
    }
    
    public static Document toEntity(DocumentDTO dto) {
        if (dto == null) return null;
        Document entity = new Document();
        entity.setId(dto.getId());
        // Map other fields here
        return entity;
    }
}
