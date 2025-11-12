package com.vaultborn.world;

public class DungeonWorld extends BaseWorld{
    public DungeonWorld() {
        super("DesertMap/map", "backgrounds/background_desert.png");
    }

    @Override
    protected void initMobs() {
        mobs.add(factory.createMob("gorgon", 400, 580, this));
        mobs.add(factory.createMob("gorgon", 900, 580, this));
    }
}
