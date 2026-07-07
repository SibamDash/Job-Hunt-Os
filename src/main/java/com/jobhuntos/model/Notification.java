package com.jobhuntos.model;
import com.jobhuntos.model.enums.NotificationStatus;
import java.time.LocalDateTime;
public class Notification extends BaseEntity {
    private String title;
    private String message;
    private LocalDateTime date;
    private NotificationStatus status;
    public Notification() {}
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public NotificationStatus getStatus() { return status; }
    public void setStatus(NotificationStatus status) { this.status = status; }
    @Override public String toString() { return "Notification{id=" + getId() + ", title='" + title + "'}"; }
}
