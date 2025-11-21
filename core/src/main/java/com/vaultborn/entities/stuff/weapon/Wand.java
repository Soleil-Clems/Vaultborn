package com.vaultborn.entities.stuff.weapon;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
//import com.vaultborn.entities.Entity;

public class Wand extends Weapon{
    public Wand(Vector2 position, TextureRegion texture,int lvl) {
        super(position, texture, "wand");
        this.range = 10;
        this.damage = 10*lvl;


    }
    public Wand(Vector2 position, TextureRegion texture,String specialPerk,int lvl) {
        super(position, texture, "wand",specialPerk);
        this.range = 10;
        this.damage = 10*lvl;
    }




}
