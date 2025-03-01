package com.cardgame.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String profileImg;

    private Integer wins;

    @PrePersist
    protected void onCreate() {
        if (profileImg == null) {
            this.profileImg = "https://www.premiumpiacszeged.hu/img/38802/BAN0001/BAN0001.jpg";
        }
        if (wins == null) {
            this.wins = 0;
        }
    }
}
