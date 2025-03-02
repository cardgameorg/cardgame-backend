package com.cardgame.response;

import com.cardgame.entity.database.User;
import lombok.Data;

@Data
public class UserResponse {
    private Long id;

    private String username;

    private String profileImg;

    private Integer wins;


    protected UserResponse (User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.profileImg = user.getProfileImg();
        this.wins = user.getWins();
    }

    public static UserResponse of(User user) {
        return new UserResponse(user);
    }
}
