package com.vaultborn.entities.characters.players;

import com.badlogic.gdx.math.Rectangle;
import com.vaultborn.entities.characters.Character;
import com.vaultborn.screens.InventoryPlayer;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public abstract  class Player extends Character {
    public Player(Vector2 position, TextureRegion texture, String name) {
        super(position, texture, name);
        this.isPlayerControlled = true;

    }
    protected InventoryPlayer inv;
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
