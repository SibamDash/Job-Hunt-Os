package com.jobhuntos.service;
import com.jobhuntos.model.Recruiter;
import com.jobhuntos.repository.RecruiterRepository;
import com.jobhuntos.exception.ValidationException;
import java.util.List;

public class RecruiterService {
    private static final RecruiterService instance = new RecruiterService();
    private final RecruiterRepository repository = new RecruiterRepository();

    private RecruiterService() {}
    public static RecruiterService getInstance() { return instance; }

    public Recruiter save(Recruiter entity) {
        boolean isNew = entity.getId() == null;
        Recruiter saved = isNew ? repository.save(entity) : (repository.update(entity) ? entity : null);
        if (saved != null) {
            ActivityLogService.getInstance().log("Recruiter", saved.getId(), isNew ? "CREATED" : "UPDATED");
        }
        return saved;
    }

    public boolean delete(Long id) {
        boolean deleted = repository.delete(id);
        if (deleted) ActivityLogService.getInstance().log("Recruiter", id, "DELETED");
        return deleted;
    }

    public List<Recruiter> getAll(int limit, int offset) { return repository.findAll(limit, offset); }
}
