package com.vaultborn.entities.stuff;


import com.vaultborn.entities.characters.Character;

public interface Stuff {
    String getType();
    String getName();
    int getDurability();
    String getSpecialPerk();
    void pickUp(Character character);

}
