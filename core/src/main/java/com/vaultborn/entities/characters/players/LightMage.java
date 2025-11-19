package com.vaultborn.entities.characters.players;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.Character;


public class LightMage extends Mage{

    public LightMage(Vector2 position, TextureRegion texture) {
        super(position, texture, "Lisa");
        this.maxHp = 100;
        this.hp = this.maxHp;
        this.damage = 20;
        this.defense = 8;
        this.level = 1;
        this.agility = 2;
        this.range = 10;
    }

    @Override
    public void attack(Character target) {
       super.attack(target);
    }

    @Override
    protected void handleInput(float delta) {
        attackSound = Gdx.audio.newSound(Gdx.files.internal("sounds/power.mp3"));

        this.charge = "lightmage/Charge.png";
        this.chargeFrameCount = 6;
        float moveX = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("left")))) {
            moveX -= 1f;
            facingRight = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("right")))) {
            moveX += 1f;
            facingRight = true;
        }


        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("attack")))){
            attack = "attack";
        }
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("attack2")))) attack = "attack2";
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("attack3")))) attack = "attack3";
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("attack4")))){
            attack = "attack4";
            attackSound = Gdx.audio.newSound(Gdx.files.internal("sounds/power2.mp3"));

        }

        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) isProtected = true;

        if (Gdx.input.isKeyJustPressed(Input.Keys.valueOf(inputList.get("jump"))) && onGround) {
            velocityY = jumpSpeed + agility;
            onGround = false;
        }

        moveAndCollide(moveX * speed * delta, velocityY * delta);
    }

    public void loadAnimations() {
        String asset = "lightmage/";
        addAnimation("idle", new Texture(asset+"Idle.png"), 7, 0.1f);
        addAnimation("walk", new Texture(asset+"Walk.png"), 7, 0.05f);
        addAnimation("attack", new Texture(asset+"Attack_1.png"), 10, 0.05f);
        addAnimation("attack2", new Texture(asset+"Attack_2.png"), 4, 0.05f);
        addAnimation("attack3", new Texture(asset+"Light_ball.png"), 7, 0.05f);
        addAnimation("attack4", new Texture(asset+"Light_Charge.png"), 13, 0.05f);
        addAnimation("run", new Texture(asset+"Run.png"), 8, 0.01f);
        addAnimation("jump", new Texture(asset+"Jump.png"), 8, 0.09f);
        addAnimation("protect", new Texture(asset+"Idle.png"), 7, 2f);
        addAnimation("dead", new Texture(asset+"Dead.png"), 5, 0.15f);
        addAnimation("hurt", new Texture(asset+"Hurt.png"), 3, 0.09f);
    }

}
