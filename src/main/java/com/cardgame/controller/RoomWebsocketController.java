package com.cardgame.controller;

import com.cardgame.entity.database.User;
import com.cardgame.entity.inMemory.Room;
import com.cardgame.service.GameService;
import com.cardgame.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class RoomWebsocketController {
    private final SimpMessagingTemplate messagingTemplate;
    private final GameService gameService;

    @MessageMapping("/createRoom")
    public void createRoom() {
        User user = UserUtils.getCurrentUser();
        Room room = gameService.createGame(user);
        messagingTemplate.convertAndSendToUser(
                String.valueOf(user.getId()), "queue/messages","room created with id: " + room.getId()
        );
    }
}
