package com.jobhuntos.model;
import java.util.Objects;
public class Settings {
    private String key;
    private String value;
    public Settings() {}
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Settings)) return false;
        Settings settings = (Settings) o;
        return Objects.equals(key, settings.key);
    }
    @Override public int hashCode() { return Objects.hash(key); }
    @Override public String toString() { return "Settings{key='" + key + "'}"; }
}
