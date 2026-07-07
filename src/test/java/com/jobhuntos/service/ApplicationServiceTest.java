package com.jobhuntos.service;

import com.jobhuntos.model.Application;
import com.jobhuntos.exception.ValidationException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationServiceTest {

    @Test
    public void testSave_MissingCompanyId_ThrowsException() {
        Application app = new Application();
        app.setRole("Software Engineer");
        
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            ApplicationService.getInstance().save(app);
        });
        
        assertEquals("Company is required.", exception.getMessage());
    }

    @Test
    public void testSave_MissingRole_ThrowsException() {
        Application app = new Application();
        app.setCompanyId(1L);
        
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            ApplicationService.getInstance().save(app);
        });
        
        assertEquals("Role is required.", exception.getMessage());
    }
}
