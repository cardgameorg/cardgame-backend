package com.cardgame.message;

import com.cardgame.entity.database.User;
import com.cardgame.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatMessage {
    private UserResponse user;
    private String message;

    public ChatMessage(User user, String message) {
        this.user = UserResponse.of(user);
        this.message = message;
    }
}
