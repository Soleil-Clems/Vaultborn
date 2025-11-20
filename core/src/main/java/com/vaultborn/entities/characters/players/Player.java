package com.vaultborn.entities.characters.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.vaultborn.entities.characters.Character;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.managers.AssetManager;
import com.vaultborn.screens.InventoryPlayer;

public abstract class Player extends Character {
    protected InventoryPlayer inv;
    private AssetManager assets;
    protected Sound attackSound;
    private float meleeRange = 80f;
    public boolean isTest = false;


    public Player(Vector2 position, TextureRegion texture, String name) {
        super(position, texture, name);
        this.isPlayerControlled = true;
        attackSound = Gdx.audio.newSound(Gdx.files.internal("sounds/sword.mp3"));
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("sounds/gameover.mp3"));
    }

    //    Pour les tests unitaires
    public Player(Vector2 position, String name) {
        super(position, name);
        this.isPlayerControlled = true;

    }

    public InventoryPlayer getInventory() {
        return inv;
    }

    public void setInventory(InventoryPlayer inv) {
        this.inv = inv;
    }

    public void expGain(int nbExp) {
        inv.addExp(nbExp);
    }

    @Override
    public void attack(Character target) {
        if (!isTest) {
            attackSound.play(0.5f);
        }

        if (target == null) {
            return;
        }

        float distance = position.dst(target.getPosition());

        if (distance <= meleeRange) {
            int dmg = Math.max(1, this.damage - target.getDefense());
            target.takeDamage(dmg);
        }
    }

    @Override
    protected void die() {
        if (isDead) return;

        isDead = true;
        stateTime = 0f;
        velocityY = 0f;
        setAnimation("dead");
        gameOverSound.play(0.5f);

    }


    public void loadAnimations() {

    }
}
