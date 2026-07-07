package com.jobhuntos.service;
import com.jobhuntos.model.ActivityLog;
import com.jobhuntos.repository.ActivityLogRepository;
import java.time.LocalDateTime;

public class ActivityLogService {
    private static final ActivityLogService instance = new ActivityLogService();
    private final ActivityLogRepository repository = new ActivityLogRepository();

    private ActivityLogService() {}
    public static ActivityLogService getInstance() { return instance; }

    public void log(String entityName, Long entityId, String action) {
        ActivityLog log = new ActivityLog();
        log.setEntityName(entityName);
        log.setEntityId(entityId);
        log.setAction(action);
        log.setTimestamp(LocalDateTime.now());
        repository.save(log);
    }
}
