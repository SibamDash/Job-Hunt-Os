package com.jobhuntos.model;
import java.util.Objects;
public class ApplicationTag {
    private Long applicationId;
    private Long tagId;
    public ApplicationTag() {}
    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }
    public Long getTagId() { return tagId; }
    public void setTagId(Long tagId) { this.tagId = tagId; }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApplicationTag)) return false;
        ApplicationTag that = (ApplicationTag) o;
        return Objects.equals(applicationId, that.applicationId) && Objects.equals(tagId, that.tagId);
    }
    @Override public int hashCode() { return Objects.hash(applicationId, tagId); }
    @Override public String toString() { return "ApplicationTag{appId=" + applicationId + ", tagId=" + tagId + "}"; }
}
