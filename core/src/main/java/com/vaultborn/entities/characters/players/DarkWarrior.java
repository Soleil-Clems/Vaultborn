package com.vaultborn.entities.characters.players;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.Character;


public class DarkWarrior extends Player {
    private float meleeRange = 80f;
    public DarkWarrior(Vector2 position, TextureRegion texture) {
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
    public void attack(Character target) {
        if (target == null) {
            return;
        }

        float distance = position.dst(target.getPosition());

        if (distance <= meleeRange) {
            int dmg = Math.max(1, this.damage - target.getDefense());
            target.takeDamage(dmg);
        }
    }

    public void loadAnimations() {
        String assets = "darkwarrior/";
        addAnimation("idle", new Texture(assets+"Idle.png"), 6, 0.1f);
        addAnimation("walk", new Texture(assets+"Walk.png"), 9, 0.1f);
        addAnimation("attack", new Texture(assets+"Attack_1.png"), 4, 0.15f);
        addAnimation("attack2", new Texture(assets+"Attack_2.png"), 5, 0.10f);
        addAnimation("attack3", new Texture(assets+"Attack_3.png"), 4, 0.15f);
        addAnimation("run", new Texture(assets+"Run.png"), 8, 0.08f);
        addAnimation("jump", new Texture(assets+"Jump.png"), 9, 0.09f);
        addAnimation("protect", new Texture(assets+"Protect.png"), 2, 2f);
        addAnimation("dead", new Texture(assets+"Dead.png"), 6, 0.15f);
        addAnimation("hurt", new Texture(assets+"Hurt.png"), 3, 0.09f);
    }

}
