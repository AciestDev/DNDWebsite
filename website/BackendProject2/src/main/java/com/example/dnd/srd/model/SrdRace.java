package com.example.dnd.srd.model;

import com.example.dnd.srd.model.enums.RacesEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "races", schema = "srd_schema")
public class SrdRace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RacesEnum name;
}
