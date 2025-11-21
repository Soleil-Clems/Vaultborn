package com.vaultborn.entities.stuff.armor;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
//import com.vaultborn.entities.Entity;
//import com.badlogic.gdx.graphics.Texture;
//import com.vaultborn.entities.stuff.Stuff;

public class Breastplate extends Torso{
    //sans specialPerk
    public Breastplate(Vector2 position, TextureRegion texture, String name, int lvl){
        super(position, texture,"breastplate",name,lvl);
        this.stamina = 10*lvl;

    }

    public Breastplate(Vector2 position,  String name, int lvl){
        super(position,"breastplate",name,lvl);
        this.stamina = 10*lvl;

    }
    //avec specialPerk
    public Breastplate(Vector2 position, TextureRegion texture,String specialPerk,String name, int lvl){
        super(position, texture,"breastplate",specialPerk,name,lvl);
        this.stamina = 10*lvl;
    }

}
