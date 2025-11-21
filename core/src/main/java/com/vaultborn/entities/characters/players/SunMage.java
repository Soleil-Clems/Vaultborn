package com.vaultborn.entities.characters.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.Character;

/**
 * Représente un Mage spécialisé : le SunMage (Monet).
 * <p>
 * Le SunMage utilise des attaques magiques à distance basées sur la lumière et le feu.
 * Il dispose de plusieurs compétences :
 * <ul>
 *     <li>Attaque 1 : Fire Bolt</li>
 *     <li>Attaque 2 : Fire Blast</li>
 *     <li>Attaque 3 : Solar Burst</li>
 *     <li>Forme de protection</li>
 * </ul>
 * Ce personnage possède une agilité et une puissance d’attaque très élevées.
 */
public class SunMage extends Mage {

    /**
     * Constructeur principal.
     *
     * @param position position initiale dans le monde
     * @param texture  sprite de base pour le rendu
     */
    public SunMage(Vector2 position, TextureRegion texture) {
        super(position, texture, "Monet");
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
     * @param position position initiale
     */
    public SunMage(Vector2 position) {
        super(position, "Monet");
        this.maxHp = 100;
        this.hp = this.maxHp;
        this.damage = 30;
        this.defense = 20;
        this.level = 1;
        this.agility = 40;
        this.range = 20;
    }

    /**
     * Attaque magique du SunMage.
     * Utilise la logique d’attaque définie dans {@link Mage#attack(Character)}.
     *
     * @param target la cible visée
     */
    @Override
    public void attack(Character target) {
        super.attack(target);
    }

    /**
     * Gestion des inputs du joueur lorsqu'il contrôle le SunMage.
     * <p>
     * Ce mage dispose de plusieurs variantes d’attaques,
     * avec des sprites de projectiles différents selon la compétence utilisée.
     * <ul>
     *     <li>Left → se déplacer à gauche</li>
     *     <li>Right → se déplacer à droite</li>
     *     <li>Q → attaque Fire_1</li>
     *     <li>A → attaque Fire_2 (plus puissante)</li>
     *     <li>D → attaque Fire_3</li>
     *     <li>Shift → protection</li>
     *     <li>Space → saut</li>
     * </ul>
     *
     * @param delta temps écoulé depuis le dernier frame
     */
    @Override
    protected void handleInput(float delta) {

        // Configuration dynamique du projectile selon l’attaque
        attackSound = Gdx.audio.newSound(Gdx.files.internal("sounds/power.mp3"));
        this.charge = "sunmage/Fire_1.png";
        this.chargeFrameCount = 14;

        float moveX = 0;

        // Déplacement horizontal
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("left")))) {
            moveX -= 1f;
            facingRight = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("right")))) {
            moveX += 1f;
            facingRight = true;
        }

        // Attaque 1
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("attack")))) {
            attack = "attack";
        }

        // Attaque 2 (plus puissante, change le projectile)
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("attack2")))) {
            attack = "attack2";
            this.charge = "sunmage/Fire_2.png";
            this.chargeFrameCount = 11;
            attackSound = Gdx.audio.newSound(Gdx.files.internal("sounds/power2.mp3"));
        }

        // Attaque 3
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("attack3")))) {
            attack = "attack3";
        }

        // Protection
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            isProtected = true;
        }

        // Saut
        if (Gdx.input.isKeyJustPressed(Input.Keys.valueOf(inputList.get("jump"))) && onGround) {
            velocityY = jumpSpeed + agility;
            onGround = false;
        }

        moveAndCollide(moveX * speed * delta, velocityY * delta);
    }

    /**
     * Charge toutes les animations du SunMage.
     * <p>
     * Contient les animations :
     * <ul>
     *     <li>Idle</li>
     *     <li>Walk</li>
     *     <li>Attaque (1, 2, 3)</li>
     *     <li>Run</li>
     *     <li>Jump</li>
     *     <li>Protect</li>
     *     <li>Dead</li>
     *     <li>Hurt</li>
     * </ul>
     */
    public void loadAnimations() {
        String asset = "sunmage/";
        addAnimation("idle", new Texture(asset + "Idle.png"), 8, 0.1f);
        addAnimation("walk", new Texture(asset + "Walk.png"), 8, 0.05f);
        addAnimation("attack", new Texture(asset + "Attack_1.png"), 10, 0.05f);
        addAnimation("attack2", new Texture(asset + "Attack_2.png"), 10, 0.05f);
        addAnimation("attack3", new Texture(asset + "Attack_3.png"), 7, 0.05f);
        addAnimation("run", new Texture(asset + "Run.png"), 8, 0.01f);
        addAnimation("jump", new Texture(asset + "Jump.png"), 10, 0.09f);
        addAnimation("protect", new Texture(asset + "Idle_2.png"), 6, 2f);
        addAnimation("dead", new Texture(asset + "Dead.png"), 10, 0.10f);
        addAnimation("hurt", new Texture(asset + "Hurt.png"), 2, 0.09f);
    }
}
