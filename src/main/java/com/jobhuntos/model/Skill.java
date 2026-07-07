package com.jobhuntos.model;
public class Skill extends BaseEntity {
    private String name;
    private int progress;
    public Skill() {}
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getProgress() { return progress; }
    public void setProgress(int progress) { this.progress = progress; }
    @Override public String toString() { return "Skill{id=" + getId() + ", name='" + name + "'}"; }
}
