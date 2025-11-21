package com.vaultborn.entities.characters.players;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.Character;

/**
 * Représente un joueur de type Guerrier.
 * <p>
 * Le Warrior est un combattant spécialisé dans :
 * <ul>
 *     <li>Les attaques au corps à corps</li>
 *     <li>Une défense élevée</li>
 *     <li>Des animations variées (attaque, protection, course...)</li>
 * </ul>
 * Ce personnage est basé sur le chevalier "Lancelot".
 */
public class Warrior extends Player {

    /**
     * Constructeur principal du guerrier.
     * Initialise ses statistiques (HP, dégâts, défense, etc.).
     *
     * @param position position initiale dans le monde
     * @param texture  sprite utilisé pour l'affichage
     */
    public Warrior(Vector2 position, TextureRegion texture) {
        super(position, texture, "Lancelot");
        this.maxHp = 100;
        this.hp = this.maxHp;
        this.damage = 10;
        this.defense = 6;
        this.level = 1;
        this.agility = 2;
        this.range = 1;
    }

    /**
     * Constructeur secondaire utilisé pour les tests unitaires.
     *
     * @param position position initiale
     */
    public Warrior(Vector2 position) {
        super(position, "Lancelot");
        this.maxHp = 100;
        this.hp = this.maxHp;
        this.damage = 20;
        this.defense = 8;
        this.level = 1;
        this.agility = 2;
        this.range = 1;
        this.isTest = true;
    }

    /**
     * Attaque du guerrier.
     * <p>
     * Le guerrier attaque au corps à corps et utilise la logique d’attaque
     * définie dans {@link Player#attack(Character)}.
     *
     * @param character la cible de l'attaque
     */
    @Override
    public void attack(Character character) {
        super.attack(character);
    }

    /**
     * Charge toutes les animations du Warrior :
     * <ul>
     *     <li>Idle</li>
     *     <li>Walk</li>
     *     <li>Run</li>
     *     <li>Attacks (1, 2, 3)</li>
     *     <li>Jump</li>
     *     <li>Protect</li>
     *     <li>Dead</li>
     *     <li>Hurt</li>
     * </ul>
     * Chaque animation est extraite d'une spritesheet.
     */
    public void loadAnimations() {
        addAnimation("idle", new Texture("warrior/Idle.png"), 5, 0.1f);
        addAnimation("walk", new Texture("warrior/Walk.png"), 9, 0.1f);
        addAnimation("attack", new Texture("warrior/Attack_1.png"), 4, 0.15f);
        addAnimation("attack2", new Texture("warrior/Attack_2.png"), 5, 0.10f);
        addAnimation("attack3", new Texture("warrior/Attack_3.png"), 4, 0.15f);
        addAnimation("run", new Texture("warrior/Run.png"), 8, 0.08f);
        addAnimation("jump", new Texture("warrior/Jump.png"), 7, 0.09f);
        addAnimation("protect", new Texture("warrior/Protect.png"), 2, 2f);
        addAnimation("dead", new Texture("warrior/Dead.png"), 6, 0.15f);
        addAnimation("hurt", new Texture("warrior/Hurt.png"), 2, 0.09f);
    }

    /**
     * Libère les ressources du Warrior, notamment son son d'attaque.
     * À appeler lors de la destruction du personnage.
     */
    public void dispose() {
        if (attackSound != null) attackSound.dispose();
    }
}
