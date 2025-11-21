package com.vaultborn.entities.characters.mobs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.Character;

public class Tengu extends Mob{
    public Tengu(Vector2 position, TextureRegion texture, int lvl) {
        super(position, texture, "Tengu", lvl, 100);
        this.hp = 200*lvl;
        this.damage = 40*lvl;
        this.defense = 55*lvl;
        this.level = lvl;
        this.agility = 10*lvl;
        this.range = 1;

    }

    public Tengu(Vector2 position, int lvl) {
        super(position, "Tengu", lvl, 33);
        this.hp = 100*lvl;
        this.damage = 4*lvl;
        this.defense = 5*lvl;
        this.level = lvl;
        this.agility = 10*lvl;
        this.range = 1;

    }

    @Override
    public void attack(Character target) {
        if (isDead) return;
        int dmg = Math.max(1, this.damage - target.getDefense());
        target.takeDamage(dmg);
    }

    public void loadAnimations() {
        addAnimation("idle", new Texture("tengu/Idle.png"), 6, 0.1f);
        addAnimation("walk", new Texture("tengu/Walk.png"), 8, 0.1f);
        addAnimation("attack", new Texture("tengu/Attack_1.png"), 3, 0.20f);
        addAnimation("attack2", new Texture("tengu/Attack_2.png"), 6, 0.20f);
        addAnimation("attack3", new Texture("tengu/Attack_3.png"), 4, 0.20f);
        addAnimation("jump", new Texture("tengu/Jump.png"), 15, 0.09f);
        addAnimation("protect", new Texture("tengu/Idle_2.png"), 5, 2f);
        addAnimation("dead", new Texture("tengu/Dead.png"), 6, 0.15f);
        addAnimation("hurt", new Texture("tengu/Hurt.png"), 3, 0.15f);

    }
}
