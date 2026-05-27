package org.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // פותח ערוץ שידור שנקרא topic/ שבו השרת מפיץ עדכונים לכולם
        config.enableSimpleBroker("/topic");
        // נתיב קידומת להודעות שמגיעות מה-React לשרת
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // נקודת החיבור הפיזית של ה-React לשרת (כולל הגנה מפני חסימות דפדפן)
        registry.addEndpoint("/ws-game")
                .setAllowedOrigins("http://localhost:3000")
                .withSockJS();
    }
}