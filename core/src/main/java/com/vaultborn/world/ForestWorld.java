package com.vaultborn.world;

import com.vaultborn.MainGame;
import com.vaultborn.entities.characters.mobs.Gorgon;
import com.vaultborn.entities.characters.players.Archer;
import com.vaultborn.entities.characters.players.DarkMage;
import com.vaultborn.entities.characters.players.LightMage;
import com.vaultborn.entities.characters.players.Warrior;
import com.vaultborn.entities.stuff.GameObject;
import com.vaultborn.entities.stuff.trigger.SpecialDoor;

public class ForestWorld extends BaseWorld {

    public ForestWorld(MainGame game) {
        super(game,"LavaMap/map", "backgrounds/background_hell.png");
        this.game = game;
        setBoss(Gorgon.class);
    }



    @Override
    protected void initMobs() {
        mobs.add(factory.createMob("gorgon", 730, 580, this));
        mobs.add(factory.createMob("minotaur", 600, 580, this));
        mobs.add(factory.createMob("yokai", 450, 580, this));
        mobs.add(factory.createMob("tengu", 650, 580, this));
    }

    @Override
    protected void initObjects() {
        gameObjects.add(factory.createObject("sword", 350, 800, null));

        SpecialDoor door = (SpecialDoor) factory.createSpecialDoor("special_door", 460, 560, this, game.hellWorld);
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
