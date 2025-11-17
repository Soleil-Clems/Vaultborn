package com.vaultborn.entities.stuff.weapon;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.Entity;
//import com.badlogic.gdx.graphics.Texture;
import com.vaultborn.entities.characters.Character;
import com.vaultborn.entities.characters.players.Player;
import com.vaultborn.entities.stuff.GameObject;
import com.vaultborn.entities.stuff.Stuff;
import com.vaultborn.screens.InventoryPlayer;
import com.vaultborn.world.BaseWorld;


import java.util.HashMap;
import java.util.Map;


public abstract class Weapon extends GameObject implements Stuff{
    protected String type;
    protected String name;
    protected int durability;
    protected String specialPerk;
    protected int damage;
    protected int range;
    protected Character character;
    public BaseWorld world;
    protected Map<String, Animation<TextureRegion>> animations = new HashMap<>();


    //weapon sans specialPerk
    public Weapon(Vector2 position, TextureRegion texture, String type){
        super(position, texture);
        this.type = type;
        this.name = type;
        this.durability = 100;
        this.specialPerk = null;

    }
    //weapon avec specialPerk
    public Weapon(Vector2 position, TextureRegion texture, String type,String specialPerk){
        super(position, texture);
        this.type = type;
        this.name = type;
        this.durability = 100;
        this.specialPerk = specialPerk;
    }
    //getter
    @Override
    public String getName(){return this.name;}
    @Override
    public String getType(){return this.type;}
    @Override
    public int getDurability(){return this.durability;}
    @Override
    public String getSpecialPerk(){return this.specialPerk;}

    public int getDamage(){return this.damage;}
    public int getRange(){return this.range;}

    //setter
    public void setDurability(){this.durability -=1;}
    public void setDamage(int damage){this.damage =damage;}
    public void setRange(int range){this.range =range;}

    //les différente special perk possible pour les armes
    public void Bonus(String specialPerk){
        if (specialPerk.equals(null)){
            return;
        }
        else{
            if (specialPerk.equals("fire")){
                // réduit les dagat de 10% mais faire un tic de 5% des dégat/sec pour 5 sec
                return;
            }
            else if (specialPerk.equals("unbreakable")){
                // toujours this.durability = 100;
                return;
            }
            if (specialPerk.equals("moreRange")){

                // augmente la range de 0.2 this.range = Math.round(this.range*1.2f);
                return;
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void pickUp(Player player) {
        InventoryPlayer inv = player.getInventory();
        inv.addInventory(this);
    }

    public void setWorld(BaseWorld world) {
        this.world = world;
    }

    protected void addAnimation(String key, Texture spriteSheet, int frameCount, float frameDuration) {
        int frameWidth = spriteSheet.getWidth() / frameCount;
        int frameHeight = spriteSheet.getHeight();
        TextureRegion[][] tmp = TextureRegion.split(spriteSheet, frameWidth, frameHeight);

        TextureRegion[] frames = new TextureRegion[frameCount];
        for (int i = 0; i < frameCount; i++) {
            frames[i] = tmp[0][i];
        }

        Animation<TextureRegion> anim = new Animation<>(frameDuration, frames);
        animations.put(key, anim);
    }

}
