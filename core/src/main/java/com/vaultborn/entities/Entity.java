package com.vaultborn.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    protected Vector2 position;
    protected Vector2 velocity;
    protected TextureRegion texture;
    protected Rectangle bounds;

    public Entity(Vector2 position, TextureRegion texture) {
        this.position = position;
        this.texture = texture;
        this.velocity = new Vector2(0, 0);

        if (texture != null) {
            this.bounds = new Rectangle(position.x, position.y,
                texture.getRegionWidth(), texture.getRegionHeight());
        } else {
            this.bounds = new Rectangle(position.x, position.y, 32, 32);
        }
    }

    // Pour changer chaque frame mdr #soso_la_melo
    public abstract void update(float delta);

    // Pour dessiner l'entité mdr #soso_la_melo
    public abstract void render(SpriteBatch batch);
    public Rectangle getBounds() { return bounds; }

    public Vector2 getPosition() { return position; }
    public void setPosition(Vector2 position) {
        this.position = position;
        this.bounds.setPosition(position);
    }

    public TextureRegion getTexture() { return texture; }
    public void setTexture(TextureRegion texture) { this.texture = texture; }



}
