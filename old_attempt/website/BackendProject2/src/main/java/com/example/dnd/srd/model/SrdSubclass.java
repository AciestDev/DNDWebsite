package com.example.dnd.srd.model;

import jakarta.persistence.*;
import com.example.dnd.srd.model.enums.SubclassesEnum;

@Entity
@Table(name = "subclasses", schema = "srd_schema")
public class SrdSubclass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private SubclassesEnum name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private SrdClass srdClass;
}
