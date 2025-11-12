package com.vaultborn.entities.stuff.armor;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
//import com.vaultborn.entities.Entity;
//import com.badlogic.gdx.graphics.Texture;
//import com.vaultborn.entities.stuff.Stuff;

public abstract class Foot extends Armor{

    //sans specialPerk
    public Foot(Vector2 position, TextureRegion texture, String type, String name){
        super(position, texture,"foot",name);
        this.specialPerk = null;
        this.health = 5;
        this.defense = 2;
        this.agility = 10;
        
    }
    //avec specialPerk
    public Foot(Vector2 position, TextureRegion texture, String type,String specialPerk,String name){
        super(position, texture,"foot",name);
        this.specialPerk = specialPerk;
        this.health = 5;
        this.defense = 2;
        this.agility = 10;
    }
}
