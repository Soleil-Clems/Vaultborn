package com.vaultborn.entities.characters.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.Character;
import com.vaultborn.entities.projectiles.Projectile;

/**
 * Classe abstraite représentant un joueur de type Mage.
 * <p>
 * Le mage se distingue des autres joueurs par :
 * <ul>
 *     <li>Une attaque à distance utilisant un projectile magique</li>
 *     <li>Une animation de charge (spritesheet)</li>
 *     <li>Un son d'attaque spécifique</li>
 * </ul>
 * Les classes de mages jouables (ex : FireMage, IceMage...) hériteront de cette classe.
 */
public abstract class Mage extends Player {

    /** Chemin vers la spritesheet de charge du projectile. */
    public String charge = "satyr/charge.png";

    /** Nombre de frames dans l'animation de charge. */
    protected int chargeFrameCount = 8;

    /**
     * Constructeur principal du Mage.
     *
     * @param position position initiale du mage
     * @param texture  sprite du mage
     * @param name     nom du personnage
     */
    public Mage(Vector2 position, TextureRegion texture, String name) {
        super(position, texture, name);
        attackSound = Gdx.audio.newSound(Gdx.files.internal("sounds/power.mp3"));
    }

    /**
     * Constructeur alternatif utilisé pour les tests unitaires.
     *
     * @param position position initiale
     * @param name     nom du personnage
     */
    public Mage(Vector2 position, String name) {
        super(position, name);
        this.isTest = true;
    }

    /**
     * Attaque du mage : tire un projectile magique.
     * <p>
     * Fonctionnement :
     * <ul>
     *     <li>Joue un son d'incantation</li>
     *     <li>Crée un projectile depuis la position du mage</li>
     *     <li>Le projectile vise soit la cible, soit une direction (si target == null)</li>
     *     <li>Le projectile est ajouté au monde</li>
     * </ul>
     *
     * @param target la cible visée (peut être null : le mage tire alors en ligne droite)
     */
    @Override
    public void attack(Character target) {
        attackSound.play(0.5f);

        if (world == null) {
            return;
        }

        Vector2 projPos = new Vector2(position.x + characterWidth / 2, position.y + characterHeight / 2);

        TextureRegion projTexture = new TextureRegion(new Texture(charge));

        Vector2 targetPos = target != null
            ? target.getPosition()
            : new Vector2(position.x + (facingRight ? 1000 : -1000), position.y);

        Projectile proj = new Projectile(
            projPos,
            projTexture,
            this.facingRight,
            this.damage,
            targetPos,
            chargeFrameCount
        );

        proj.setWorld(world);
        world.projectiles.add(proj);
    }
}
