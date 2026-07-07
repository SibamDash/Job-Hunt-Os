package com.jobhuntos.model;
import com.jobhuntos.model.enums.DocumentType;
public class Document extends BaseEntity {
    private Long applicationId;
    private String name;
    private DocumentType type;
    private String path;
    public Document() {}
    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public DocumentType getType() { return type; }
    public void setType(DocumentType type) { this.type = type; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
    @Override public String toString() { return "Document{id=" + getId() + ", name='" + name + "'}"; }
}
