package com.jobhuntos.model;
import com.jobhuntos.model.enums.ResumeType;
public class Resume extends BaseEntity {
    private String name;
    private String path;
    private ResumeType type;
    public Resume() {}
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
    public ResumeType getType() { return type; }
    public void setType(ResumeType type) { this.type = type; }
    @Override public String toString() { return "Resume{id=" + getId() + ", name='" + name + "'}"; }
}
