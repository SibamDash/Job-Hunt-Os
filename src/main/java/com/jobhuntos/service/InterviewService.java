package com.jobhuntos.service;
import com.jobhuntos.model.Interview;
import com.jobhuntos.repository.InterviewRepository;
import com.jobhuntos.exception.ValidationException;
import java.util.List;

public class InterviewService {
    private static final InterviewService instance = new InterviewService();
    private final InterviewRepository repository = new InterviewRepository();

    private InterviewService() {}
    public static InterviewService getInstance() { return instance; }

    public Interview save(Interview entity) {
        boolean isNew = entity.getId() == null;
        Interview saved = isNew ? repository.save(entity) : (repository.update(entity) ? entity : null);
        if (saved != null) {
            ActivityLogService.getInstance().log("Interview", saved.getId(), isNew ? "CREATED" : "UPDATED");
        }
        return saved;
    }

    public boolean delete(Long id) {
        boolean deleted = repository.delete(id);
        if (deleted) ActivityLogService.getInstance().log("Interview", id, "DELETED");
        return deleted;
    }

    public List<Interview> getAll(int limit, int offset) { return repository.findAll(limit, offset); }
}
