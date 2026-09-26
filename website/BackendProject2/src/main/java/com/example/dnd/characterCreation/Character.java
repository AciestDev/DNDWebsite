package com.example.dnd.characterCreation;

import jakarta.persistence.*;

@Entity
@Table(name="characters")
public class Character {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

}
