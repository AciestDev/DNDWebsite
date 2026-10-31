package com.example.dnd.character.model.Embeddable;

import jakarta.persistence.Embeddable;

@Embeddable
public class CharacterStats {
    private int level = 1;
    private int currentHp;
    private int maxHp;
    private int tempHp;
    private int armorClass;
    private int speed = 30;

    public CharacterStats() {}

}
