package com.jobhuntos.model;
import com.jobhuntos.model.enums.Priority;
import java.time.LocalDate;
public class Task extends BaseEntity {
    private Long applicationId;
    private String title;
    private String description;
    private LocalDate dueDate;
    private Priority priority;
    private boolean completed;
    public Task() {}
    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    @Override public String toString() { return "Task{id=" + getId() + ", title='" + title + "'}"; }
}
