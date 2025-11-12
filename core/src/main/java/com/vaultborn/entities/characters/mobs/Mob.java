package com.vaultborn.entities.characters.mobs;

import com.vaultborn.entities.characters.Character;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class Mob extends Character {

    protected int weapon;
    private float attackCooldown = 1.5f;
    private float attackTimer = 0f;

    private float deadTimer = 0f;
    private final float deadDuration = 5f;
    public boolean readyToRemove = false;

    public Mob(Vector2 position, TextureRegion texture, String name) {
        super(position, texture, name);
        this.isPlayerControlled = false;
    }

    @Override
    protected void handleAI(float delta) {
        float speedAI = 50f;
        float moveX = (float) Math.sin(stateTime * 1.5f) * speedAI * delta;
        moveAndCollide(moveX, velocityY * delta);
    }

    @Override
    public void update(float delta) {
        if (isDead) {
            Animation<TextureRegion> deadAnim = animations.get("dead");

            if (deadAnim != null) {
                if (!deadAnim.isAnimationFinished(stateTime)) {
                    setAnimation("dead");
                    stateTime += delta;
                }
            }

            deadTimer += delta;
            if (deadTimer >= deadDuration) {
                readyToRemove = true;
            }

            velocityY = 0;
            return;
        }

        if (world == null || world.getPlayer() == null) return;

        Character player = world.getPlayer();
        Vector2 playerPos = player.getPosition();

        float distanceX = playerPos.x - position.x;
        float distanceY = playerPos.y - position.y;
        float distance = (float) Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        float speed = 100f;
        float attackRange = 50f;
        float followRange = 300f;

        if (player.getHp() <= 0) {
            setAnimation("idle");
            return;
        }

        facingRight = distanceX > 0;

        attackTimer -= delta;

        if (distance > attackRange && distance < followRange) {
            float moveX = Math.signum(distanceX) * speed * delta;
            position.x += moveX;
            setAnimation("walk");
        } else if (distance <= attackRange) {
            if (attackTimer <= 0f) {
                setAnimation("attack");
                attack(player);
                attackTimer = attackCooldown;
            }
        } else {
            setAnimation("idle");
        }

        bounds.setPosition(position);
        stateTime += delta;
    }
}
