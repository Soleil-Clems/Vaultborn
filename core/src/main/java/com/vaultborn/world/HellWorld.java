package com.vaultborn.world;

import com.vaultborn.entities.characters.mobs.Gorgon;

public class HellWorld extends BaseWorld {

    public HellWorld() {
        super("HellMap/map", "backgrounds/background_hell.png");
    }

    @Override
    protected void initMobs() {
        mobs.add(factory.createMob("gorgon", 300, 580, this));
        mobs.add(factory.createMob("gorgon", 600, 580, this));
        mobs.add(factory.createMob("gorgon", 900, 580, this));
    }


}
