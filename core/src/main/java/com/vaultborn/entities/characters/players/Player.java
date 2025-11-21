package com.vaultborn.entities.characters.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.vaultborn.entities.characters.Character;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.managers.AssetManager;
import com.vaultborn.screens.InventoryPlayer;

/**
 * Classe abstraite représentant un joueur contrôlé par l'utilisateur.
 * <p>
 * Player hérite de {@link Character} et ajoute :
 * <ul>
 *     <li>Un inventaire joueur via {@link InventoryPlayer}</li>
 *     <li>Un système d'expérience</li>
 *     <li>Des sons spécifiques (attaque, game over)</li>
 *     <li>Une portée d’attaque au corps à corps</li>
 * </ul>
 * Les classes de joueurs jouables (ex : Guerrier, Archer, Mage…) hériteront de Player.
 */
public abstract class Player extends Character {

    /** Inventaire du joueur. */
    protected InventoryPlayer inv;

    /** Gestionnaire des assets du joueur. */
    private AssetManager assets;

    /** Son joué lors d'une attaque. */
    protected Sound attackSound;

    /** Portée d'attaque au corps à corps. */
    private float meleeRange = 80f;

    /** Mode test unitaire (désactive les sons). */
    public boolean isTest = false;

    /**
     * Constructeur principal pour un joueur.
     *
     * @param position position initiale dans le monde
     * @param texture  texture du joueur (sprite)
     * @param name     nom du joueur
     */
    public Player(Vector2 position, TextureRegion texture, String name) {
        super(position, texture, name);
        this.isPlayerControlled = true;

        attackSound = Gdx.audio.newSound(Gdx.files.internal("sounds/sword.mp3"));
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("sounds/gameover.mp3"));
    }

    /**
     * Constructeur utilisé pour les tests unitaires (sans texture).
     *
     * @param position position initiale
     * @param name     nom du joueur
     */
    public Player(Vector2 position, String name) {
        super(position, name);
        this.isPlayerControlled = true;
        this.isTest = true;
    }

    /**
     * @return l'inventaire du joueur
     */
    public InventoryPlayer getInventory() {
        return inv;
    }

    /**
     * Définit l'inventaire du joueur.
     *
     * @param inv inventaire à assigner
     */
    public void setInventory(InventoryPlayer inv) {
        this.inv = inv;
    }

    /**
     * Ajoute de l'expérience à l'inventaire du joueur.
     *
     * @param nbExp quantité d'expérience gagnée
     */
    public void expGain(int nbExp) {
        inv.addExp(nbExp);
    }

    /**
     * Effectue une attaque contre une cible.
     * <p>
     * La méthode :
     * <ul>
     *     <li>joue un son (sauf en mode test)</li>
     *     <li>vérifie la distance d'attaque</li>
     *     <li>calcule les dégâts en tenant compte de la défense adverse</li>
     * </ul>
     *
     * @param target personnage ciblé (peut être null)
     */
    @Override
    public void attack(Character target) {
        if (!isTest) {
            attackSound.play(0.5f);
        }

        if (target == null) return;

        float distance = position.dst(target.getPosition());

        if (distance <= meleeRange) {
            int dmg = Math.max(1, this.damage - target.getDefense());
            target.takeDamage(dmg);
        }
    }

    /**
     * Gère la mort du joueur :
     * <ul>
     *     <li>déclenche animation "dead"</li>
     *     <li>réinitialise la physique</li>
     *     <li>joue le son de game over</li>
     * </ul>
     */
    @Override
    protected void die() {
        if (isDead) return;

        isDead = true;
        stateTime = 0f;
        velocityY = 0f;

        setAnimation("dead");
        gameOverSound.play(0.5f);
    }

    /**
     * Charge les animations du joueur.
     * <p>
     * À implémenter dans chaque classe fille
     * (ex : Guerrier, Archer...).
     */
    public void loadAnimations() {}
}
