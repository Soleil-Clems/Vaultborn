package com.vaultborn.entities.characters.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.Character;

/**
 * Représente le Dark Mage (Mordred), un personnage spécialisé dans la magie offensive.
 * <p>
 * Le Dark Mage utilise des projectiles magiques et possède plusieurs types d'attaques
 * ainsi que des animations pour marcher, courir, sauter, attaquer, se protéger, être blessé et mourir.
 */
public class DarkMage extends Mage {

    /**
     * Constructeur principal avec texture assignée.
     *
     * @param position position initiale du Dark Mage
     * @param texture  texture par défaut avant chargement des animations
     */
    public DarkMage(Vector2 position, TextureRegion texture) {
        super(position, texture, "Mordred");
        this.maxHp = 100;
        this.hp = this.maxHp;
        this.damage = 10;
        this.defense = 6;
        this.level = 1;
        this.agility = 2;
        this.range = 10;
    }

    /**
     * Constructeur utilisé pour les tests unitaires ou si la texture n’est pas encore chargée.
     *
     * @param position position initiale du personnage
     */
    public DarkMage(Vector2 position) {
        super(position, "Mordred");
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
     * La logique d'attaque est gérée par {@link Mage#attack(Character)} et peut inclure
     * des projectiles magiques selon le type d'attaque sélectionné.
     *
     * @param target cible de l’attaque
     */
    @Override
    public void attack(Character target) {
        super.attack(target);
    }

    /**
     * Gère les entrées clavier pour le Dark Mage.
     * <p>
     * Détecte les touches pour se déplacer, sauter, attaquer ou se protéger.
     * Selon la touche, le type de projectile et le son d’attaque sont ajustés.
     *
     * @param delta temps écoulé depuis le dernier rendu (en secondes)
     */
    @Override
    protected void handleInput(float delta) {
        attackSound = Gdx.audio.newSound(Gdx.files.internal("sounds/power.mp3"));

        this.charge = "darkmage/Charge_2.png";
        this.chargeFrameCount = 6;
        float moveX = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("left")))) {
            moveX -= 1f;
            facingRight = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("right")))) {
            moveX += 1f;
            facingRight = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("attack")))){
            this.charge = "darkmage/Charge_1.png";
            this.chargeFrameCount = 9;
            attack = "attack";
        }
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("attack2")))) attack = "attack2";
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("attack3")))) attack = "attack3";
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("attack4")))){
            attack = "attack4";
            this.charge = "darkmage/Charge_1.png";
            attackSound = Gdx.audio.newSound(Gdx.files.internal("sounds/power2.mp3"));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) isProtected = true;

        if (Gdx.input.isKeyJustPressed(Input.Keys.valueOf(inputList.get("jump"))) && onGround) {
            velocityY = jumpSpeed + agility;
            onGround = false;
        }

        moveAndCollide(moveX * speed * delta, velocityY * delta);
    }

    /**
     * Charge toutes les animations du Dark Mage.
     * <p>
     * Inclut les animations suivantes :
     * <ul>
     *     <li>Idle</li>
     *     <li>Walk</li>
     *     <li>Run</li>
     *     <li>Jump</li>
     *     <li>Attack, Attack2, Attack3, Attack4</li>
     *     <li>Protect</li>
     *     <li>Dead</li>
     *     <li>Hurt</li>
     * </ul>
     * Les spritesheets sont situées dans le dossier <code>darkmage/</code>.
     */
    public void loadAnimations() {
        String asset = "darkmage/";
        addAnimation("idle", new Texture(asset+"Idle.png"), 8, 0.1f);
        addAnimation("walk", new Texture(asset+"Walk.png"), 7, 0.1f);
        addAnimation("attack", new Texture(asset+"Attack_1.png"), 7, 0.05f);
        addAnimation("attack2", new Texture(asset+"Attack_2.png"), 9, 0.05f);
        addAnimation("attack3", new Texture(asset+"Magic_arrow.png"), 6, 0.05f);
        addAnimation("attack4", new Texture(asset+"Magic_sphere.png"), 16, 0.05f);
        addAnimation("run", new Texture(asset+"Run.png"), 8, 0.01f);
        addAnimation("jump", new Texture(asset+"Jump.png"), 8, 0.09f);
        addAnimation("protect", new Texture(asset+"Attack_1.png"), 7, 2f);
        addAnimation("dead", new Texture(asset+"Dead.png"), 4, 0.15f);
        addAnimation("hurt", new Texture(asset+"Hurt.png"), 4, 0.09f);
    }
}
