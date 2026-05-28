package org.example.controller; // ודא שזה ה-package הנכון שלכם

import org.example.model.QuestionDTO;
import org.example.service.QuestionService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin // מאפשר ל-React להתחבר בלי שגיאות חסימה
public class MathController {

    // 1. קריאה לשירות החדש במקום לישן
    private final QuestionService questionService;

    public MathController(QuestionService questionService) {
        this.questionService = questionService;
    }

    // 2. הנתיב שהיה לכם מקודם (השאר אותו כפי שהוא אצלך)
    @GetMapping("/get-question")
    public QuestionDTO getQuestion(@RequestParam int difficulty) {
        // 3. שימוש בפונקציה החדשה שיצרנו
        return questionService.generateQuestion(difficulty);
    }
    // נתיב חדש לבדיקת התשובה! (הפכתי את זה זמנית ל-GET כדי שיהיה לך קל לבדוק בדפדפן)
    @GetMapping("/check-answer")
    public String checkAnswer(@RequestParam String questionId, @RequestParam int answer) {
        boolean isCorrect = questionService.checkAnswer(questionId, answer);

        if (isCorrect) {
            return "🏆 תשובה נכונה! (10 נקודות)";
        } else {
            return "❌ טעות, נסה שוב במרוץ הבא.";
        }
    }
}