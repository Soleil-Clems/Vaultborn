package com.vaultborn.entities.characters.players;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.Character;

/**
 * Représente le Dark Warrior (Juzo), un personnage spécialisé en combat au corps à corps.
 * <p>
 * Le Dark Warrior est robuste et possède des attaques puissantes de courte portée.
 * Ses mouvements incluent la marche, la course, le saut et des animations spécifiques
 * pour les attaques, la protection, les dégâts reçus et la mort.
 */
public class DarkWarrior extends Player {
    private float meleeRange = 80f;

    /**
     * Constructeur principal avec texture assignée.
     *
     * @param position position initiale du Dark Warrior
     * @param texture  texture par défaut avant chargement des animations
     */
    public DarkWarrior(Vector2 position, TextureRegion texture) {
        super(position, texture, "Juzo");
        this.maxHp = 100;
        this.hp = this.maxHp;
        this.damage = 10;
        this.defense = 6;
        this.level = 1;
        this.agility = 2;
        this.range = 1;
    }

    /**
     * Constructeur utilisé pour les tests unitaires ou si la texture n’est pas encore chargée.
     *
     * @param position position initiale du personnage
     */
    public DarkWarrior(Vector2 position) {
        super(position, "Juzo");
        this.maxHp = 100;
        this.hp = this.maxHp;
        this.damage = 20;
        this.defense = 8;
        this.level = 1;
        this.agility = 2;
        this.range = 1;
    }

    /**
     * Attaque un personnage cible.
     * <p>
     * La logique d'attaque est gérée par {@link Player#attack(Character)}.
     *
     * @param character cible de l’attaque
     */
    @Override
    public void attack(Character character) {
        super.attack(character);
    }

    /**
     * Charge toutes les animations du Dark Warrior.
     * <p>
     * Inclut les animations suivantes :
     * <ul>
     *     <li>Idle</li>
     *     <li>Walk</li>
     *     <li>Run</li>
     *     <li>Jump</li>
     *     <li>Attack, Attack2, Attack3</li>
     *     <li>Protect</li>
     *     <li>Dead</li>
     *     <li>Hurt</li>
     * </ul>
     * Les spritesheets sont situées dans le dossier <code>darkwarrior/</code>.
     */
    public void loadAnimations() {
        String assets = "darkwarrior/";
        addAnimation("idle", new Texture(assets+"Idle.png"), 6, 0.1f);
        addAnimation("walk", new Texture(assets+"Walk.png"), 9, 0.1f);
        addAnimation("attack", new Texture(assets+"Attack_1.png"), 4, 0.15f);
        addAnimation("attack2", new Texture(assets+"Attack_2.png"), 5, 0.10f);
        addAnimation("attack3", new Texture(assets+"Attack_3.png"), 4, 0.15f);
        addAnimation("run", new Texture(assets+"Run.png"), 8, 0.08f);
        addAnimation("jump", new Texture(assets+"Jump.png"), 9, 0.09f);
        addAnimation("protect", new Texture(assets+"Protect.png"), 2, 2f);
        addAnimation("dead", new Texture(assets+"Dead.png"), 6, 0.15f);
        addAnimation("hurt", new Texture(assets+"Hurt.png"), 3, 0.09f);
    }
}
