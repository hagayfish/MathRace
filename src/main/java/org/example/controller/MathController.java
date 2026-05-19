package org.example.controller;

import org.example.service.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/math")
@CrossOrigin(origins = "*")
public class MathController {

    @Autowired
    private MathService mathService;

    // הכתובת החדשה: http://localhost:8080/api/math/generate?difficulty=1
    @GetMapping("/generate")
    public String getQuestion(@RequestParam(defaultValue = "1") int difficulty) {
        return mathService.generateQuestionByDifficulty(difficulty);
    }
}