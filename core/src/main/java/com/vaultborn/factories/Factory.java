package com.vaultborn.factories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.mobs.Gorgon;
import com.vaultborn.entities.characters.mobs.Mob;
import com.vaultborn.entities.characters.players.Player;
import com.vaultborn.entities.characters.players.Warrior;
import com.vaultborn.entities.stuff.GameObject;
import com.vaultborn.entities.stuff.armor.Hat;
import com.vaultborn.entities.stuff.weapon.Sword;
import com.vaultborn.managers.AssetManager;
import com.vaultborn.world.BaseWorld;

public class Factory {

    private final AssetManager assetsManager;
    private TextureRegion warriorRegion;
    private TextureRegion gorgonRegion;
    private TextureRegion swordRegion;

    public Factory() {
        assetsManager = new AssetManager();


        Texture warriorTexture = new Texture("warrior/Idle.png");
        warriorRegion = new TextureRegion(warriorTexture);


        Texture gorgonTexture = new Texture("gorgon/Idle.png");
        gorgonRegion = new TextureRegion(gorgonTexture);
        Texture swordTexture = new Texture("objects/weapons/sword.png");
        swordRegion = new TextureRegion(swordTexture);
    }


    public Player createPlayer(String type, float x, float y, BaseWorld world) {
        switch (type.toLowerCase()) {
            case "warrior":
                Warrior player = new Warrior(new Vector2(x, y), warriorRegion);
                player.loadAnimations();
                player.setWorld(world);
                return player;

            default:
                throw new IllegalArgumentException("Unknown player type: " + type);
        }
    }


    public Mob createMob(String type, float x, float y, BaseWorld world) {
        switch (type.toLowerCase()) {
            case "gorgon":
                Gorgon mob = new Gorgon(new Vector2(x, y), gorgonRegion);
                mob.loadAnimations();
                mob.setWorld(world);
                return mob;

            default:
                throw new IllegalArgumentException("Unknown mob type: " + type);
        }
    }


    public GameObject createObject(String type, float x, float y, BaseWorld world) {
        switch (type.toLowerCase()) {
            case "sword":
                Sword sword = new Sword(new Vector2(x, y), swordRegion);
                sword.loadAnimations();
                sword.setWorld(world);
                return sword;
            case "hat":
                Hat hat = new Hat(new Vector2(x, y), swordRegion,"","Mon chapeau");
                hat.setWorld(world);
                return hat;
            default:
                throw new IllegalArgumentException("Unknown mob type: " + type);
        }
    }
}
