package org.example.service;

import org.example.model.Template;
import org.example.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;

@Service
public class MathService {
    @Autowired
    private TemplateRepository templateRepository;

    private final Random random = new Random();

    // מייצר שאלה אקראית לפי רמת קושי
    public String generateQuestionByDifficulty(int difficulty) {
        // 1. שליפת כל התבניות שמתאימות לרמה המבוקשת
        List<Template> templates = templateRepository.findByDifficulty(difficulty);

        if (templates.isEmpty()) {
            return "אין תבניות זמינות לרמה זו";
        }

        // 2. בחירת תבנית אחת באקראי מתוך הרשימה
        Template chosenTemplate = templates.get(random.nextInt(templates.size()));
        String expression = chosenTemplate.getExpression(); // למשל "X + Y"

        // 3. הגדרת טווח המספרים להגרלה לפי הרמה
        int min = 1, max = 10;
        if (difficulty == 2) { max = 50; }
        else if (difficulty == 3) { max = 100; }

        int x = random.nextInt((max - min) + 1) + min;
        int y = random.nextInt((max - min) + 1) + min;

        // 4. הגנה מפני מספרים שליליים בחיסור (חוק משחק חשוב לילדים!)
        if (expression.contains("-") && x < y) {
            // נחליף ביניהם כדי ש-X תמיד יהיה הגדול יותר
            int temp = x;
            x = y;
            y = temp;
        }

        // 5. השתלת המספרים שהגרלנו בתוך מחרוזת התבנית
        String finalQuestion = expression.replace("X", String.valueOf(x))
                .replace("Y", String.valueOf(y));

        return finalQuestion;
    }
}
