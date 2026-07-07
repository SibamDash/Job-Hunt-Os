package com.jobhuntos.model;
public class Company extends BaseEntity {
    private String name;
    private String website;
    private String glassdoor;
    private String packageInfo;
    private String techStack;
    private String notes;
    public Company() {}
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
    public String getGlassdoor() { return glassdoor; }
    public void setGlassdoor(String glassdoor) { this.glassdoor = glassdoor; }
    public String getPackageInfo() { return packageInfo; }
    public void setPackageInfo(String packageInfo) { this.packageInfo = packageInfo; }
    public String getTechStack() { return techStack; }
    public void setTechStack(String techStack) { this.techStack = techStack; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    @Override public String toString() { return "Company{id=" + getId() + ", name='" + name + "'}"; }
}
