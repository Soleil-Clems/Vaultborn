package com.vaultborn.entities.stuff.armor;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
//import com.vaultborn.entities.Entity;
//import com.badlogic.gdx.graphics.Texture;
//import com.vaultborn.entities.stuff.Stuff;

public class Helmet extends Head{
    //sans specialPerk
    public Helmet(Vector2 position, TextureRegion texture, String type, String name){
        super(position, texture,"helmet",name);
        
    }
    //avec specialPerk
    public Helmet(Vector2 position, TextureRegion texture, String type,String specialPerk,String name){
        super(position, texture,"helmet",specialPerk,name);
    }
    
}
