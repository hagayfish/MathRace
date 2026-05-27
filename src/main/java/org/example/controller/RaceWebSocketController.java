package org.example.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class RaceWebSocketController {

    // כששחקן ישלח הודעה ל-app/move/, היא תגיע לכאן
    @MessageMapping("/move")
    @SendTo("/topic/race-updates") // והשרת ישדר אותה אוטומטית לכל מי שמקשיב לערוץ הזה
    public PlayerMovementDTO broadcastMovement(PlayerMovementDTO movement) {
        // השרת פשוט לוקח את עדכון התנועה ומעביר אותו הלאה בזמן אמת
        return movement;
    }

    // מחלקה פנימית קטנה (DTO) שמייצגת את הודעת התנועה
    public static class PlayerMovementDTO {
        private String playerName;
        private int progress;

        // Getters and Setters
        public String getPlayerName() { return playerName; }
        public void setPlayerName(String playerName) { this.playerName = playerName; }
        public int getProgress() { return progress; }
        public void setProgress(int progress) { this.progress = progress; }
    }
}
