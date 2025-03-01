package com.cardgame.entity;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.cardgame.enums.PackType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "app_packs")
public class Pack {
    @Id
    @GeneratedValue
    private Long id;

    private Long previousId; // reference to the previous version

    private String publicId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // owner of pack

    private PackType packType;

    private String name;

    private String descTitle;

    private String descText;

    @OneToMany(mappedBy = "pack")
    private List<PlayCard> playCards;

    @OneToMany(mappedBy = "pack")
    private List<PromptCard> promptCards;



    private Integer version;

    private boolean isDeleted;

    @PrePersist
    protected void onCreate() {
        if (publicId == null) {
            this.publicId = NanoIdUtils.randomNanoId();
        }
        if (version == null) {
            this.version = 0;
        }
    }


}
