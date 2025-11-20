package com.vaultborn.entities.stuff;


import com.vaultborn.entities.characters.Character;
import com.vaultborn.entities.characters.players.Player;

public interface Stuff {
    String getType();
    String getName();
    int getDurability();
    String getSpecialPerk();
    boolean pickUp(Player character);

}
