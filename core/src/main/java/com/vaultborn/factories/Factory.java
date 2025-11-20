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
import com.vaultborn.entities.stuff.armor.Helmet;
import com.vaultborn.entities.stuff.armor.Breastplate;
import com.vaultborn.entities.stuff.armor.LegPlate;
import com.vaultborn.entities.stuff.armor.GauteletPlate;
import com.vaultborn.entities.stuff.armor.IronFoot;


/**
 * Factory simplifiée pour créer Players, Mobs et GameObjects.
 */
public class Factory {

    public static boolean IS_TEST = false;
    private final AssetManager assetsManager;

    // Only needed for production
    private TextureRegion warriorRegion;
    private TextureRegion darkwarriorRegion;
    private TextureRegion archerRegion;
    private TextureRegion satyrRegion;
    private TextureRegion darkMageRegion;
    private TextureRegion lightMageRegion;
    private TextureRegion sunmageRegion;

    private TextureRegion gorgonRegion;
    private TextureRegion minotaurRegion;
    private TextureRegion tenguRegion;
    private TextureRegion yokaiRegion;

    private TextureRegion swordRegion;
    private TextureRegion HelmetRegion;
    private TextureRegion BreastplateRegion;
    private TextureRegion LegPlateRegion;
    private TextureRegion GauteletPlateRegion;
    private TextureRegion IronFootRegion;
    private TextureRegion specialDoorRegion;

    /**
     * Factory normale avec assets
     */
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
        HelmetRegion = new TextureRegion(new Texture("objects/armor/Helmet.png"));
        BreastplateRegion = new TextureRegion(new Texture("objects/armor/Breastplate.png"));
        LegPlateRegion = new TextureRegion(new Texture("objects/armor/LegPlate.png"));
        GauteletPlateRegion = new TextureRegion(new Texture("objects/armor/GauteletPlate.png"));
        IronFootRegion = new TextureRegion(new Texture("objects/armor/IronFoot.png"));
        specialDoorRegion = new TextureRegion(new Texture("specialdoor/closeddoor.png"));
    }

    /**
     * Factory pour les tests unitaires
     */
    public Factory(boolean test) {
        IS_TEST = test;
        this.assetsManager = null;
    }

    public Player createPlayer(String type, float x, float y, BaseWorld world) throws FactoryException {
        Player player;
        switch (type.toLowerCase()) {
            case "warrior":
                player = IS_TEST ? new Warrior(new Vector2(x, y)) : new Warrior(new Vector2(x, y), warriorRegion);
                break;
            case "darkwarrior":
                player = IS_TEST ? new DarkWarrior(new Vector2(x, y)) : new DarkWarrior(new Vector2(x, y), darkwarriorRegion);
                break;
            case "archer":
                player = IS_TEST ? new Archer(new Vector2(x, y)) : new Archer(new Vector2(x, y), archerRegion);
                break;
            case "satyr":
                player = IS_TEST ? new Satyr(new Vector2(x, y)) : new Satyr(new Vector2(x, y), satyrRegion);
                break;
            case "darkmage":
                player = IS_TEST ? new DarkMage(new Vector2(x, y)) : new DarkMage(new Vector2(x, y), darkMageRegion);
                break;
            case "lightmage":
                player = IS_TEST ? new LightMage(new Vector2(x, y)) : new LightMage(new Vector2(x, y), lightMageRegion);
                break;
            case "sunmage":
                player = IS_TEST ? new SunMage(new Vector2(x, y)) : new SunMage(new Vector2(x, y), sunmageRegion);
                break;
            default:
                throw new FactoryException("Unknown player type: " + type);
        }
        if (!IS_TEST) player.loadAnimations();
        player.setWorld(world);
        return player;
    }

    public Mob createMob(String type, float x, float y, BaseWorld world) throws FactoryException {
        Mob mob;
        switch (type.toLowerCase()) {
            case "gorgon":
                mob = IS_TEST ? new Gorgon(new Vector2(x, y), 1) : new Gorgon(new Vector2(x, y), gorgonRegion, 1);
                break;
            case "minotaur":
                mob = IS_TEST ? new Minotaur(new Vector2(x, y), 1) : new Minotaur(new Vector2(x, y), minotaurRegion, 1);
                break;
            case "tengu":
                mob = IS_TEST ? new Tengu(new Vector2(x, y), 1) : new Tengu(new Vector2(x, y), tenguRegion, 1);
                break;
            case "yokai":
                mob = IS_TEST ? new Yokai(new Vector2(x, y), 1) : new Yokai(new Vector2(x, y), yokaiRegion, 1);
                break;
            default:
                throw new FactoryException("Unknown mob type: " + type);
        }
        if (!IS_TEST) mob.loadAnimations();
        mob.setWorld(world);
        return mob;
    }

    public GameObject createObject(String type, float x, float y, BaseWorld world) throws FactoryException {
        GameObject obj;
        switch (type.toLowerCase()) {
            case "sword":
                obj = IS_TEST ? new Sword(new Vector2(x, y), swordRegion) : new Sword(new Vector2(x, y), swordRegion);
                break;
            case "helmet":
                obj = IS_TEST ? new Helmet(new Vector2(x, y), HelmetRegion, "", "Epic Helmet") : new Helmet(new Vector2(x, y), HelmetRegion, "", "The Helmet");
                break;
            case "breastplate":
                obj = IS_TEST ? new Breastplate(new Vector2(x, y), BreastplateRegion, "", "Epic Chest") : new Breastplate(new Vector2(x, y), BreastplateRegion, "", "Epic Chest");
                break;
            case "legplate":
                obj = IS_TEST ? new LegPlate(new Vector2(x, y), LegPlateRegion, "", "Epic Leg") : new LegPlate(new Vector2(x, y), LegPlateRegion, "", "Epic Leg");
                break;
            case "gauteletplate":
                obj = IS_TEST ? new GauteletPlate(new Vector2(x, y), GauteletPlateRegion, "", "Epic Hand") : new GauteletPlate(new Vector2(x, y), GauteletPlateRegion, "", "Epic Hand");
                break;
            case "ironfoot":
                obj = IS_TEST ? new IronFoot(new Vector2(x, y), IronFootRegion, "", "Epic Boots") : new IronFoot(new Vector2(x, y), IronFootRegion, "", "Epic Boots");
                break;
            case "special_door":
                obj = IS_TEST ? new SpecialDoor(new Vector2(x, y), specialDoorRegion) : new SpecialDoor(new Vector2(x, y), specialDoorRegion);
                break;
            default:
                throw new FactoryException("Unknown object type: " + type);
        }
        if (!IS_TEST) obj.loadAnimations();
        obj.setWorld(world);
        return obj;
    }


    public GameObject createSpecialDoor(String type, float x, float y, BaseWorld worldFrom, BaseWorld worldTo) {
        if (!type.equalsIgnoreCase("special_door")) throw new IllegalArgumentException("Unknown object type: " + type);
        SpecialDoor door = new SpecialDoor(new Vector2(x, y), specialDoorRegion);
        door.loadAnimations();
        door.setWorld(worldFrom);
        door.setTargetWorld(worldTo);
        return door;
    }
}
