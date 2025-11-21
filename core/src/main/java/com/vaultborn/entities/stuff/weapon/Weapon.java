package com.vaultborn.entities.stuff.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.Character;
import com.vaultborn.entities.characters.players.Player;
import com.vaultborn.entities.stuff.GameObject;
import com.vaultborn.managers.AssetManager;
import com.vaultborn.save.SaveData;
import com.vaultborn.save.SaveManager;
import com.vaultborn.entities.stuff.Stuff;
import com.vaultborn.screens.InventoryPlayer;
import com.vaultborn.world.BaseWorld;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe abstraite représentant une arme dans le jeu.
 * <p>
 * Les armes sont des objets que le joueur peut ramasser et utiliser pour infliger des dégâts
 * aux ennemis. Elles peuvent avoir un type, des dégâts, une portée et un effet spécial (specialPerk).
 * Cette classe gère également l'affichage via {@link Animation} et le son de ramassage.
 */
public abstract class Weapon extends GameObject{
    protected String type;
    protected String name;
    protected int durability;
    protected String specialPerk;
    protected int damage;
    protected int range;
    protected Character character;
    public BaseWorld world;
    private AssetManager assets;
    protected Sound pickUpSound;
    protected Map<String, Animation<TextureRegion>> animations = new HashMap<>();


    //weapon sans specialPerk
 /**
     * Constructeur d'une arme sans specialPerk.
     *
     * @param position Position initiale de l'arme
     * @param texture  Texture de l'arme
     * @param type     Type et nom de l'arme
     */
    public Weapon(Vector2 position, TextureRegion texture, String type) {
        super(position, texture);
        this.type = type;
        this.name = type;
        this.durability = 100;
        this.specialPerk = null;
        pickUpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/pickup.mp3"));
    }

    /**
     * Constructeur d'une arme avec specialPerk.
     *
     * @param position    Position initiale de l'arme
     * @param texture     Texture de l'arme
     * @param type        Type et nom de l'arme
     * @param specialPerk Effet spécial appliqué à l'arme
     */
    public Weapon(Vector2 position, TextureRegion texture, String type, String specialPerk) {
        super(position, texture);
        this.type = type;
        this.name = type;
        this.durability = 100;
        this.specialPerk = specialPerk;
        pickUpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/pickup.mp3"));
    }

    public Weapon(Vector2 position,  String type) {
        super(position);
        this.type = type;
        this.name = type;
        this.durability = 100;
        this.specialPerk = specialPerk;
    }



    @Override
    public String getName() { return name; }

    @Override
    public String getType() { return type; }

    @Override
    public int getDurability() { return durability; }

    @Override
    public String getSpecialPerk() { return specialPerk; }

    public int getDamage() { return damage; }

    public int getRange() { return range; }



    /** Réduit la durabilité de l'arme de 1 à chaque utilisation. */
    public void setDurability() { this.durability -= 1; }

    public void setDamage(int damage) { this.damage = damage; }

    public void setRange(int range) { this.range = range; }

    /**
     * Applique un bonus ou un effet spécial selon le type.
     * <p>
     * Exemple :
     * - "fire" : ajoute un dommage sur la durée
     * - "unbreakable" : durabilité toujours à 100
     * - "moreRange" : augmente la portée
     *
     * @param specialPerk type de bonus
     */
    public void Bonus(String specialPerk) {
        if (specialPerk == null) return;
        switch (specialPerk) {
            case "fire":
                //  effet de dégâts sur la durée
                break;
            case "unbreakable":
                //  durabilité infinie
                break;
            case "moreRange":
                this.range = Math.round(this.range * 1.2f);
                break;
        }
    }


    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    @Override
    public void update(float delta) {
        // Peut être surchargé par les sous-classes
    }

    /**
     * Permet au joueur de ramasser l'arme.
     *
     * @param character joueur qui ramasse l'arme
     */
    @Override
    public boolean pickUp(Player character) {
        InventoryPlayer inv = character.getInventory();
        if(inv.getInventory().size()<15){inv.addInventory(this); pickUpSound.play(1f); return true;}
        return false;

    }

    /** Associe un monde à l'arme pour interactions futures. */
    public void setWorld(BaseWorld world) {
        this.world = world;
    }

    /**
     * Ajoute une animation à l'arme.
     *
     * @param key           clé de l'animation
     * @param spriteSheet   texture contenant toutes les frames
     * @param frameCount    nombre de frames
     * @param frameDuration durée de chaque frame
     */
    protected void addAnimation(String key, Texture spriteSheet, int frameCount, float frameDuration) {
        int frameWidth = spriteSheet.getWidth() / frameCount;
        int frameHeight = spriteSheet.getHeight();
        TextureRegion[][] tmp = TextureRegion.split(spriteSheet, frameWidth, frameHeight);

        TextureRegion[] frames = new TextureRegion[frameCount];
        for (int i = 0; i < frameCount; i++) {
            frames[i] = tmp[0][i];
        }

        Animation<TextureRegion> anim = new Animation<>(frameDuration, frames);
        animations.put(key, anim);
    }
}
