package com.cardgame.entity.inMemory;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.cardgame.entity.database.User;
import jakarta.persistence.PrePersist;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Room {

    private String id;
    private List<Player> players = new ArrayList<>();


    public Room (User user) {
        this.id = NanoIdUtils.randomNanoId();
        this.players.add(new Player(user));
    }
}
