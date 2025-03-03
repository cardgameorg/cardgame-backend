package com.cardgame.filters;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Set;

@Component
public class CustomWebSocketHandler extends TextWebSocketHandler {
    private static final ConcurrentHashMap<String, WebSocketSession> activeUsers = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String username = (String) session.getAttributes().get("username"); // Assuming username is stored in session attributes
        if (username != null) {
            activeUsers.put(username, session);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        activeUsers.values().removeIf(s -> s.equals(session));
    }

    public Set<String> getActiveUsers() {
        return activeUsers.keySet();
    }
}
