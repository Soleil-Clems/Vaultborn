package com.vaultborn.entities.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.Entity;
import com.badlogic.gdx.graphics.Texture;
import java.util.HashMap;
import java.util.Map;


public abstract class Character extends Entity {

    protected String name;
    protected int hp;
    protected int defense;
    protected int damage;
    protected int level;
    protected int agility;
    protected int range;

    protected TextureRegion portrait; // Image principale
    protected Map<String, Animation<TextureRegion>> animations = new HashMap<>();
    protected float stateTime = 0f;
    protected String currentAnimation = "idle";

    public Character(Vector2 position, TextureRegion texture, String name) {
        super(position, texture);
        this.name = name;
        this.level = 1;
        this.portrait = texture;
    }

    public abstract void attack(Character target);


    public String getName() { return name; }
    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }
    public int getDefense() { return defense; }
    public void setDefense(int defense) { this.defense = defense; }
    public int getDamage() { return damage; }
    public void setDamage(int damage) { this.damage = damage; }
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
    public int getAgility() { return agility; }
    public void setAgility(int agility) { this.agility = agility; }
    public int getRange() { return range; }
    public void setRange(int range) { this.range = range; }


    /**
     * Ajoute une animation au personnage
     */
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

    /**
     * Définit quelle animation jouer
     */
    public void setAnimation(String key) {
        if (animations.containsKey(key)) {
            currentAnimation = key;
            stateTime = 0f;
        }
    }

    /**
     * Renvoie l’animation demandée
     */
    public Animation<TextureRegion> getAnimation(String key) {
        return animations.get(key);
    }


    @Override
    public void render(SpriteBatch batch) {
        Animation<TextureRegion> anim = animations.get(currentAnimation);
        if (anim != null) {
            TextureRegion frame = anim.getKeyFrame(stateTime, true);
            batch.draw(frame, position.x, position.y);
        } else if (portrait != null) {
            batch.draw(portrait, position.x, position.y);
        }
    }
}
