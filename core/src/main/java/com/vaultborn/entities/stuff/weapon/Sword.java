package com.vaultborn.entities.stuff.weapon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
//import com.vaultborn.entities.Entity;

public class Sword extends Weapon{
    public Sword(Vector2 position, TextureRegion texture) {
        super(position, texture, "Sword");
        this.range = 5;
        this.damage = 100;

    }

    public Sword(Vector2 position, TextureRegion texture,String specialPerk) {
        super(position, texture, "Sword",specialPerk);
        this.range = 5;
        this.damage = 10;

    }

    public void loadAnimations() {
        addAnimation("static", new Texture("objects/weapons/sword.png"), 1, 0.1f);
    }




}
