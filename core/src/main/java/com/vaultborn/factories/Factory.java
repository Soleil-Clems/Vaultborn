package com.vaultborn.factories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.mobs.Gorgon;
import com.vaultborn.entities.characters.mobs.Minotaur;
import com.vaultborn.entities.characters.mobs.Mob;
import com.vaultborn.entities.characters.players.*;
import com.vaultborn.entities.stuff.GameObject;
import com.vaultborn.entities.stuff.trigger.SpecialDoor;
import com.vaultborn.entities.stuff.armor.Breastplate;
import com.vaultborn.entities.stuff.armor.GauteletPlate;
import com.vaultborn.entities.stuff.armor.Hat;
import com.vaultborn.entities.stuff.armor.Helmet;
import com.vaultborn.entities.stuff.armor.IronFoot;
import com.vaultborn.entities.stuff.armor.LegPlate;
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
    private TextureRegion specialDoorRegion;

    public Factory() {
        assetsManager = new AssetManager();

        warriorRegion = new TextureRegion(new Texture("warrior/Idle.png"));
        darkwarriorRegion = new TextureRegion(new Texture("darkwarrior/Idle.png"));
        satyrRegion = new TextureRegion(new Texture("satyr/Idle.png"));
        archerRegion = new TextureRegion(new Texture("archer/Idle.png"));
        darkMageRegion = new TextureRegion(new Texture("darkmage/Idle.png"));
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
