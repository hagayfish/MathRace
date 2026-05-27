package org.example.model;

public class QuestionDTO {
    private String questionId;
    private String text;
    private int timeLimit;

    public QuestionDTO(String questionId, String text, int timeLimit) {
        this.questionId = questionId;
        this.text = text;
        this.timeLimit = timeLimit;
    }

    // גטרים שחובה ל-Spring Boot כדי להפוך את זה ל-JSON
    public String getQuestionId() { return questionId; }
    public String getText() { return text; }
    public int getTimeLimit() { return timeLimit; }
}