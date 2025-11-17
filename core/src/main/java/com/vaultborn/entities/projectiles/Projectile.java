package com.vaultborn.entities.projectiles;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.Entity;
import com.vaultborn.entities.characters.mobs.Mob;

public class Projectile extends Entity {

    public int damage;
    public boolean facingRight;
    public boolean toRemove = false;
    public float speed = 600f;
    private Vector2 target;
    private Animation<TextureRegion> animation;
    private float stateTime = 0f;
    private Vector2 startPosition;

    private float projectileWidth = 16f;
    private float projectileHeight = 16f;

    public Projectile(Vector2 position, TextureRegion texture, boolean facingRight, int damage, Vector2 targetPosition, int chargeFrameCount) {
        super(position, texture);
        this.facingRight = facingRight;
        this.damage = damage;
        this.target = new Vector2(targetPosition);
        this.startPosition = new Vector2(position);

        Vector2 dir = facingRight ? new Vector2(1, 0) : new Vector2(-1, 0);
        this.velocity = dir.scl(speed);

        TextureRegion[][] tmp = TextureRegion.split(texture.getTexture(), texture.getRegionWidth() / chargeFrameCount, texture.getRegionHeight());
        TextureRegion[] frames = new TextureRegion[chargeFrameCount]; // ← Changé de 8 à chargeFrameCount
        for (int i = 0; i < chargeFrameCount; i++) frames[i] = tmp[0][i]; // ← Boucle jusqu'à chargeFrameCount
        animation = new Animation<>(0.09f, frames);

        bounds.setSize(projectileWidth, projectileHeight);
    }

    public Projectile(Vector2 position, TextureRegion texture, boolean facingRight, int damage, Vector2 targetPosition) {
        this(position, texture, facingRight, damage, targetPosition, 8);
    }

    @Override
    public void update(float delta) {
        stateTime += delta;

        Vector2 movement = velocity.cpy().scl(delta);
        Vector2 newPosition = position.cpy().add(movement);

        if (world != null && checkMapCollision(newPosition)) {
            toRemove = true;
            return;
        }

        position.set(newPosition);
        bounds.setPosition(position);

        if (position.x < -2000 || position.x > 20000 || position.y < -2000 || position.y > 20000) {
            toRemove = true;
            return;
        }

        if (world != null) {
            for (Mob mob : world.mobs) {
                if (!mob.isDead && bounds.overlaps(mob.getBounds())) {
                    float dx = mob.getPosition().x - startPosition.x;
                    boolean isInCorrectDirection = (facingRight && dx > 0) || (!facingRight && dx < 0);

                    if (isInCorrectDirection) {
                        mob.takeDamage(damage);
                        toRemove = true;
                        break;
                    }
                }
            }
        }
    }

    private boolean checkMapCollision(Vector2 pos) {
        if (world == null) return false;

        boolean topLeft = world.isCellBlocked(pos.x, pos.y + projectileHeight);
        boolean topRight = world.isCellBlocked(pos.x + projectileWidth, pos.y + projectileHeight);
        boolean bottomLeft = world.isCellBlocked(pos.x, pos.y);
        boolean bottomRight = world.isCellBlocked(pos.x + projectileWidth, pos.y);

        return topLeft || topRight || bottomLeft || bottomRight;
    }

    @Override
    public void render(SpriteBatch batch) {
        if (animation != null) {
            TextureRegion frame = animation.getKeyFrame(stateTime, true);

            if ((facingRight && frame.isFlipX()) || (!facingRight && !frame.isFlipX())) {
                frame.flip(true, false);
            }

            batch.draw(frame, position.x, position.y);
        }
    }

    public Rectangle getHitbox() {
        return bounds;
    }
}
