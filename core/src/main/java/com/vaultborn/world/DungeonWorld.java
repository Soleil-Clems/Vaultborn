package com.vaultborn.world;

import com.vaultborn.MainGame;
import com.vaultborn.factories.FactoryException;

public class DungeonWorld extends BaseWorld{
    public DungeonWorld(MainGame  game) throws FactoryException {
        super(game,"DesertMap/map", "backgrounds/background_desert.png");
    }

    @Override
    protected void initMobs() throws FactoryException {
        mobs.add(factory.createMob("gorgon", 400, 580, this));
        mobs.add(factory.createMob("gorgon", 900, 580, this));
    }

    @Override
    protected void initObjects() {}
}
