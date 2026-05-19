package org.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "templates")
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String expression; // למשל: "X + Y" או "X - Y"
    private int difficulty;    // רמת קושי: 1 (קל), 2 (בינוני), 3 (קשה)

    // קונסטרקטורים
    public Template() {}

    public Template(String expression, int difficulty) {
        this.expression = expression;
        this.difficulty = difficulty;
    }

    // גטרים וסטרים
    public Long getId() { return id; }
    public String getExpression() { return expression; }
    public void setExpression(String expression) { this.expression = expression; }
    public int getDifficulty() { return difficulty; }
    public void setDifficulty(int difficulty) { this.difficulty = difficulty; }
}