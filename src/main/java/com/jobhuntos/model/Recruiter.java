package com.jobhuntos.model;
import com.jobhuntos.model.enums.ConnectionStatus;
import java.time.LocalDate;
public class Recruiter extends BaseEntity {
    private Long companyId;
    private String name;
    private String linkedin;
    private String email;
    private String phone;
    private ConnectionStatus connectionStatus;
    private LocalDate lastContact;
    private LocalDate followUpDate;
    private String notes;
    public Recruiter() {}
    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLinkedin() { return linkedin; }
    public void setLinkedin(String linkedin) { this.linkedin = linkedin; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public ConnectionStatus getConnectionStatus() { return connectionStatus; }
    public void setConnectionStatus(ConnectionStatus connectionStatus) { this.connectionStatus = connectionStatus; }
    public LocalDate getLastContact() { return lastContact; }
    public void setLastContact(LocalDate lastContact) { this.lastContact = lastContact; }
    public LocalDate getFollowUpDate() { return followUpDate; }
    public void setFollowUpDate(LocalDate followUpDate) { this.followUpDate = followUpDate; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    @Override public String toString() { return "Recruiter{id=" + getId() + ", name='" + name + "'}"; }
}
