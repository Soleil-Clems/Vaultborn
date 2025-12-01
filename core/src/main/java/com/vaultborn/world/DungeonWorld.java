package com.vaultborn.world;

import com.vaultborn.MainGame;
import com.vaultborn.factories.FactoryException;

public class DungeonWorld extends BaseWorld{
    public DungeonWorld(MainGame  game) throws FactoryException {
        super(game,"DungeonMap/map", "backgrounds/background_dungeon.jpg");
    }

    @Override
    protected void initMobs() throws FactoryException {
        mobs.add(factory.createMob("gorgon", 1200, 400, this, 1));
        mobs.add(factory.createMob("gorgon", 880, 400, this, 1));
    }

    @Override
    protected void initObjects() {}
}
