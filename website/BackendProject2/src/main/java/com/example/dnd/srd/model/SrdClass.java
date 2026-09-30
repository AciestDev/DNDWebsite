package com.example.dnd.srd.model;

import com.example.dnd.srd.model.enums.ClassesEnum;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "classes", schema = "srd_schema")
public class SrdClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private ClassesEnum name;

    private int subclassUnlockLevel = 3;

    @OneToMany(mappedBy = "srdClass", cascade = CascadeType.ALL)
    private List<SrdSubclass> subclasses = new ArrayList<>();

    private int hitDie;
}
