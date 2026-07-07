package com.jobhuntos.service;
import com.jobhuntos.model.Document;
import com.jobhuntos.repository.DocumentRepository;
import com.jobhuntos.exception.ValidationException;
import java.util.List;

public class DocumentService {
    private static final DocumentService instance = new DocumentService();
    private final DocumentRepository repository = new DocumentRepository();

    private DocumentService() {}
    public static DocumentService getInstance() { return instance; }

    public Document save(Document entity) {
        boolean isNew = entity.getId() == null;
        Document saved = isNew ? repository.save(entity) : (repository.update(entity) ? entity : null);
        if (saved != null) {
            ActivityLogService.getInstance().log("Document", saved.getId(), isNew ? "CREATED" : "UPDATED");
        }
        return saved;
    }

    public boolean delete(Long id) {
        boolean deleted = repository.delete(id);
        if (deleted) ActivityLogService.getInstance().log("Document", id, "DELETED");
        return deleted;
    }

    public List<Document> getAll(int limit, int offset) { return repository.findAll(limit, offset); }
}
