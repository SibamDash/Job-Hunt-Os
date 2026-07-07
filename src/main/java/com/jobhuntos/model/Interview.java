package com.jobhuntos.model;
import com.jobhuntos.model.enums.InterviewType;
import com.jobhuntos.model.enums.InterviewResult;
import java.time.LocalDateTime;
public class Interview extends BaseEntity {
    private Long applicationId;
    private InterviewType type;
    private LocalDateTime date;
    private InterviewResult result;
    private String feedback;
    private String questions;
    private String mistakes;
    public Interview() {}
    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }
    public InterviewType getType() { return type; }
    public void setType(InterviewType type) { this.type = type; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public InterviewResult getResult() { return result; }
    public void setResult(InterviewResult result) { this.result = result; }
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
    public String getQuestions() { return questions; }
    public void setQuestions(String questions) { this.questions = questions; }
    public String getMistakes() { return mistakes; }
    public void setMistakes(String mistakes) { this.mistakes = mistakes; }
    @Override public String toString() { return "Interview{id=" + getId() + ", type=" + type + "}"; }
}
