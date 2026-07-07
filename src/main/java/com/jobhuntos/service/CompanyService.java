package com.jobhuntos.service;
import com.jobhuntos.model.Company;
import com.jobhuntos.repository.CompanyRepository;
import com.jobhuntos.exception.ValidationException;
import com.jobhuntos.validator.RequiredFieldValidator;
import java.util.List;

public class CompanyService {
    private static final CompanyService instance = new CompanyService();
    private final CompanyRepository repository = new CompanyRepository();

    private CompanyService() {}
    public static CompanyService getInstance() { return instance; }

    public Company save(Company c) {
        if (!RequiredFieldValidator.isNotEmpty(c.getName())) {
            throw new ValidationException("Company name is required.");
        }
        // Prevent exact duplicates (simple check)
        List<Company> existing = repository.searchByName(c.getName(), 1, 0);
        if (!existing.isEmpty() && existing.get(0).getName().equalsIgnoreCase(c.getName()) 
            && (c.getId() == null || !c.getId().equals(existing.get(0).getId()))) {
            throw new ValidationException("A company with this name already exists.");
        }
        
        boolean isNew = c.getId() == null;
        Company saved = isNew ? repository.save(c) : (repository.update(c) ? c : null);
        if (saved != null) {
            ActivityLogService.getInstance().log("Company", saved.getId(), isNew ? "CREATED" : "UPDATED");
        }
        return saved;
    }

    public boolean delete(Long id) {
        boolean deleted = repository.delete(id);
        if (deleted) ActivityLogService.getInstance().log("Company", id, "DELETED");
        return deleted;
    }

    public List<Company> getAll(int limit, int offset) { return repository.findAll(limit, offset); }
    public List<Company> search(String term, int limit, int offset) { return repository.searchByName(term, limit, offset); }
}
