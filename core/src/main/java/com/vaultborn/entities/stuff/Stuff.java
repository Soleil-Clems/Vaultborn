package com.vaultborn.entities.stuff;

import com.vaultborn.entities.characters.players.Player;

/**
 * Représente un objet ou équipement récupérable par un joueur.
 * <p>
 * Les objets implémentant cette interface peuvent être ramassés par un personnage et possèdent des caractéristiques
 * telles que le type, le nom, la durabilité et un effet spécial.
 */
public interface Stuff {

    /**
     * Retourne le type de l'objet (ex. arme, armure, potion, etc.).
     *
     * @return le type de l'objet sous forme de chaîne
     */
    String getType();

    /**
     * Retourne le nom de l'objet.
     *
     * @return le nom de l'objet
     */
    String getName();

    /**
     * Retourne la durabilité actuelle de l'objet.
     * <p>
     * La durabilité peut diminuer avec l'utilisation et atteindre zéro.
     *
     * @return la durabilité de l'objet
     */
    int getDurability();

    /**
     * Retourne l'effet ou le bonus spécial fourni par l'objet.
     *
     * @return la description du perk spécial
     */
    String getSpecialPerk();

    /**
     * Permet à un joueur de ramasser l'objet.
     * <p>
     * La logique peut inclure l'ajout à l'inventaire, l'application de bonus ou d'effets sur le joueur.
     *
     * @param character le joueur qui ramasse l'objet
     */
    boolean pickUp(Player character);
}
