package com.vaultborn.entities.stuff.weapon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.Character;
import com.vaultborn.entities.characters.players.Player;
import com.vaultborn.screens.InventoryPlayer;
//import com.vaultborn.entities.Entity;
import com.vaultborn.entities.stuff.Item;


public class Sword extends Weapon{
    protected InventoryPlayer inventoryPlayer;
    private final Item<Sword> weapon = new Item<>(this, Item.Type.EQUIPMENT);
    protected Character character;
    public Sword(Vector2 position, TextureRegion texture) {
        super(position, texture, "Sword");
        this.range = 5;
        this.damage = 100;
//        this.inventoryPlayer = new InventoryPlayer();

    }

    public Sword(Vector2 position, TextureRegion texture,String specialPerk) {
        super(position, texture, "Sword",specialPerk);
        this.range = 5;
        this.damage = 10;
//        this.inventoryPlayer = new InventoryPlayer();


    }

    public void loadAnimations() {
        addAnimation("static", new Texture("objects/weapons/sword.png"), 1, 0.1f);
    }


//    @Override
//    public void pickUp(Character character) {
//        if (character instanceof Player) {
//            character.setDamage(this.damage+ character.getDamage());
////            this.inventoryPlayer.addInventory(weapon);
//        }
//    }


}
