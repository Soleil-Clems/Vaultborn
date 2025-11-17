package com.vaultborn.entities.characters.players;

import com.badlogic.gdx.math.Rectangle;
import com.vaultborn.entities.characters.Character;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.screens.InventoryPlayer;

public abstract  class Player extends Character {
    protected InventoryPlayer inv;
    public Player(Vector2 position, TextureRegion texture, String name) {
        super(position, texture, name);
        this.isPlayerControlled = true;

    }
    public InventoryPlayer getInventory(){
        return inv;
    }
    public void setInventory(InventoryPlayer inv){
        this.inv = inv;
    }
    public void expGain(int nbExp){
        inv.addExp(nbExp);
    }

}
