package org.example.service;

import org.example.model.QuestionDTO;
import org.example.model.GameObject;
import org.example.model.Name;
import org.example.model.Template;
import org.example.repository.GameObjectRepository;
import org.example.repository.NameRepository;
import org.example.repository.TemplateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class QuestionService {

    private final TemplateRepository templateRepository;
    private final NameRepository nameRepository;
    private final GameObjectRepository gameObjectRepository;
    private final Random random = new Random();

    // ה"פנקס" שלנו: שומר איזה ID שייך לאיזו תשובה נכונה
    private final Map<String, Integer> questionMemory = new ConcurrentHashMap<>();

    public QuestionService(TemplateRepository templateRepository, NameRepository nameRepository, GameObjectRepository gameObjectRepository) {
        this.templateRepository = templateRepository;
        this.nameRepository = nameRepository;
        this.gameObjectRepository = gameObjectRepository;
    }

    // שינינו את סוג ההחזרה ל-QuestionDTO
    public QuestionDTO generateQuestion(int difficulty) {
        List<Template> templates = templateRepository.findByDifficulty(difficulty);

        if (templates.isEmpty()) {
            return new QuestionDTO("0", "אין שאלות זמינות", 0);
        }

        Template selectedTemplate = templates.get(random.nextInt(templates.size()));
        String questionText = selectedTemplate.getExpression();

        int min = 1, max = 10;
        if (difficulty == 2) max = 50;
        else if (difficulty == 3) max = 100;

        int num1 = random.nextInt((max - min) + 1) + min;
        int num2 = random.nextInt((max - min) + 1) + min;

        if (questionText.contains("-") && num1 < num2) {
            int temp = num1; num1 = num2; num2 = temp;
        }

        // --- חישוב התשובה הנכונה בזמן אמת ---
        int correctAnswer = 0;
        if (selectedTemplate.isWordProblem()) {
            correctAnswer = num1 + 2; // לפי התבנית שלנו ב-DB שאומרת "קיבל עוד 2"
        } else if (questionText.contains("+")) {
            correctAnswer = num1 + num2;
        } else if (questionText.contains("-")) {
            correctAnswer = num1 - num2;
        } else if (questionText.contains("*")) {
            correctAnswer = num1 * num2;
        }

        // השתלת המילים בשאלה
        if (selectedTemplate.isWordProblem()) {
            List<Name> names = nameRepository.findAll();
            List<GameObject> objects = gameObjectRepository.findAll();
            if (!names.isEmpty()) questionText = questionText.replace("{NAME}", names.get(random.nextInt(names.size())).getName());
            if (!objects.isEmpty()) questionText = questionText.replace("{OBJECT}", objects.get(random.nextInt(objects.size())).getObjectName());
            questionText = questionText.replace("{AMOUNT}", String.valueOf(num1));
        } else {
            questionText = questionText.replace("X", String.valueOf(num1));
            questionText = questionText.replace("Y", String.valueOf(num2));
        }

        // יצירת תעודת זהות ייחודית לשאלה, ושמירת התשובה בזיכרון
        String questionId = UUID.randomUUID().toString();
        questionMemory.put(questionId, correctAnswer);

        // מחזיר את האובייקט החדש (15 שניות לשאלה)
        return new QuestionDTO(questionId, questionText, 15);
    }

    // פונקציה חדשה: קבלת תשובה מהתלמיד ובדיקה
    public boolean checkAnswer(String questionId, int userAnswer) {
        Integer correct = questionMemory.get(questionId);
        if (correct == null) {
            return false; // השאלה לא קיימת או שכבר ענו עליה
        }

        // מוחק את השאלה מהזיכרון כדי שלא יוכלו לענות עליה פעמיים
        questionMemory.remove(questionId);

        return correct == userAnswer;
    }
}