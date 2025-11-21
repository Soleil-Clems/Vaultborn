package com.vaultborn.entities.stuff.armor;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
//import com.vaultborn.entities.Entity;
//import com.badlogic.gdx.graphics.Texture;
//import com.vaultborn.entities.stuff.Stuff;

public abstract class Head extends Armor{

    //sans specialPerk
    public Head(Vector2 position, TextureRegion texture, String type, String name, int lvl){
        super(position, texture,"head",name);
        this.specialPerk = null;
        this.health = 10*lvl;
        this.defense = 6*lvl;

    }

    public Head(Vector2 position,  String type, String name, int lvl){
        super(position,"head",name);
        this.specialPerk = null;
        this.health = 10*lvl;
        this.defense = 6*lvl;

    }

    //avec specialPerk
    public Head(Vector2 position, TextureRegion texture, String type,String specialPerk,String name, int lvl){
        super(position, texture,"head",name);
        this.specialPerk = specialPerk;
        this.health = 10*lvl;
        this.defense = 6*lvl;
    }
}
