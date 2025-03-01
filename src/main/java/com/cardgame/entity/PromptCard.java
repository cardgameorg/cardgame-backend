package com.cardgame.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "app_prompt_cards")
public class PromptCard {
    @Id
    @GeneratedValue
    private Long id;


    private String text1;

    //PROMPT SPACE, NEM KELL KÜLÖN FIELD

    private String text2;

    private boolean extraPromptSpace; // plusz prompt space :)

    private String text3;


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