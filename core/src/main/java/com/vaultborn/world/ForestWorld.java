package com.vaultborn.world;

import com.vaultborn.MainGame;
import com.vaultborn.entities.characters.mobs.Gorgon;
import com.vaultborn.entities.characters.mobs.Minotaur;
import com.vaultborn.entities.characters.mobs.Mob;
import com.vaultborn.entities.stuff.GameObject;
import com.vaultborn.entities.stuff.trigger.SpecialDoor;
import com.vaultborn.factories.FactoryException;

public class ForestWorld extends BaseWorld {

    public ForestWorld(MainGame game) throws FactoryException {
        super(game,"ForestMap/map", "backgrounds/background_forest.png");
        this.game = game;
        setBoss(Minotaur.class);
        levelName = "forest";
        this.spawnX = 250;
        this.spawnY = 800;
    }

    @Override
    protected void initMobs() throws FactoryException {
        Mob boss = factory.createMob("minotaur", 11500, 800, this, 1);
        boss.setBoss();
        mobs.add(boss);
//        mobs.add(factory.createMob("gorgon", 500, 580, this));
//        mobs.add(factory.createMob("gorgon", 550, 580, this));
//        mobs.add(factory.createMob("gorgon", 600, 580, this));
        mobs.add(factory.createMob("gorgon", 700, 580, this,1));
        mobs.add(factory.createMob("gorgon", 1000, 580, this, 1));
        mobs.add(factory.createMob("gorgon", 1300, 580, this, 1));
        mobs.add(factory.createMob("gorgon", 1500, 580, this, 1));
        mobs.add(factory.createMob("gorgon", 1600, 580, this, 1));
        mobs.add(factory.createMob("gorgon", 1700, 580, this, 1));
        mobs.add(factory.createMob("gorgon", 2200, 580, this, 1));
        mobs.add(factory.createMob("gorgon", 2500, 580, this, 1));
        mobs.add(factory.createMob("gorgon", 2800, 580, this, 1));
        mobs.add(factory.createMob("gorgon", 2900, 580, this, 1));
        mobs.add(factory.createMob("gorgon", 3100, 580, this, 1));
        mobs.add(factory.createMob("gorgon", 3300, 580, this, 1));
        mobs.add(factory.createMob("gorgon", 3500, 580, this, 1));
        mobs.add(factory.createMob("gorgon", 3700, 580, this, 1));
        mobs.add(factory.createMob("gorgon", 3900, 580, this, 1));
        mobs.add(factory.createMob("gorgon", 4200, 580, this, 1));
        mobs.add(factory.createMob("gorgon", 6300, 580, this, 1));
        mobs.add(factory.createMob("gorgon", 6600, 580, this, 1));
        mobs.add(factory.createMob("gorgon", 7600, 580, this, 1));
        mobs.add(factory.createMob("gorgon", 7900, 580, this, 1));



//        mobs.add(factory.createMob("yokai", 450, 580, this));
//        mobs.add(factory.createMob("tengu", 650, 580, this));
    }

    @Override
    protected void initObjects() throws FactoryException {
        gameObjects.add(factory.createObject("ironfoot", 1500, 200, null,1));
        gameObjects.add(factory.createObject("sword", 1000, 200, null,6));
        gameObjects.add(factory.createObject("helmet", 3550, 400, null,1));
        gameObjects.add(factory.createObject("sword", 4550, 400, null,10));
        gameObjects.add(factory.createObject("breastplate", 5950, 600, null,4));
        gameObjects.add(factory.createObject("legplate", 8000, 600, null,1));
        gameObjects.add(factory.createObject("gauteletplate", 12000, 800, null,1));

        SpecialDoor door = (SpecialDoor) factory.createSpecialDoor("special_door", 12100, 700, this, game.hellWorld);
        door.setParentWorld(this);
        door.setSpawnPosition(550, 3800); // Position d'arrivée dans HellWorld
        gameObjects.add(door);
    }


    public void linkWorlds() {
        for (GameObject obj : gameObjects) {
            if (obj instanceof SpecialDoor) {
                SpecialDoor door = (SpecialDoor) obj;
                door.setTargetWorld(game.hellWorld);
            }
        }
    }
}
