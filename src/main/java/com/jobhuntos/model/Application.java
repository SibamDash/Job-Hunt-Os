package com.jobhuntos.model;
import com.jobhuntos.model.enums.ApplicationStatus;
import com.jobhuntos.model.enums.Priority;
import java.time.LocalDate;
public class Application extends BaseEntity {
    private Long companyId;
    private String role;
    private String salary;
    private String location;
    private String portal;
    private LocalDate applyDate;
    private ApplicationStatus status;
    private Priority priority;
    private LocalDate deadline;
    private Long resumeId;
    private String notes;
    public Application() {}
    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getSalary() { return salary; }
    public void setSalary(String salary) { this.salary = salary; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getPortal() { return portal; }
    public void setPortal(String portal) { this.portal = portal; }
    public LocalDate getApplyDate() { return applyDate; }
    public void setApplyDate(LocalDate applyDate) { this.applyDate = applyDate; }
    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }
    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
    public Long getResumeId() { return resumeId; }
    public void setResumeId(Long resumeId) { this.resumeId = resumeId; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    @Override public String toString() { return "Application{id=" + getId() + ", role='" + role + "'}"; }
}
