package com.vaultborn.entities.stuff.armor;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
//import com.vaultborn.entities.Entity;
//import com.badlogic.gdx.graphics.Texture;
//import com.vaultborn.entities.stuff.Stuff;

public class Hat extends Head{
    //sans specialPerk
    public Hat(Vector2 position, TextureRegion texture, String type, String name){
        super(position, texture,"hat",name);
        
    }
    //avec specialPerk
    public Hat(Vector2 position, TextureRegion texture, String type,String specialPerk,String name){
        super(position, texture,"hat",specialPerk,name);
    }
    
}
