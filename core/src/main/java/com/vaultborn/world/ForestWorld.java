package com.vaultborn.world;

import com.vaultborn.entities.characters.mobs.Gorgon;

public class ForestWorld extends BaseWorld {

    public ForestWorld() {
        super("HellMap/map1", "backgrounds/background_hell.png");
    }

    @Override
    protected void initMobs() {
        mobs.add(factory.createMob("gorgon", 330, 580, this));
        mobs.add(factory.createMob("gorgon", 600, 580, this));
        mobs.add(factory.createMob("gorgon", 850, 580, this));
    }

    @Override
    protected void initObjects() {}
}
