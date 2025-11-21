package com.vaultborn.entities.stuff.armor;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
//import com.vaultborn.entities.Entity;
//import com.badlogic.gdx.graphics.Texture;
//import com.vaultborn.entities.stuff.Stuff;

public class GauteletPlate extends Arm{
    //sans specialPerk
    public GauteletPlate(Vector2 position, TextureRegion texture, String name, int lvl){
        super(position, texture,"gauteletPlate",name,lvl);
        this.stamina = 30*lvl;

    }

    public GauteletPlate(Vector2 position,  String name, int lvl){
        super(position,"gauteletPlate",name,lvl);
        this.stamina = 30*lvl;

    }

    //avec specialPerk
    public GauteletPlate(Vector2 position, TextureRegion texture,String specialPerk,String name, int lvl){
        super(position, texture,"gauteletPlate",specialPerk,name,lvl);
        this.stamina = 30*lvl;
    }

}
