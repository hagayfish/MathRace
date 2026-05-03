package org.example.service;

import org.example.repository.TemplateRepository;
import org.hibernate.sql.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;

@Service
public class MathService {

    @Autowired
    private TemplateRepository templateRepository;

    private Random random = new Random();

    // פונקציה שמביאה את כל התבניות שיש לנו
    public List<org.example.model.Template> getAllTemplates() {
        return templateRepository.findAll();
    }

    // פונקציה לדוגמה שמייצרת שאלה אקראית
    public String generateQuestion(Long templateId) {
        Template template = templateRepository.findById(templateId).orElse(null);
        if (template == null) return "Template not found";

        // כאן בהמשך נוסיף לוגיקה שמחליפה את ה-X וה-Y במספרים אקראיים
        return "Generated question based on: " + template.getExpression();
    }
}
