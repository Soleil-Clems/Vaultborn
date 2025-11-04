package com.vaultborn.entities.stuff.armor;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
//import com.vaultborn.entities.Entity;
//import com.badlogic.gdx.graphics.Texture;
//import com.vaultborn.entities.stuff.Stuff;

public class Robe extends Torso{
    //sans specialPerk
    public Robe(Vector2 position, TextureRegion texture, String type, String name){
        super(position, texture,"robe",name);
        this.mana = 10;
        
    }
    //avec specialPerk
    public Robe(Vector2 position, TextureRegion texture, String type,String specialPerk,String name){
        super(position, texture,"robe",specialPerk,name);
        this.mana = 10;
    }
    
}
