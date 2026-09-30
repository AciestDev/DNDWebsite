package com.example.dnd.character.model;

import com.example.dnd.srd.model.*;
import jakarta.persistence.*;

@Entity
@Table(name = "characters", schema = "character_schema")
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- 1. CORE IDENTITY ---
    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private SrdClass characterClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "race_id")
    private SrdRace race;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "background_id")
    private SrdBackground background;



    public Long getId() {
        return id;
    }

}
