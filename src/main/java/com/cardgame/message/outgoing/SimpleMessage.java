package com.cardgame.message.outgoing;

import lombok.Data;

@Data
public class SimpleMessage {
    private String message;
    public SimpleMessage(String message) {
        this.message = message;
    }
}
