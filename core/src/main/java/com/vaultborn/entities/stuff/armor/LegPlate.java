package com.vaultborn.entities.stuff.armor;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
//import com.vaultborn.entities.Entity;
//import com.badlogic.gdx.graphics.Texture;
//import com.vaultborn.entities.stuff.Stuff;

public class LegPlate extends Leg{
    //sans specialPerk
    public LegPlate(Vector2 position, TextureRegion texture, String name, int lvl){
        super(position, texture,"leg plate",name,lvl);

    }

    public LegPlate(Vector2 position,  String name, int lvl){
        super(position, "leg plate",name,lvl);

    }

    //avec specialPerk
    public LegPlate(Vector2 position, TextureRegion texture,String specialPerk,String name, int lvl){
        super(position, texture,"leg plate",specialPerk,name,lvl);
    }

}
