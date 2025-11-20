package com.vaultborn.entities.stuff.armor;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
//import com.vaultborn.entities.Entity;
//import com.badlogic.gdx.graphics.Texture;
//import com.vaultborn.entities.stuff.Stuff;

public class GauteletTissue extends Arm{
    //sans specialPerk
    public GauteletTissue(Vector2 position, TextureRegion texture, String name, int lvl){
        super(position, texture,"gauteletTissue",name,lvl);
        this.stamina = 30*lvl;
        
    }
    //avec specialPerk
    public GauteletTissue(Vector2 position, TextureRegion texture,String specialPerk,String name, int lvl){
        super(position, texture,"gauteletTissue",specialPerk,name,lvl);
        this.stamina = 30*lvl;
    }
    
}
