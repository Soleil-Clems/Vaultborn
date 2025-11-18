package com.vaultborn.factories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.mobs.*;
import com.vaultborn.entities.characters.players.*;
import com.vaultborn.entities.stuff.GameObject;
import com.vaultborn.entities.stuff.trigger.SpecialDoor;
import com.vaultborn.entities.stuff.weapon.Sword;
import com.vaultborn.managers.AssetManager;
import com.vaultborn.world.BaseWorld;
import com.vaultborn.world.ForestWorld;
import com.vaultborn.world.HellWorld;

public class Factory {

    private final AssetManager assetsManager;
    private TextureRegion warriorRegion;
    private TextureRegion darkwarriorRegion;
    private TextureRegion satyrRegion;
    private TextureRegion gorgonRegion;
    private TextureRegion minotaurRegion;
    private TextureRegion swordRegion;
    private TextureRegion darkMageRegion;
    private TextureRegion lightMageRegion;
    private TextureRegion archerRegion;
    private TextureRegion tenguRegion;
    private TextureRegion yokaiRegion;
    private TextureRegion sunmageRegion;
    private TextureRegion specialDoorRegion;

    public Factory() {
        assetsManager = new AssetManager();

        warriorRegion = new TextureRegion(new Texture("warrior/Idle.png"));
        darkwarriorRegion = new TextureRegion(new Texture("darkwarrior/Idle.png"));
        satyrRegion = new TextureRegion(new Texture("satyr/Idle.png"));
        archerRegion = new TextureRegion(new Texture("archer/Idle.png"));
        sunmageRegion = new TextureRegion(new Texture("sunmage/Idle.png"));
        darkMageRegion = new TextureRegion(new Texture("darkmage/Idle.png"));
        tenguRegion = new TextureRegion(new Texture("tengu/Idle.png"));
        yokaiRegion = new TextureRegion(new Texture("yokai/Idle.png"));
        lightMageRegion = new TextureRegion(new Texture("lightmage/Idle.png"));
        gorgonRegion = new TextureRegion(new Texture("gorgon/Idle.png"));
        minotaurRegion = new TextureRegion(new Texture("minotaur/Idle.png"));
        swordRegion = new TextureRegion(new Texture("objects/weapons/sword.png"));
        specialDoorRegion = new TextureRegion(new Texture("specialdoor/closeddoor.png"));
    }

    public Player createPlayer(String type, float x, float y, BaseWorld world) {
        switch (type.toLowerCase()) {
            case "warrior":
                Warrior player = new Warrior(new Vector2(x, y), warriorRegion);
                player.loadAnimations();
                player.setWorld(world);
                return player;
            case "darkwarrior":
                DarkWarrior darkWarrior = new DarkWarrior(new Vector2(x, y), darkwarriorRegion);
                darkWarrior.loadAnimations();
                darkWarrior.setWorld(world);
                return darkWarrior;
            case "archer":
                Archer archer = new Archer(new Vector2(x, y), archerRegion);
                archer.loadAnimations();
                archer.setWorld(world);
                return archer;
            case "satyr":
                Satyr satyr = new Satyr(new Vector2(x, y), satyrRegion);
                satyr.loadAnimations();
                satyr.setWorld(world);
                return satyr;
            case "darkmage":
                DarkMage darkMage = new DarkMage(new Vector2(x, y), darkMageRegion);
                darkMage.loadAnimations();
                darkMage.setWorld(world);
                return darkMage;
            case "lightmage":
                LightMage lightMage = new LightMage(new Vector2(x, y), lightMageRegion);
                lightMage.loadAnimations();
                lightMage.setWorld(world);
                return lightMage;
            case "sunmage":
                SunMage sunMage = new SunMage(new Vector2(x, y), sunmageRegion);
                sunMage.loadAnimations();
                sunMage.setWorld(world);
                return sunMage;
            default:
                throw new IllegalArgumentException("Unknown player type: " + type);
        }
    }

    public Mob createMob(String type, float x, float y, BaseWorld world) {
        switch (type.toLowerCase()) {
            case "gorgon":
                Gorgon gorgon = new Gorgon(new Vector2(x, y), gorgonRegion, 1);
                gorgon.loadAnimations();
                gorgon.setWorld(world);
                return gorgon;
            case "minotaur":
                Minotaur minotaur = new Minotaur(new Vector2(x, y), minotaurRegion, 1);
                minotaur.loadAnimations();
                minotaur.setWorld(world);
                return minotaur;
            case "tengu":
                Tengu tengu = new Tengu(new Vector2(x, y), tenguRegion, 1);
                tengu.loadAnimations();
                tengu.setWorld(world);
                return tengu;
            case "yokai":
                Yokai yokai = new Yokai(new Vector2(x, y), yokaiRegion, 1);
                yokai.loadAnimations();
                yokai.setWorld(world);
                return yokai;
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

            case "special_door":
                SpecialDoor door = new SpecialDoor(new Vector2(x, y), specialDoorRegion);
                door.loadAnimations();
                door.setWorld(world);
                return door;

            default:
                throw new IllegalArgumentException("Unknown object type: " + type);
        }
    }

    public GameObject createSpecialDoor(String type, float x, float y, BaseWorld worldFrom, BaseWorld worldTo) {
        if (!type.equalsIgnoreCase("special_door"))
            throw new IllegalArgumentException("Unknown object type: " + type);

        SpecialDoor door = new SpecialDoor(new Vector2(x, y), specialDoorRegion);
        door.loadAnimations();
        door.setWorld(worldFrom);
        door.setTargetWorld(worldTo);
        return door;
    }

//    public BaseWorld createWorld(String name) {
//        switch (name.toLowerCase()) {
//
//            case "hell":
//                return new HellWorld();
//
//            case "forest":
//                return new ForestWorld();
//
//            default:
//                throw new IllegalArgumentException("Unknown world: " + name);
//        }
//    }
}
