package com.vaultborn.entities.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.vaultborn.world.World;

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
    public boolean facingRight = true;
    private float speed = 200f;
    protected World world;
    // Dimensions du personnage pour les collisions
    protected float characterWidth = 32f;
    protected float characterHeight = 48f;


    protected TextureRegion portrait;
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

    // Getters et setters...
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

    public void setAnimation(String key) {
        if (animations.containsKey(key) && !currentAnimation.equals(key)) {
            currentAnimation = key;
            stateTime = 0f;
        }
    }

    public Animation<TextureRegion> getAnimation(String key) {
        return animations.get(key);
    }



//public void update(float delta) {
//    float moveX = 0;
//    float moveY = 0;
//
//    if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
//        moveX -= 1f;
//        facingRight = false;
//    }
//    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//        moveX += 1f;
//        facingRight = true;
//    }
//    if (Gdx.input.isKeyPressed(Input.Keys.UP))    moveY += 1;
//    if (Gdx.input.isKeyPressed(Input.Keys.DOWN))  moveY -= 1;
//
//
//    if (moveX != 0 || moveY != 0) {
//        Vector2 move = new Vector2(moveX, moveY).nor().scl(speed * delta);
//        position.add(move);
//        bounds.setPosition(position);
//
//        if (facingRight) {
//            portrait.flip(false, true);
//        }
//
//
//        setAnimation("walk");
//    } else {
//        setAnimation("idle");
//    }
//
//    stateTime += delta;
//}


    public void update(float delta) {
        float moveX = 0;
        float moveY = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveX -= 1f;
            facingRight = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveX += 1f;
            facingRight = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP))    moveY += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))  moveY -= 1;

        if (moveX != 0 || moveY != 0) {
            Vector2 move = new Vector2(moveX, moveY).nor().scl(speed * delta);

            // Vérifier les collisions avant de déplacer
            Vector2 newPosition = new Vector2(position).add(move);

            if (world != null && !isColliding(newPosition)) {
                position.set(newPosition);
                bounds.setPosition(position);
            } else if (world != null) {
                // Essayer de glisser le long des murs
                // Tester le mouvement X seul
                Vector2 moveX_only = new Vector2(position.x + move.x, position.y);
                if (!isColliding(moveX_only)) {
                    position.x = moveX_only.x;
                }

                // Tester le mouvement Y seul
                Vector2 moveY_only = new Vector2(position.x, position.y + move.y);
                if (!isColliding(moveY_only)) {
                    position.y = moveY_only.y;
                }

                bounds.setPosition(position);
            }

            setAnimation("walk");
        } else {
            setAnimation("idle");
        }

        stateTime += delta;
    }

    // Vérifie si le personnage entre en collision aux coins de sa hitbox
    private boolean isColliding(Vector2 pos) {
        if (world == null) return false;

        // Vérifier les 4 coins du personnage
        boolean topLeft = world.isCellBlocked(pos.x, pos.y + characterHeight);
        boolean topRight = world.isCellBlocked(pos.x + characterWidth, pos.y + characterHeight);
        boolean bottomLeft = world.isCellBlocked(pos.x, pos.y);
        boolean bottomRight = world.isCellBlocked(pos.x + characterWidth, pos.y);

        return topLeft || topRight || bottomLeft || bottomRight;
    }

    @Override
    public void render(SpriteBatch batch) {
        Animation<TextureRegion> anim = animations.get(currentAnimation);
        if (anim != null) {
            TextureRegion frame = anim.getKeyFrame(stateTime, true);

            if ((facingRight && frame.isFlipX()) || (!facingRight && !frame.isFlipX())) {
                frame.flip(true, false);
            }

            batch.draw(frame, position.x, position.y);
        } else if (portrait != null) {
            batch.draw(portrait, position.x, position.y);
        }
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
