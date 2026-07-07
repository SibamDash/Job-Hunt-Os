package com.jobhuntos.service;
import com.jobhuntos.model.Application;
import com.jobhuntos.repository.ApplicationRepository;
import com.jobhuntos.exception.ValidationException;
import com.jobhuntos.validator.RequiredFieldValidator;
import com.jobhuntos.validator.DateValidator;
import com.jobhuntos.validator.SalaryValidator;
import java.util.List;

public class ApplicationService {
    private static final ApplicationService instance = new ApplicationService();
    private final ApplicationRepository repository = new ApplicationRepository();

    private ApplicationService() {}
    public static ApplicationService getInstance() { return instance; }

    public Application save(Application app) {
        if (!RequiredFieldValidator.isNotNull(app.getCompanyId())) throw new ValidationException("Company is required.");
        if (!RequiredFieldValidator.isNotEmpty(app.getRole())) throw new ValidationException("Role is required.");
        if (!SalaryValidator.isValid(app.getSalary())) throw new ValidationException("Invalid salary format.");
        if (app.getDeadline() != null && app.getApplyDate() != null && !DateValidator.isAfterOrEqual(app.getApplyDate(), app.getDeadline())) {
            throw new ValidationException("Deadline cannot be before apply date.");
        }
        
        boolean isNew = app.getId() == null;
        Application saved = isNew ? repository.save(app) : (repository.update(app) ? app : null);
        if (saved != null) {
            ActivityLogService.getInstance().log("Application", saved.getId(), isNew ? "CREATED" : "UPDATED: Status=" + saved.getStatus());
        }
        return saved;
    }

    public boolean delete(Long id) {
        boolean deleted = repository.delete(id);
        if (deleted) ActivityLogService.getInstance().log("Application", id, "DELETED");
        return deleted;
    }

    public List<Application> getAll(int limit, int offset) { return repository.findAll(limit, offset); }
    public Application getById(Long id) { return repository.findById(id).orElse(null); }
}
