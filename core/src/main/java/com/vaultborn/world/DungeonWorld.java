package com.vaultborn.world;

import com.vaultborn.MainGame;
import com.vaultborn.entities.characters.mobs.Mob;
import com.vaultborn.entities.characters.mobs.Tengu;
import com.vaultborn.entities.characters.mobs.Yokai;
import com.vaultborn.entities.stuff.GameObject;
import com.vaultborn.entities.stuff.trigger.SpecialDoor;
import com.vaultborn.factories.FactoryException;

public class DungeonWorld extends BaseWorld{
    public DungeonWorld(MainGame  game) throws FactoryException {
        super(game,"DungeonMap/map", "backgrounds/background_dungeon.png", "sounds/dungeon.wav");
        setBoss(Yokai.class);
        levelName = "dungeon";

    }

    @Override
    protected void initMobs() throws FactoryException {
        mobs.add(factory.createMob("gorgon", 880, 200, this, 1));
        mobs.add(factory.createMob("gorgon", 1200, 200, this, 1));
        mobs.add(factory.createMob("minotaur", 1400, 200, this, 1));
        mobs.add(factory.createMob("minotaur", 1600, 200, this, 1));
        mobs.add(factory.createMob("gorgon", 2000, 600, this, 1));
        mobs.add(factory.createMob("gorgon", 2200, 600, this, 1));
        mobs.add(factory.createMob("minotaur", 2500, 600, this, 1));
        mobs.add(factory.createMob("minotaur", 3500, 600, this, 1));
        mobs.add(factory.createMob("minotaur", 3500, 1000, this, 1));
        mobs.add(factory.createMob("gorgon", 4000, 1000, this, 1));

        mobs.add(factory.createMob("minotaur", 2500, 1700, this, 1));
        mobs.add(factory.createMob("gorgon", 3000, 1700, this, 1));
        mobs.add(factory.createMob("minotaur", 3500, 1700, this, 1));
        mobs.add(factory.createMob("gorgon", 4000, 1700, this, 1));
        mobs.add(factory.createMob("gorgon", 4500, 1700, this, 1));

        mobs.add(factory.createMob("minotaur", 2500, 2100, this, 1));
        mobs.add(factory.createMob("gorgon", 3000, 2100, this, 1));
        mobs.add(factory.createMob("minotaur", 3500, 2100, this, 1));
        mobs.add(factory.createMob("gorgon", 4000, 2100, this, 1));
        mobs.add(factory.createMob("gorgon", 4500, 2100, this, 1));

        mobs.add(factory.createMob("minotaur", 2000, 3450, this, 1));
        mobs.add(factory.createMob("minotaur", 2200, 3450, this, 1));
        mobs.add(factory.createMob("gorgon", 2400, 3450, this, 1));
        mobs.add(factory.createMob("minotaur", 2600, 3450, this, 1));
        mobs.add(factory.createMob("gorgon", 2900, 3450, this, 1));
        mobs.add(factory.createMob("minotaur", 3500, 3450, this, 1));

        Mob boss = factory.createMob("yokai", 1800, 3450, this, 1);
        boss.setBoss();
        mobs.add(boss);

    }

    @Override
    protected void initObjects() {
        SpecialDoor door = (SpecialDoor) factory.createSpecialDoor("special_door", 1770, 3450, this, game.forestWorld);
        door.setParentWorld(this);
        door.setSpawnPosition(550, 3800);
        gameObjects.add(door);
    }

    @Override
    protected void initPlayer() {
        player = this.getPlayer();
    }

    /**
     * Lie les portes spéciales de HellWorld à leur monde cible.
     * Ici, toutes les portes sont reliées à ForestWorld.
     */
    public void linkWorlds() {
        for (GameObject obj : gameObjects) {
            if (obj instanceof SpecialDoor) {
                SpecialDoor door = (SpecialDoor) obj;
                door.setTargetWorld(game.hellWorld);
            }
        }
    }
}
