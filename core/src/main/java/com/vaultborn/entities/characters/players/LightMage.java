package com.vaultborn.entities.characters.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.Character;

/**
 * Représente un mage spécialisé : le Light Mage (Lisa).
 * <p>
 * Le Light Mage est un personnage agile capable d'utiliser différentes attaques
 * magiques basées sur la lumière. Il possède plusieurs patterns d’attaques
 * (quatre au total), une animation de charge et des mouvements rapides.
 *
 * <p>Comme tous les mages, il utilise la logique d’attaque définie dans
 * {@link Mage#attack(Character)}.</p>
 */
public class LightMage extends Mage {

    /**
     * Constructeur principal avec texture assignée avant le chargement
     * des animations.
     *
     * @param position position initiale du Light Mage dans le monde
     * @param texture  texture par défaut avant chargement des animations
     */
    public LightMage(Vector2 position, TextureRegion texture) {
        super(position, texture, "Lisa");
        this.maxHp = 100;
        this.hp = this.maxHp;
        this.damage = 10;
        this.defense = 6;
        this.level = 1;
        this.agility = 2;
        this.range = 10;
    }

    /**
     * Constructeur utilisé dans les tests unitaires ou lorsque
     * la texture n’est pas encore définie.
     *
     * @param position position initiale du personnage
     */
    public LightMage(Vector2 position) {
        super(position, "Lisa");
        this.maxHp = 100;
        this.hp = this.maxHp;
        this.damage = 20;
        this.defense = 8;
        this.level = 1;
        this.agility = 2;
        this.range = 10;
    }

    /**
     * Attaque un personnage cible.
     * <p>
     * La logique principale est gérée par {@link Mage#attack(Character)},
     * qui crée et lance le projectile en fonction de l’animation d’attaque courante.
     *
     * @param target la cible de l’attaque
     */
    @Override
    public void attack(Character target) {
        super.attack(target);
    }

    /**
     * Gère toutes les entrées clavier du joueur contrôlant le Light Mage.
     * <p>
     * Les actions gérées sont :
     * <ul>
     *     <li>Déplacement (gauche/droite)</li>
     *     <li>Saut</li>
     *     <li>Quatre attaques différentes</li>
     *     <li>Protection</li>
     * </ul>
     *
     * @param delta temps écoulé depuis la dernière frame
     */
    @Override
    protected void handleInput(float delta) {
        attackSound = Gdx.audio.newSound(Gdx.files.internal("sounds/power.mp3"));

        this.charge = "lightmage/Charge.png";
        this.chargeFrameCount = 6;
        float moveX = 0;

        // Déplacements
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("left")))) {
            moveX -= 1f;
            facingRight = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("right")))) {
            moveX += 1f;
            facingRight = true;
        }

        // Attaques
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("attack")))) {
            attack = "attack";
        }
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("attack2")))) {
            attack = "attack2";
        }
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("attack3")))) {
            attack = "attack3";
        }
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("attack4")))) {
            attack = "attack4";
            attackSound = Gdx.audio.newSound(Gdx.files.internal("sounds/power2.mp3"));
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
     * Charge toutes les animations du Light Mage.
     * <p>
     * Inclut :
     * <ul>
     *     <li>Idle</li>
     *     <li>Walk</li>
     *     <li>Run</li>
     *     <li>Jump</li>
     *     <li>Quatre attaques</li>
     *     <li>Charge</li>
     *     <li>Protect</li>
     *     <li>Hurt</li>
     *     <li>Dead</li>
     * </ul>
     *
     * Les spritesheets sont situées dans le dossier <code>lightmage/</code>.
     */
    public void loadAnimations() {
        String asset = "lightmage/";
        addAnimation("idle", new Texture(asset+"Idle.png"), 7, 0.1f);
        addAnimation("walk", new Texture(asset+"Walk.png"), 7, 0.05f);
        addAnimation("attack", new Texture(asset+"Attack_1.png"), 10, 0.05f);
        addAnimation("attack2", new Texture(asset+"Attack_2.png"), 4, 0.05f);
        addAnimation("attack3", new Texture(asset+"Light_ball.png"), 7, 0.05f);
        addAnimation("attack4", new Texture(asset+"Light_Charge.png"), 13, 0.05f);
        addAnimation("run", new Texture(asset+"Run.png"), 8, 0.01f);
        addAnimation("jump", new Texture(asset+"Jump.png"), 8, 0.09f);
        addAnimation("protect", new Texture(asset+"Idle.png"), 7, 2f);
        addAnimation("dead", new Texture(asset+"Dead.png"), 5, 0.15f);
        addAnimation("hurt", new Texture(asset+"Hurt.png"), 3, 0.09f);
    }
}
