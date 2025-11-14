package com.vaultborn.world;

import com.vaultborn.entities.characters.mobs.Gorgon;
import com.vaultborn.entities.characters.players.Warrior;
import com.vaultborn.entities.stuff.trigger.SpecialDoor;

public class ForestWorld extends BaseWorld {

    public ForestWorld() {
        super("LavaMap/map1", "backgrounds/background_hell.png");
    }

    @Override
    protected void initPlayer() {
        player = (Warrior) factory.createPlayer("warrior", 450, 800, this);
    }

    @Override
    protected void initMobs() {
        mobs.add(factory.createMob("gorgon", 730, 580, this));
        mobs.add(factory.createMob("gorgon", 600, 580, this));
        mobs.add(factory.createMob("gorgon", 850, 580, this));
    }

    @Override
    protected void initObjects() {
        gameObjects.add(factory.createObject("sword", 750, 400, this));

//        SpecialDoor door = (SpecialDoor) factory.createSpecialDoor("special_door", 460, 560, this, factory.createWorld("hell"));
//        door.setParentWorld(this);
//        door.setTargetWorld(factory.createWorld("hell"));
//        gameObjects.add(door);
    }


}
