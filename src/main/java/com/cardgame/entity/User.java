package com.cardgame.entity;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.cardgame.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "app_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    public Long id;

    @Column(unique = true)
    private String publicId;

    @Column(unique = true)
    private String username;

    private String password;

    private String profileImg;

    private Integer wins;

    private Boolean isDeleted;

    private UserRole role;

    @OneToMany(mappedBy = "user")
    private List<Pack> packs;

    @PrePersist
    protected void onCreate() {
        if (publicId == null) {
            this.publicId = NanoIdUtils.randomNanoId();
        }
        if (profileImg == null) {
            this.profileImg = "https://www.premiumpiacszeged.hu/img/38802/BAN0001/BAN0001.jpg";
        }
        if (wins == null) {
            this.wins = 0;
        }
        if (isDeleted == null) {
            this.isDeleted = false;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
