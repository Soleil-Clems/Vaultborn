package com.vaultborn.entities.characters.players;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.Character;


public class Warrior extends Player {
    public Warrior(Vector2 position, TextureRegion texture) {
        super(position, texture, "Lancelot");
        this.maxHp = 100;
        this.hp = this.maxHp;
        this.damage = 20;
        this.defense = 8;
        this.level = 1;
        this.agility = 2;
        this.range = 1;


    }

    @Override
    public void attack(Character character) {
        super.attack(character);
    }
    public void loadAnimations() {
        addAnimation("idle", new Texture("warrior/Idle.png"), 5, 0.1f);
        addAnimation("walk", new Texture("warrior/Walk.png"), 9, 0.1f);
        addAnimation("attack", new Texture("warrior/Attack_1.png"), 4, 0.15f);
        addAnimation("attack2", new Texture("warrior/Attack_2.png"), 5, 0.10f);
        addAnimation("attack3", new Texture("warrior/Attack_3.png"), 4, 0.15f);
        addAnimation("run", new Texture("warrior/Run.png"), 8, 0.08f);
        addAnimation("jump", new Texture("warrior/Jump.png"), 7, 0.09f);
        addAnimation("protect", new Texture("warrior/Protect.png"), 2, 2f);
        addAnimation("dead", new Texture("warrior/Dead.png"), 6, 0.15f);
        addAnimation("hurt", new Texture("warrior/Hurt.png"), 2, 0.09f);
    }

    public void dispose() {
        if (attackSound != null) attackSound.dispose();
    }


}
