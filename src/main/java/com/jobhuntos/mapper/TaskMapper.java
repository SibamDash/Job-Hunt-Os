package com.jobhuntos.mapper;

import com.jobhuntos.model.Task;
import com.jobhuntos.dto.TaskDTO;

public class TaskMapper {
    
    public static TaskDTO toDTO(Task entity) {
        if (entity == null) return null;
        TaskDTO dto = new TaskDTO();
        dto.setId(entity.getId());
        // Map other fields here
        return dto;
    }
    
    public static Task toEntity(TaskDTO dto) {
        if (dto == null) return null;
        Task entity = new Task();
        entity.setId(dto.getId());
        // Map other fields here
        return entity;
    }
}
