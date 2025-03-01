package com.cardgame.response;

import com.cardgame.entity.User;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class AuthResponse {
    private Long id;

    private String username;

    private String profileImg;

    private Integer wins;

    protected AuthResponse (User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.profileImg = user.getProfileImg();
        this.wins = user.getWins();
    }

    public static AuthResponse of(User user) {
        return new AuthResponse(user);
    }
}
