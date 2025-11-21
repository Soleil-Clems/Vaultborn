package com.vaultborn.entities.stuff.armor;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
//import com.vaultborn.entities.Entity;
//import com.badlogic.gdx.graphics.Texture;
//import com.vaultborn.entities.stuff.Stuff;

public abstract class Leg extends Armor{

    //sans specialPerk
    public Leg(Vector2 position, TextureRegion texture, String type, String name, int lvl){
        super(position, texture,"leg",name);
        this.specialPerk = null;
        this.health = 15*lvl;
        this.defense = 6*lvl;
        this.agility = -2*lvl;

    }

    public Leg(Vector2 position, String type, String name, int lvl){
        super(position,"leg",name);
        this.specialPerk = null;
        this.health = 15*lvl;
        this.defense = 6*lvl;
        this.agility = -2*lvl;

    }

    //avec specialPerk
    public Leg(Vector2 position, TextureRegion texture, String type,String specialPerk,String name, int lvl){
        super(position, texture,"leg",name);
        this.specialPerk = specialPerk;
        this.health = 15*lvl;
        this.defense = 6*lvl;
        this.agility = -2*lvl;
    }
}
