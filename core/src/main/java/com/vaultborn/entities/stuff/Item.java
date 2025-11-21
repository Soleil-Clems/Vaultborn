package com.vaultborn.entities.stuff;

import com.vaultborn.entities.Entity;

/**
 * Représente un conteneur pour un objet du jeu implémentant l'interface {@link Stuff} et héritant de {@link Entity}.
 * <p>
 * Cette classe permet de gérer des objets avec un type précis (consommable ou équipement) et de les manipuler de manière
 * générique dans le jeu.
 *
 * @param <T> le type de l'objet stocké, qui doit être une entité et implémenter {@link Stuff}
 */
public class Item<T extends Entity & Stuff> {

    /**
     * Enumération représentant le type de l'objet.
     */
    public enum Type {
        /** Objet consommable (ex: potion, nourriture) */
        CONSUMABLE,
        /** Objet d'équipement (ex: arme, armure) */
        EQUIPMENT
    }

    /** L'objet contenu dans cet Item */
    private T object;

    /** Le type de l'objet */
    private Type type;

    /**
     * Crée un nouvel Item avec l'objet et son type.
     *
     * @param object l'objet contenu
     * @param type   le type de l'objet (consommable ou équipement)
     */
    public Item(T object, Type type) {
        this.object = object;
        this.type = type;
    }

    /**
     * Retourne l'objet contenu dans cet Item.
     *
     * @return l'objet contenu
     */
    public T getObject() {
        return object;
    }

    /**
     * Retourne le type de l'objet contenu.
     *
     * @return le type de l'objet
     */
    public Type getType() {
        return type;
    }
}
