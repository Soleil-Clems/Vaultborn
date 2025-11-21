package com.vaultborn.entities.stuff.armor;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
//import com.vaultborn.entities.Entity;
//import com.badlogic.gdx.graphics.Texture;
//import com.vaultborn.entities.stuff.Stuff;

public abstract class Foot extends Armor{

    //sans specialPerk
    public Foot(Vector2 position, TextureRegion texture, String type, String name, int lvl){
        super(position, texture,"foot",name);
        this.specialPerk = null;
        this.health = 5*lvl;
        this.defense = 2*lvl;
        this.agility = 10*lvl;

    }

    public Foot(Vector2 position,  String type, String name, int lvl){
        super(position, "foot",name);
        this.specialPerk = null;
        this.health = 5*lvl;
        this.defense = 2*lvl;
        this.agility = 10*lvl;

    }

    //avec specialPerk
    public Foot(Vector2 position, TextureRegion texture, String type,String specialPerk,String name, int lvl){
        super(position, texture,"foot",name);
        this.specialPerk = specialPerk;
        this.health = 5*lvl;
        this.defense = 2*lvl;
        this.agility = 10*lvl;
    }
}
