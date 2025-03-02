package com.cardgame.entity.inMemory;

import com.cardgame.entity.database.User;
import jakarta.persistence.PrePersist;
import lombok.Data;

@Data
public class Player {
    private String username;
    private String userId;
    private String profileImg;
    private int points;

    public Player(User user) {
        this.userId = user.getPublicId();
        this.username = user.getUsername();
        this.profileImg = user.getProfileImg();
        this.points = 0;
    }
}
