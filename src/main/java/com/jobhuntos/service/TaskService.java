package com.jobhuntos.service;
import com.jobhuntos.model.Task;
import com.jobhuntos.repository.TaskRepository;
import com.jobhuntos.exception.ValidationException;
import java.util.List;

public class TaskService {
    private static final TaskService instance = new TaskService();
    private final TaskRepository repository = new TaskRepository();

    private TaskService() {}
    public static TaskService getInstance() { return instance; }

    public Task save(Task entity) {
        boolean isNew = entity.getId() == null;
        Task saved = isNew ? repository.save(entity) : (repository.update(entity) ? entity : null);
        if (saved != null) {
            ActivityLogService.getInstance().log("Task", saved.getId(), isNew ? "CREATED" : "UPDATED");
        }
        return saved;
    }

    public boolean delete(Long id) {
        boolean deleted = repository.delete(id);
        if (deleted) ActivityLogService.getInstance().log("Task", id, "DELETED");
        return deleted;
    }

    public List<Task> getAll(int limit, int offset) { return repository.findAll(limit, offset); }
}
