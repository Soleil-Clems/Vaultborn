package com.vaultborn.world;

import com.vaultborn.entities.characters.mobs.Gorgon;
import com.vaultborn.entities.stuff.trigger.SpecialDoor;

public class HellWorld extends BaseWorld {

    public HellWorld() {
        super("HellMap/maptest", "backgrounds/background_hell.png");
    }

    @Override
    protected void initMobs() {
//        mobs.add(factory.createMob("gorgon", 300, 580, this));
//        mobs.add(factory.createMob("gorgon", 600, 580, this));
//        mobs.add(factory.createMob("gorgon", 900, 580, this));
    }

    @Override
    protected void initObjects() {
        gameObjects.add(factory.createObject("sword", 650, 600, this));
        SpecialDoor door = (SpecialDoor) factory.createSpecialDoor("special_door", 1120, 560, this, factory.createWorld("forest"));
//        door.setParentWorld(this);
//        door.setTargetWorld(factory.createWorld("forest"));
        gameObjects.add(door);

    }


}
