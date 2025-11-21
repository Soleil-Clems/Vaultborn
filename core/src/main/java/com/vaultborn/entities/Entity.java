package com.vaultborn.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.world.BaseWorld;

/**
 * Classe abstraite représentant une entité générique dans le jeu.
 * <p>
 * Une entité possède :
 * - une position
 * - une vélocité
 * - une texture (facultative)
 * - une hitbox (Rectangle)
 * - une référence vers le monde dans lequel elle existe
 * <p>
 * Toutes les entités doivent implémenter les méthodes {@link #update(float)} et {@link #render(SpriteBatch)}.
 */
public abstract class Entity {

    /** Position de l'entité dans le monde. */
    protected Vector2 position;

    /** Vitesse actuelle de l'entité. */
    protected Vector2 velocity;

    /** Texture ou frame courante utilisée pour l'affichage. */
    protected TextureRegion texture;

    /** Rectangle représentant la zone de collision de l'entité. */
    protected Rectangle bounds;

    /** Monde dans lequel l'entité évolue. */
    public BaseWorld world;

    /**
     * Constructeur principal permettant de créer une entité avec une position et une texture.
     *
     * @param position position initiale de l'entité
     * @param texture  texture affichée (peut être {@code null})
     */
    public Entity(Vector2 position, TextureRegion texture) {
        this.position = position;
        this.texture = texture;
        this.velocity = new Vector2(0, 0);

        if (texture != null) {
            this.bounds = new Rectangle(
                position.x,
                position.y,
                texture.getRegionWidth(),
                texture.getRegionHeight()
            );
        } else {
            // Taille par défaut si aucune texture n'est fournie
            this.bounds = new Rectangle(position.x, position.y, 32, 32);
        }
    }

    /**
     * Constructeur simplifié utilisé principalement pour les tests unitaires.
     * Initialise l'entité sans texture, avec une hitbox par défaut de 32x32.
     *
     * @param position position initiale de l'entité
     */
    public Entity(Vector2 position) {
        this.position = position;
        this.velocity = new Vector2(0, 0);
        this.texture = null;

        this.bounds = new Rectangle(position.x, position.y, 32, 32);
    }

    /**
     * Met à jour l'état de l'entité à chaque frame.
     * <p>
     * Les sous-classes doivent implémenter cette méthode pour gérer :
     * - déplacements
     * - animations
     * - interactions
     *
     * @param delta temps écoulé depuis la dernière frame (en secondes)
     */
    public abstract void update(float delta);

    /**
     * Dessine l'entité à l'écran.
     *
     * @param batch SpriteBatch utilisé pour le rendu
     */
    public abstract void render(SpriteBatch batch);

    /**
     * Retourne les limites (hitbox) de l'entité.
     *
     * @return rectangle représentant les collisions
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Met à jour la position du rectangle de collision pour correspondre à celle de l'entité.
     */
    public void updateBounds() {
        bounds.setPosition(position.x, position.y);
    }

    /**
     * Retourne la position de l'entité.
     *
     * @return vecteur indiquant la position
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Modifie la position de l'entité et met à jour les limites.
     *
     * @param position nouvelle position
     */
    public void setPosition(Vector2 position) {
        this.position = position;
        this.bounds.setPosition(position);
    }

    /**
     * Retourne la texture de l'entité.
     *
     * @return texture actuelle (peut être {@code null})
     */
    public TextureRegion getTexture() {
        return texture;
    }

    /**
     * Définit la texture utilisée pour l'affichage.
     *
     * @param texture nouvelle texture
     */
    public void setTexture(TextureRegion texture) {
        this.texture = texture;
    }

    /**
     * Associe l'entité à un monde.
     *
     * @param world monde dans lequel l'entité évolue
     */
    public void setWorld(BaseWorld world) {
        this.world = world;
    }
}
