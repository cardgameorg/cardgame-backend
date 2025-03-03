package com.cardgame.message.incoming;

import lombok.Data;

@Data
public class TargetedMessage {
    private String message;
    private String username;
}
