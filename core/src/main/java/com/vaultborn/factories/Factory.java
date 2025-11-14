package com.vaultborn.factories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.mobs.Gorgon;
import com.vaultborn.entities.characters.mobs.Mob;
import com.vaultborn.entities.characters.players.Player;
import com.vaultborn.entities.characters.players.Warrior;
import com.vaultborn.entities.stuff.GameObject;
import com.vaultborn.entities.stuff.armor.Breastplate;
import com.vaultborn.entities.stuff.armor.GauteletPlate;
import com.vaultborn.entities.stuff.armor.Hat;
import com.vaultborn.entities.stuff.armor.Helmet;
import com.vaultborn.entities.stuff.armor.IronFoot;
import com.vaultborn.entities.stuff.armor.LegPlate;
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
            case "helmet":
                Helmet helmet = new Helmet(new Vector2(x, y), swordRegion,"","Le magnifique casque");
                helmet.setWorld(world);
                return helmet;
            case "breastplate":
                Breastplate breastpalte = new Breastplate(new Vector2(x, y), swordRegion,"","Le plastron lustre");
                breastpalte.setWorld(world);
                return breastpalte;
            case "gauteletplate":
                GauteletPlate gauteletPlate = new GauteletPlate(new Vector2(x, y), swordRegion,"","Les poings");
                gauteletPlate.setWorld(world);
                return gauteletPlate;
            case "legplate":
                LegPlate legPlate = new LegPlate(new Vector2(x, y), swordRegion,"","Les jambes lourdes");
                legPlate.setWorld(world);
                return legPlate;
            case "ironfoot":
                IronFoot ironFoot = new IronFoot(new Vector2(x, y), swordRegion,"","Des petits chaussons");
                ironFoot.setWorld(world);
                return ironFoot;
            default:
                throw new IllegalArgumentException("Unknown mob type: " + type);
        }
    }
}
