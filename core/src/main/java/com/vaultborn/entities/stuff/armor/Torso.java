package com.vaultborn.entities.stuff.armor;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
//import com.vaultborn.entities.Entity;
//import com.badlogic.gdx.graphics.Texture;
//import com.vaultborn.entities.stuff.Stuff;

public abstract class Torso extends Armor{

    //sans specialPerk
    public Torso(Vector2 position, TextureRegion texture, String type, String name, int lvl){
        super(position, texture,"torso",name);
        this.specialPerk = null;
        this.health = 20*lvl;
        this.defense = 10*lvl;
        this.agility = -3*lvl;

    }

    public Torso(Vector2 position, String type, String name, int lvl){
        super(position, "torso",name);
        this.specialPerk = null;
        this.health = 20*lvl;
        this.defense = 10*lvl;
        this.agility = -3*lvl;

    }

    //avec specialPerk
    public Torso(Vector2 position, TextureRegion texture, String type,String specialPerk,String name, int lvl){
        super(position, texture,"torso",name);
        this.specialPerk = specialPerk;
        this.health = 20*lvl;
        this.defense = 10*lvl;
        this.agility = -3*lvl;
    }
}
