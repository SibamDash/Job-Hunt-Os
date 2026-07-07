package com.jobhuntos.model;
import java.time.LocalDateTime;
public class ActivityLog extends BaseEntity {
    private String entityName;
    private Long entityId;
    private String action;
    private LocalDateTime timestamp;
    public ActivityLog() {}
    public String getEntityName() { return entityName; }
    public void setEntityName(String entityName) { this.entityName = entityName; }
    public Long getEntityId() { return entityId; }
    public void setEntityId(Long entityId) { this.entityId = entityId; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    @Override public String toString() { return "ActivityLog{id=" + getId() + ", action='" + action + "'}"; }
}
