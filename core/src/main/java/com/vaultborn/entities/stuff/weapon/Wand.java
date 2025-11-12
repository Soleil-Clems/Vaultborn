package com.vaultborn.entities.stuff.weapon;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
//import com.vaultborn.entities.Entity;

public class Wand extends Weapon{
    public Wand(Vector2 position, TextureRegion texture) {
        super(position, texture, "wand");
        this.range = 10;

        

    }
    public Wand(Vector2 position, TextureRegion texture,String specialPerk) {
        super(position, texture, "wand",specialPerk);
        this.range = 10;


    }

    
    

}
