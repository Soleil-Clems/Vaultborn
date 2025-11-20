package com.vaultborn.entities.stuff.armor;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
//import com.vaultborn.entities.Entity;
//import com.badlogic.gdx.graphics.Texture;
//import com.vaultborn.entities.stuff.Stuff;

public class Pants extends Leg{
    //sans specialPerk
    public Pants(Vector2 position, TextureRegion texture, String name, int lvl){
        super(position, texture,"pants",name,lvl);
        
    }
    //avec specialPerk
    public Pants(Vector2 position, TextureRegion texture,String specialPerk,String name, int lvl){
        super(position, texture,"pants",specialPerk,name,lvl);
    }
    
}
