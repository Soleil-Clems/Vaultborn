package com.vaultborn.entities.characters.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.Character;
import com.vaultborn.world.World;


public class Warrior extends Character {

    private float speed = 200f;
    public boolean facingRight = true;
    private World world;


    public Warrior(Vector2 position, TextureRegion texture) {
        super(position, texture, "Lancelot");
        this.hp = 100;
        this.damage = 10;
        this.defense = 8;
        this.level = 1;
        this.agility = 10;
        this.range = 1;

        // Ajustez selon votre sprite
//        this.characterWidth = 48f;  // Largeur de votre personnage
//        this.characterHeight = 64f; // Hauteur de votre personnage
    }

    @Override
    public void attack(Character target) {
        int dmg = Math.max(1, this.damage - target.getDefense());
        target.setHp(target.getHp() - dmg);
    }

    public void loadAnimations() {
        addAnimation("idle", new Texture("warrior/Idle.png"), 5, 0.1f);
        addAnimation("walk", new Texture("warrior/Walk.png"), 9, 0.1f);
        addAnimation("attack", new Texture("warrior/Attack_1.png"), 4, 0.08f);
    }



}
