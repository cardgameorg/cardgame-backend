package com.cardgame.message;

import lombok.Data;

@Data
public class SimpleMessage {
    private String message;
    public SimpleMessage(String message) {
        this.message = message;
    }
}
