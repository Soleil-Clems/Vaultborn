package com.vaultborn.entities.characters.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.Character;


public class Warrior extends Character {

    private float speed = 200f;

    public Warrior(Vector2 position, TextureRegion texture) {
        super(position, texture, "Lancelot");
        this.hp = 100;
        this.damage = 10;
        this.defense = 8;
        this.level = 1;
        this.agility = 10;
        this.range = 1;
    }

    @Override
    public void update(float delta) {
        float moveX = 0;
        float moveY = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))  moveX -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) moveX += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.UP))    moveY += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))  moveY -= 1;

        // Déplacement
        if (moveX != 0 || moveY != 0) {
            Vector2 move = new Vector2(moveX, moveY).nor().scl(speed * delta);
            position.add(move);
            bounds.setPosition(position);

            // ⚡ Changer l’animation en marche
            setAnimation("walk");
        } else {
            // ⚡ Retour à l’animation idle si on ne bouge pas
            setAnimation("idle");
        }

        // Toujours mettre à jour le timer de l’animation
        stateTime += delta;
    }

    @Override
    public void attack(Character target) {
        int dmg = Math.max(1, this.damage - target.getDefense());
        target.setHp(target.getHp() - dmg);
    }

    public void loadAnimations() {
        addAnimation("idle", new Texture("warrior/Idle.png"), 5, 0.1f);
        addAnimation("walk", new Texture("warrior/Walk.png"), 9, 0.08f);
        addAnimation("attack", new Texture("warrior/Attack_1.png"), 4, 0.08f);
    }


}
