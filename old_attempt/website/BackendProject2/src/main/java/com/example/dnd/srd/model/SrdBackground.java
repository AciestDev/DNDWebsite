package com.example.dnd.srd.model;

import jakarta.persistence.*;

@Entity
@Table(name = "backgrounds", schema = "srd_schema")
public class SrdBackground {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
