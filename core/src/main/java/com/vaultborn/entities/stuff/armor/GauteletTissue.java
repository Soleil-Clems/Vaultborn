package com.vaultborn.entities.stuff.armor;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
//import com.vaultborn.entities.Entity;
//import com.badlogic.gdx.graphics.Texture;
//import com.vaultborn.entities.stuff.Stuff;

public class GauteletTissue extends Arm{
    //sans specialPerk
    public GauteletTissue(Vector2 position, TextureRegion texture, String type, String name){
        super(position, texture,"gauteletTissue",name);
        this.stamina = 30;
        
    }
    //avec specialPerk
    public GauteletTissue(Vector2 position, TextureRegion texture, String type,String specialPerk,String name){
        super(position, texture,"gauteletTissue",specialPerk,name);
        this.stamina = 30;
    }
    
}
