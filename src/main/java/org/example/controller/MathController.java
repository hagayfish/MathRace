package org.example.controller;

import org.example.model.Template;
import org.example.service.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/math")
@CrossOrigin(origins = "*") // מאפשר ל-React שלכם להתחבר לשרת בלי חסימות אבטחה
public class MathController {

    @Autowired
    private MathService mathService;

    // כתובת שתחזיר את כל התבניות: http://localhost:8080/api/math/templates
    @GetMapping("/templates")
    public List<Template> getTemplates() {
        return mathService.getAllTemplates();
    }

    // כתובת לייצור שאלה: http://localhost:8080/api/math/generate/{id}
    @GetMapping("/generate/{id}")
    public String getQuestion(@PathVariable Long id) {
        return mathService.generateQuestion(id);
    }
}