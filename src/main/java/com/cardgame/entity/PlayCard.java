package com.cardgame.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "app_play_cards")
public class PlayCard {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String text;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pack_id")
    private Pack pack;


    @NotNull
    private String publicPackId; // the public id of the pack

    @NotNull
    private Integer versionAdded;

    private Integer versionRemoved;

    @PrePersist
    protected void onCreate() {
        if (versionAdded == null) {
            this.versionAdded = pack.getVersion();
        }
    }



}
