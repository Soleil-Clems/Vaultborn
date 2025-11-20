package com.vaultborn.entities.characters.players;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.Character;
import com.vaultborn.entities.projectiles.Projectile;


public class Satyr extends Mage {

    public Satyr(Vector2 position, TextureRegion texture) {
        super(position, texture, "Merline");
        this.maxHp = 100;
        this.hp = this.maxHp;
        this.damage = 20;
        this.defense = 8;
        this.level = 1;
        this.agility = 2;
        this.range = 10;
        this.speed = 400;

    }

    public Satyr(Vector2 position) {
        super(position, "Merline");
        this.maxHp = 100;
        this.hp = this.maxHp;
        this.damage = 20;
        this.defense = 8;
        this.level = 1;
        this.agility = 2;
        this.range = 10;
        this.speed = 400;

    }

    @Override
    public void attack(Character target) {
      super.attack(target);
    }


    public void loadAnimations() {
        addAnimation("idle", new Texture("satyr/Idle.png"), 7, 0.1f);
        addAnimation("walk", new Texture("satyr/Walk.png"), 12, 0.04f);
        addAnimation("attack", new Texture("satyr/Attack.png"), 8, 0.15f);
        addAnimation("attack2", new Texture("satyr/Attack.png"), 8, 0.10f);
        addAnimation("attack3", new Texture("satyr/Attack.png"), 8, 0.15f);
        addAnimation("run", new Texture("satyr/Walk.png"), 12, 0.08f);
        addAnimation("jump", new Texture("satyr/Walk.png"), 12, 0.09f);
        addAnimation("protect", new Texture("satyr/Attack.png"), 8, 2f);
        addAnimation("dead", new Texture("satyr/Dead.png"), 4, 0.15f);
        addAnimation("hurt", new Texture("satyr/Hurt.png"), 4, 0.09f);
        addAnimation("charge", new Texture("satyr/Charge.png"), 8, 0.09f);
    }

}
