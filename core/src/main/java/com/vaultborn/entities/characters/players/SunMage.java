package com.vaultborn.entities.characters.players;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.Character;


public class SunMage extends Mage {

    public SunMage(Vector2 position, TextureRegion texture) {
        super(position, texture, "Monet");
        this.maxHp = 100;
        this.hp = this.maxHp;
        this.damage = 30;
        this.defense = 20;
        this.level = 1;
        this.agility = 40;
        this.range = 20;
    }

    public SunMage(Vector2 position) {
        super(position, "Monet");
        this.maxHp = 100;
        this.hp = this.maxHp;
        this.damage = 30;
        this.defense = 20;
        this.level = 1;
        this.agility = 40;
        this.range = 20;
    }

    @Override
    public void attack(Character target) {
        super.attack(target);
    }

    @Override
    protected void handleInput(float delta) {
        attackSound = Gdx.audio.newSound(Gdx.files.internal("sounds/power.mp3"));

        this.charge = "sunmage/Fire_1.png";
        this.chargeFrameCount = 14;
        float moveX = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveX -= 1f;
            facingRight = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveX += 1f;
            facingRight = true;
        }


        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            attack = "attack";
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            attack = "attack2";
            this.charge = "sunmage/Fire_2.png";
            this.chargeFrameCount = 11;
            attackSound = Gdx.audio.newSound(Gdx.files.internal("sounds/power2.mp3"));

        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) attack = "attack3";


        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) isProtected = true;

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && onGround) {
            velocityY = jumpSpeed + agility;
            onGround = false;
        }

        moveAndCollide(moveX * speed * delta, velocityY * delta);
    }

    public void loadAnimations() {
        String asset = "sunmage/";
        addAnimation("idle", new Texture(asset + "Idle.png"), 8, 0.1f);
        addAnimation("walk", new Texture(asset + "Walk.png"), 8, 0.05f);
        addAnimation("attack", new Texture(asset + "Attack_1.png"), 10, 0.05f);
        addAnimation("attack2", new Texture(asset + "Attack_2.png"), 10, 0.05f);
        addAnimation("attack3", new Texture(asset + "Attack_3.png"), 7, 0.05f);
        addAnimation("run", new Texture(asset + "Run.png"), 8, 0.01f);
        addAnimation("jump", new Texture(asset + "Jump.png"), 10, 0.09f);
        addAnimation("protect", new Texture(asset + "Idle_2.png"), 6, 2f);
        addAnimation("dead", new Texture(asset + "Dead.png"), 10, 0.10f);
        addAnimation("hurt", new Texture(asset + "Hurt.png"), 2, 0.09f);
    }

}
