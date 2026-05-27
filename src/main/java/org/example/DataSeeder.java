package org.example;

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
        // בודק אם מאגר השאלות ריק
        if (templateRepository.count() == 0) {
            System.out.println("⏳ מתחיל להזריק נתונים בצורה בטוחה דרך Repositories...");

            // הכנסת תבניות
            Template t1 = new Template(); t1.setExpression("X + Y"); t1.setDifficulty(1); t1.setWordProblem(false);
            Template t2 = new Template(); t2.setExpression("X - Y"); t2.setDifficulty(1); t2.setWordProblem(false);
            Template t3 = new Template(); t3.setExpression("X + Y"); t3.setDifficulty(2); t3.setWordProblem(false);
            Template t4 = new Template(); t4.setExpression("X * Y"); t4.setDifficulty(2); t4.setWordProblem(false);
            Template t5 = new Template(); t5.setExpression("{NAME} קנה {AMOUNT} {OBJECT}, ואז קיבל עוד 2. כמה יש לו סה\"כ?"); t5.setDifficulty(1); t5.setWordProblem(true);
            templateRepository.save(t1); templateRepository.save(t2); templateRepository.save(t3); templateRepository.save(t4); templateRepository.save(t5);

            // הכנסת שמות
            Name n1 = new Name(); n1.setName("דני");
            Name n2 = new Name(); n2.setName("נועה");
            Name n3 = new Name(); n3.setName("איתי");
            Name n4 = new Name(); n4.setName("מיכל");
            nameRepository.save(n1); nameRepository.save(n2); nameRepository.save(n3); nameRepository.save(n4);

            // הכנסת חפצים
            GameObject o1 = new GameObject(); o1.setObjectName("ארטיק");
            GameObject o2 = new GameObject(); o2.setObjectName("כדור");
            GameObject o3 = new GameObject(); o3.setObjectName("ספר");
            GameObject o4 = new GameObject(); o4.setObjectName("תפוח");
            gameObjectRepository.save(o1); gameObjectRepository.save(o2); gameObjectRepository.save(o3); gameObjectRepository.save(o4);

            System.out.println("✅ הנתונים הוכנסו בהצלחה והמשחק מוכן!");
        } else {
            System.out.println("✅ הנתונים כבר קיימים, מדלג על הזרקה.");
        }
    }
}