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
    }

    @Override
    protected void initPlayer() {
//        player = (Warrior) factory.createPlayer("warrior", 450, 800, this);
//        player = (DarkMage) factory.createPlayer("darkMage", 450, 800, this);
//        player = (LightMage) factory.createPlayer("lightMage", 450, 800, this);
        player = (Archer) factory.createPlayer("archer", 450, 800, this);
    }

    @Override
    protected void initMobs() {
        mobs.add(factory.createMob("gorgon", 730, 580, this));
        mobs.add(factory.createMob("minotaur", 600, 580, this));
    }

    @Override
    protected void initObjects() {
        gameObjects.add(factory.createObject("sword", 750, 400, null));

        SpecialDoor door = (SpecialDoor) factory.createSpecialDoor("special_door", 460, 560, this, game.hellWorld);
        door.setParentWorld(this);
//        door.setTargetWorld(game.hellWorld);
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
