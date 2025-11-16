package com.vaultborn.factories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.mobs.Gorgon;
import com.vaultborn.entities.characters.mobs.Minotaur;
import com.vaultborn.entities.characters.mobs.Mob;
import com.vaultborn.entities.characters.players.DarkMage;
import com.vaultborn.entities.characters.players.Player;
import com.vaultborn.entities.characters.players.Satyr;
import com.vaultborn.entities.characters.players.Warrior;
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
    private TextureRegion satyrRegion;
    private TextureRegion gorgonRegion;
    private TextureRegion minotaurRegion;
    private TextureRegion swordRegion;
    private TextureRegion darkMageRegion;
    private TextureRegion specialDoorRegion;

    public Factory() {
        assetsManager = new AssetManager();

        warriorRegion = new TextureRegion(new Texture("warrior/Idle.png"));
        satyrRegion = new TextureRegion(new Texture("satyr/Idle.png"));
        darkMageRegion = new TextureRegion(new Texture("darkmage/Idle.png"));
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
            default:
                throw new IllegalArgumentException("Unknown player type: " + type);
        }
    }

    public Mob createMob(String type, float x, float y, BaseWorld world) {
        switch (type.toLowerCase()) {
            case "gorgon":
                Gorgon gorgon = new Gorgon(new Vector2(x, y), gorgonRegion);
                gorgon.loadAnimations();
                gorgon.setWorld(world);
                return gorgon;
            case "minotaur":
                Minotaur minotaur = new Minotaur(new Vector2(x, y), minotaurRegion);
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
