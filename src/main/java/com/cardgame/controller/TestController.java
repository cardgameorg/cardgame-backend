package com.cardgame.controller;

import com.cardgame.message.TestMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Controller
public class TestController {

    @MessageMapping("/teszt")
    @SendTo("/topic/teszteles")
    public TestMessage testMessage(@Payload TestMessage received) {
        System.out.println(HtmlUtils.htmlEscape(received.getMessage()));
        return new TestMessage("hali "+received.getMessage());
    }
}
