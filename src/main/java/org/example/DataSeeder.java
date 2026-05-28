package org.example; // שנה את זה ל-package שלך אם צריך

import org.example.model.GameObject;
import org.example.model.Name;
import org.example.model.Template;
import org.example.repository.GameObjectRepository;
import org.example.repository.NameRepository;
import org.example.repository.TemplateRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final TemplateRepository templateRepository;
    private final NameRepository nameRepository;
    private final GameObjectRepository gameObjectRepository;

    public DataSeeder(TemplateRepository templateRepository, NameRepository nameRepository, GameObjectRepository gameObjectRepository) {
        this.templateRepository = templateRepository;
        this.nameRepository = nameRepository;
        this.gameObjectRepository = gameObjectRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // בודקים אם טבלת השמות ריקה, ורק אז מכניסים נתונים
        if (nameRepository.count() == 0) {
            Name n1 = new Name(); n1.setName("נועה"); nameRepository.save(n1);
            Name n2 = new Name(); n2.setName("רועי"); nameRepository.save(n2);
            Name n3 = new Name(); n3.setName("דני"); nameRepository.save(n3);
        }

        // בודקים אם טבלת החפצים ריקה
        if (gameObjectRepository.count() == 0) {
            GameObject g1 = new GameObject(); g1.setObjectName("ארטיק"); gameObjectRepository.save(g1);
            GameObject g2 = new GameObject(); g2.setObjectName("כדור"); gameObjectRepository.save(g2);
            GameObject g3 = new GameObject(); g3.setObjectName("ספר"); gameObjectRepository.save(g3);
        }

        // בודקים אם טבלת התבניות לשאלות ריקה
        if (templateRepository.count() == 0) {
            Template t1 = new Template();
            t1.setExpression("X + Y");
            t1.setDifficulty(1);
            t1.setWordProblem(false);
            templateRepository.save(t1);

            Template t2 = new Template();
            t2.setExpression("X - Y");
            t2.setDifficulty(1);
            t2.setWordProblem(false);
            templateRepository.save(t2);

            Template t3 = new Template();
            t3.setExpression("{NAME} קנה {AMOUNT} {OBJECT}, ואז קיבל עוד 2. כמה יש לו סהכ?");
            t3.setDifficulty(1);
            t3.setWordProblem(true);
            templateRepository.save(t3);
        }

        System.out.println("✅ נתוני המשחק נטענו בהצלחה למסד הנתונים!");
    }
}