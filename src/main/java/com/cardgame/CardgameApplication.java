package com.cardgame;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.ReactiveRedisTemplate;

@SpringBootApplication
public class CardgameApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardgameApplication.class, args);
    }


}
