package com.example.dnd.character.model;

import com.example.dnd.auth.User;
import com.example.dnd.character.model.Embeddable.*;
import com.example.dnd.srd.model.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "characters", schema = "character_schema")
public class Character {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- 1. CORE IDENTITY ---
    @Column(nullable = false)
    private String name;

//    @Embedded
//    private CharacterStats stats;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "class_id")
//    private SrdClass characterClass;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "race_id")
//    private SrdRace race;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "background_id")
//    private SrdBackground background;


    public Character() {}
    public Character(User user, String name) {
        this.user = user;
    }

    public String getName() {
        return name;
    }
    public Long getId() {
        return id;
    }
    public User getUser() {
        return user;
    }
    public Long getUserId() {
        return user != null ? user.getId() : null;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
