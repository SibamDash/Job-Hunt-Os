package com.jobhuntos.service;
import com.jobhuntos.model.Resume;
import com.jobhuntos.repository.ResumeRepository;
import com.jobhuntos.exception.ValidationException;
import java.util.List;

public class ResumeService {
    private static final ResumeService instance = new ResumeService();
    private final ResumeRepository repository = new ResumeRepository();

    private ResumeService() {}
    public static ResumeService getInstance() { return instance; }

    public Resume save(Resume entity) {
        boolean isNew = entity.getId() == null;
        Resume saved = isNew ? repository.save(entity) : (repository.update(entity) ? entity : null);
        if (saved != null) {
            ActivityLogService.getInstance().log("Resume", saved.getId(), isNew ? "CREATED" : "UPDATED");
        }
        return saved;
    }

    public boolean delete(Long id) {
        boolean deleted = repository.delete(id);
        if (deleted) ActivityLogService.getInstance().log("Resume", id, "DELETED");
        return deleted;
    }

    public List<Resume> getAll(int limit, int offset) { return repository.findAll(limit, offset); }
}
