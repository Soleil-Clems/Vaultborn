package com.vaultborn.entities.characters.mobs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.vaultborn.entities.characters.Character;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.MainGame;

public abstract class Mob extends Character {

    protected int weapon;
    private float attackCooldown = 1.5f;
    private float attackTimer = 0f;
    private float aiOffset;

    public Mob(Vector2 position, TextureRegion texture, String name) {
        super(position, texture, name);
        this.isPlayerControlled = false;
        aiOffset = (float)(Math.random() * 0.5f);
    }

    @Override
    protected void handleAI(float delta) {
        if (world == null || world.getPlayer() == null) {
            moveAndCollide(0, velocityY * delta);
            return;
        }

        Character player = world.getPlayer();
        Vector2 playerPos = player.getPosition();

        float distanceX = playerPos.x - position.x;
        float distanceY = playerPos.y - position.y;
        float distance = (float) Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        float speed = 100f + aiOffset * 40f;
        float attackRange = 50f;
        float followRange = 300f+ aiOffset * 20f;

        if (player.getHp() <= 0) {
            setAnimation("idle");
            moveAndCollide(0, velocityY * delta); // Gravité même si joueur mort
            return;
        }

        facingRight = distanceX > 0;
        attackTimer -= delta;

        if (distance > attackRange && distance < followRange) {

            float moveX = Math.signum(distanceX) * speed * delta;
            moveAndCollide(moveX, velocityY * delta);
            setAnimation("walk");
        } else if (distance <= attackRange) {

            if (attackTimer <= 0f) {
                startAttack("attack");
                attackTimer = attackCooldown;
            }

            moveAndCollide(0, velocityY * delta);
        } else {

            setAnimation("idle");
            moveAndCollide(0, velocityY * delta);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        if (!isDead) {
            renderHealthBar(batch);
        }
    }

    private void renderHealthBar(SpriteBatch batch) {
        int hp = getHp();
        int maxHp = getMaxHp();
        float ratio = Math.max(0f, Math.min(1f, (float) hp / maxHp));


        float x = position.x + (characterWidth / 2) - 20;
        float y = position.y + characterHeight + 5;
        float width = 40f;
        float height = 6f;


        batch.setColor(Color.BLACK);
        batch.draw(MainGame.getWhitePixel(), x - 1, y - 1, width + 2, height + 2);


        batch.setColor(0.3f, 0.3f, 0.3f, 1f);
        batch.draw(MainGame.getWhitePixel(), x, y, width, height);


        batch.setColor(0.8f, 0f, 0f, 1f);
        batch.draw(MainGame.getWhitePixel(), x, y, width * ratio, height);

        batch.setColor(Color.WHITE);
    }
}
