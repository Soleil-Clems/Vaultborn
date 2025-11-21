package com.vaultborn.entities.stuff.armor;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.Entity;
//import com.badlogic.gdx.graphics.Texture;
import com.vaultborn.entities.characters.Character;
import com.vaultborn.entities.characters.players.Player;
import com.vaultborn.entities.stuff.GameObject;
import com.vaultborn.entities.stuff.Stuff;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.vaultborn.screens.InventoryPlayer;


public abstract class Armor extends GameObject {
    protected String type;
    protected String name;
    protected int durability;
    protected String specialPerk;
    protected int health;
    protected int defense;
    protected int agility;
    protected int stamina;
    protected int mana;
    protected Sound pickUpSound;

    public Armor(Vector2 position, TextureRegion texture, String type, String name) {
        super(position, texture);
        this.type = type;
        this.name = name;
        this.durability = 100;
        pickUpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/pickup.mp3"));

    }

    public Armor(Vector2 position, String type, String name) {
        super(position);
        this.type = type;
        this.name = name;
        this.durability = 100;
        pickUpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/pickup.mp3"));

    }

    public Armor(Vector2 position, TextureRegion texture, String type, String name, String specialPerk) {
        super(position, texture);
        this.type = type;
        this.name = name;
        this.durability = 100;
        this.specialPerk = specialPerk;
        pickUpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/pickup.mp3"));

    }

    //getter
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public int getDurability() {
        return this.durability;
    }

    @Override
    public String getSpecialPerk() {
        return this.specialPerk;
    }

    public int getHealth() {
        return this.health;
    }

    public int getDefense() {
        return this.defense;
    }

    public int getAgility() {
        return this.agility;
    }

    public int getStamina() {
        return this.stamina;
    }

    public int getMana() {
        return this.mana;
    }

    //setter
    public void setDurability(boolean death) {
        if (death) {
            this.durability -= 20;
        } else {
            this.durability -= 1;
        }
        if (this.durability < 0) {
            this.durability = 0;
        }
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }


    //les différente special perk possible pour les armes
    public void Bonus(String specialPerk) {
        if (specialPerk.equals(null)) {
            return;
        } else {
            if (specialPerk.equals("immune")) {
                // ne peux pas subir de statut
                return;
            } else if (specialPerk.equals("unbreakable")) {
                // toujours this.durability = 100;
                return;
            }
            if (specialPerk.equals("extra jump")) {

                // peut faire un saut supplémentaire
                return;
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public boolean pickUp(Player character) {
        InventoryPlayer inv = character.getInventory();
        if (inv.getInventory().size() < 15) {
            inv.addInventory(this);
            pickUpSound.play(1f);
            return true;
        }
        return false;
    }
}
