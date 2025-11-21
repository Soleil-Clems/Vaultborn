package com.vaultborn.entities.characters.players;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.Character;

/**
 * Représente un mage spécialisé : le Satyr (Merline).
 * <p>
 * Le Satyr est un mage polyvalent, maîtrisant différentes formes d’attaques magiques
 * et disposant d’animations rapides et fluides. Il dispose de statistiques équilibrées
 * en attaque, défense et vitesse.
 *
 * <p>Comme tous les mages, il tire des projectiles définis dans {@link Mage#attack(Character)}.</p>
 */
public class Satyr extends Mage {

    /**
     * Constructeur principal.
     *
     * @param position position initiale du Satyr dans le monde
     * @param texture  texture affichée lorsque l'animation n’est pas encore chargée
     */
    public Satyr(Vector2 position, TextureRegion texture) {
        super(position, texture, "Merline");
        this.maxHp = 100;
        this.hp = this.maxHp;
        this.damage = 10;
        this.defense = 6;
        this.level = 1;
        this.agility = 2;
        this.range = 20;
    }

    /**
     * Constructeur utilisé pour les tests unitaires.
     *
     * @param position position initiale du Satyr
     */
    public Satyr(Vector2 position) {
        super(position, "Merline");
        this.maxHp = 100;
        this.hp = this.maxHp;
        this.damage = 20;
        this.defense = 8;
        this.level = 1;
        this.agility = 2;
        this.range = 10;
        this.speed = 400;
    }

    /**
     * Attaque du Satyr.
     * <p>
     * La logique exacte de l’attaque est gérée par {@link Mage#attack(Character)}
     * qui crée et lance un projectile en direction de la cible.
     *
     * @param target la cible visée par l’attaque
     */
    @Override
    public void attack(Character target) {
        super.attack(target);
    }

    /**
     * Charge toutes les animations du Satyr.
     * <p>
     * Les animations sont divisées en :
     * <ul>
     *     <li>Idle</li>
     *     <li>Walk</li>
     *     <li>Attaque : Attack / Attack2 / Attack3</li>
     *     <li>Run</li>
     *     <li>Jump</li>
     *     <li>Protect</li>
     *     <li>Dead</li>
     *     <li>Hurt</li>
     *     <li>Charge (projectile)</li>
     * </ul>
     *
     * Chaque animation utilise des spritesheets
     * situées dans le dossier <code>satyr/</code>.
     */
    public void loadAnimations() {
        addAnimation("idle", new Texture("satyr/Idle.png"), 7, 0.1f);
        addAnimation("walk", new Texture("satyr/Walk.png"), 12, 0.04f);
        addAnimation("attack", new Texture("satyr/Attack.png"), 8, 0.15f);
        addAnimation("attack2", new Texture("satyr/Attack.png"), 8, 0.10f);
        addAnimation("attack3", new Texture("satyr/Attack.png"), 8, 0.15f);
        addAnimation("run", new Texture("satyr/Walk.png"), 12, 0.08f);
        addAnimation("jump", new Texture("satyr/Walk.png"), 12, 0.09f);
        addAnimation("protect", new Texture("satyr/Attack.png"), 8, 2f);
        addAnimation("dead", new Texture("satyr/Dead.png"), 4, 0.15f);
        addAnimation("hurt", new Texture("satyr/Hurt.png"), 4, 0.09f);
        addAnimation("charge", new Texture("satyr/Charge.png"), 8, 0.09f);
    }
}
