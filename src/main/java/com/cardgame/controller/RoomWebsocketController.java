package com.cardgame.controller;

import com.cardgame.entity.database.User;
import com.cardgame.entity.inMemory.Room;
import com.cardgame.message.ChatMessage;
import com.cardgame.message.SimpleMessage;
import com.cardgame.service.GameService;
import com.cardgame.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class RoomWebsocketController {
    private final SimpMessagingTemplate messagingTemplate;
    private final GameService gameService;

    @MessageMapping("/createRoom")
    public void createRoom(Principal principal) {
        User user = null;
        if (principal instanceof UsernamePasswordAuthenticationToken auth) {
            user = (User) auth.getPrincipal();
        }
        Room room = gameService.createGame(user);
        assert user != null;
        messagingTemplate.convertAndSendToUser(
                user.getUsername(), "/queue/messages",new SimpleMessage("Room created with ID: "+ room.getId())
        );
    }

    @MessageMapping("/chat/user/{username}")
    public void sendMessageToUser(@DestinationVariable String username, Principal principal, @Payload SimpleMessage message) {
        User user = null;
        if (principal instanceof UsernamePasswordAuthenticationToken auth) {
            user = (User) auth.getPrincipal();
        }
        assert user != null;
        messagingTemplate.convertAndSendToUser(
               username, "/queue/messages",new ChatMessage(user,message.getMessage())
        );
    }

    @MessageMapping("/chat")  // Correct the path mapping to include the groupId
    @SendTo("/topic/chat")  // Correctly use groupId in SendTo
    public ChatMessage testMessage(@Payload String message, Principal principal) {
        User user = null;
        if (principal instanceof UsernamePasswordAuthenticationToken auth) {
            user = (User) auth.getPrincipal();
        }
        System.out.println(user.getUsername());
        return new ChatMessage(user,message);
    }
}
