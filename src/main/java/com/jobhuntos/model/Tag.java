package com.jobhuntos.model;
public class Tag extends BaseEntity {
    private String name;
    public Tag() {}
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    @Override public String toString() { return "Tag{id=" + getId() + ", name='" + name + "'}"; }
}
