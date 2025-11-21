package com.vaultborn.entities.stuff;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.Entity;

/**
 * Représente un objet du jeu qui est à la fois une {@link Entity} et un {@link Stuff}.
 * <p>
 * Cette classe abstraite sert de base pour tous les objets interactifs du jeu,
 * comme les équipements ou les consommables.
 */
public abstract class GameObject extends Entity implements Stuff {

    /**
     * Crée un nouvel objet du jeu à une position donnée avec une texture donnée.
     *
     * @param position La position initiale de l'objet
     * @param texture  La texture représentant l'objet
     */
    public GameObject(Vector2 position, TextureRegion texture) {
        super(position, texture);
    }

    public GameObject(Vector2 position) {
        super(position);
    }

    /**
     * Charge les animations associées à cet objet.
     * <p>
     * Cette méthode peut être redéfinie par les sous-classes si l'objet possède des animations.
     */
    public void loadAnimations() {
        // Méthode vide par défaut, à surcharger dans les classes filles
    }
}
