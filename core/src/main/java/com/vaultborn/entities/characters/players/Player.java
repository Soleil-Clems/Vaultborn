package com.vaultborn.entities.characters.players;

import com.vaultborn.entities.characters.Character;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public abstract  class Player extends Character {
    protected int weapon;
    public Player(Vector2 position, TextureRegion texture, String name) {
        super(position, texture, name);

    }

    public void pickup(int weapon) {
        this.weapon = weapon;
    }

}
