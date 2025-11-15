package com.vaultborn.world;

import com.vaultborn.entities.characters.mobs.Gorgon;
import com.vaultborn.entities.characters.players.Warrior;
import com.vaultborn.entities.stuff.trigger.SpecialDoor;

public class HellWorld extends BaseWorld {

    public HellWorld() {
        super("HellMap/map", "backgrounds/background_hell.png");
    }

    @Override
    protected void initPlayer() {
        player = (Warrior) factory.createPlayer("warrior", 500, 3800, this);
    }

    @Override
    protected void initMobs() {
        mobs.add(factory.createMob("gorgon", 620, 3800, this));
        mobs.add(factory.createMob("gorgon", 760, 3800, this));
        mobs.add(factory.createMob("gorgon", 710, 3600, this));
        mobs.add(factory.createMob("gorgon", 1000, 3800, this));

        mobs.add(factory.createMob("gorgon", 1900, 3800, this));
        mobs.add(factory.createMob("gorgon", 1070, 3600, this));
        mobs.add(factory.createMob("gorgon", 2000, 3600, this));

        mobs.add(factory.createMob("gorgon", 2900, 3600, this));
        mobs.add(factory.createMob("gorgon", 2950, 3600, this));
        mobs.add(factory.createMob("gorgon", 3100, 3600, this));
        mobs.add(factory.createMob("gorgon", 3300, 3600, this));
        mobs.add(factory.createMob("gorgon", 3400, 3600, this));

        mobs.add(factory.createMob("gorgon", 2900, 2800, this));
        mobs.add(factory.createMob("gorgon", 3000, 2800, this));
        mobs.add(factory.createMob("gorgon", 3100, 2800, this));
        mobs.add(factory.createMob("gorgon", 3300, 2800, this));
        mobs.add(factory.createMob("gorgon", 3700, 2800, this));

        mobs.add(factory.createMob("gorgon", 100, 2500, this));
        mobs.add(factory.createMob("gorgon", 200, 2500, this));
        mobs.add(factory.createMob("gorgon", 300, 2500, this));
        mobs.add(factory.createMob("gorgon", 800, 2800, this));
        mobs.add(factory.createMob("gorgon", 900, 2800, this));
        mobs.add(factory.createMob("gorgon", 1000, 2800, this));
        mobs.add(factory.createMob("gorgon", 1100, 2800, this));
        mobs.add(factory.createMob("gorgon", 1300, 2800, this));


        mobs.add(factory.createMob("gorgon", 1100, 2300, this));
        mobs.add(factory.createMob("gorgon", 1200, 2300, this));
        mobs.add(factory.createMob("gorgon", 1300, 2300, this));
        mobs.add(factory.createMob("gorgon", 1400, 2300, this));
        mobs.add(factory.createMob("gorgon", 1500, 2300, this));
        mobs.add(factory.createMob("gorgon", 1600, 2300, this));
        mobs.add(factory.createMob("gorgon", 1700, 2300, this));
        mobs.add(factory.createMob("gorgon", 1800, 2300, this));
        mobs.add(factory.createMob("gorgon", 1900, 2300, this));
        mobs.add(factory.createMob("gorgon", 2900, 2300, this));
        mobs.add(factory.createMob("gorgon", 2950, 2300, this));
        mobs.add(factory.createMob("gorgon", 3100, 2300, this));
        mobs.add(factory.createMob("gorgon", 3300, 2300, this));
        mobs.add(factory.createMob("gorgon", 3400, 2300, this));


        mobs.add(factory.createMob("gorgon", 1100, 1900, this));
        mobs.add(factory.createMob("gorgon", 1200, 1900, this));
        mobs.add(factory.createMob("gorgon", 1300, 1900, this));
        mobs.add(factory.createMob("gorgon", 1400, 1900, this));

        mobs.add(factory.createMob("gorgon", 420, 800, this));
        mobs.add(factory.createMob("gorgon", 520, 800, this));
        mobs.add(factory.createMob("gorgon", 620, 800, this));
        mobs.add(factory.createMob("gorgon", 760, 800, this));
        mobs.add(factory.createMob("gorgon", 810, 800, this));
        mobs.add(factory.createMob("gorgon", 1000, 800, this));

        mobs.add(factory.createMob("gorgon", 420, 100, this)); // le boss
        mobs.add(factory.createMob("gorgon", 520, 100, this));
        mobs.add(factory.createMob("gorgon", 620, 100, this));
        mobs.add(factory.createMob("gorgon", 760, 100, this));
        mobs.add(factory.createMob("gorgon", 810, 100, this));
        mobs.add(factory.createMob("gorgon", 1000, 100, this));

        mobs.add(factory.createMob("gorgon", 1500, 100, this));
        mobs.add(factory.createMob("gorgon", 1800, 100, this));
        mobs.add(factory.createMob("gorgon", 2100, 100, this));
        mobs.add(factory.createMob("gorgon", 2300, 100, this));
        mobs.add(factory.createMob("gorgon", 2500, 100, this));
        mobs.add(factory.createMob("gorgon", 3000, 100, this));

        mobs.add(factory.createMob("gorgon", 3100, 100, this));
        mobs.add(factory.createMob("gorgon", 3200, 100, this));
        mobs.add(factory.createMob("gorgon", 3400, 100, this));
        mobs.add(factory.createMob("gorgon", 3500, 100, this));
        mobs.add(factory.createMob("gorgon", 3800, 100, this));
        mobs.add(factory.createMob("gorgon", 3900, 100, this));

    }

    @Override
    protected void initObjects() {
        gameObjects.add(factory.createObject("sword", 3200, 4200, this));
        gameObjects.add(factory.createObject("sword", 4100, 2500, this));
        gameObjects.add(factory.createObject("sword", 3050, 1750, this));
        gameObjects.add(factory.createObject("sword", 450, 1400, this));
        gameObjects.add(factory.createObject("sword", 150, 1200, this));
        gameObjects.add(factory.createObject("sword", 3150, 1050, this));
//        SpecialDoor door = (SpecialDoor) factory.createSpecialDoor("special_door", 1120, 560, this, factory.createWorld("forest"));
//        door.setParentWorld(this);
//        door.setTargetWorld(factory.createWorld("forest"));
//        gameObjects.add(door);

    }

    @Override
    protected void updateCamera() {
        float delta = com.badlogic.gdx.Gdx.graphics.getDeltaTime(); // ← tu avais oublié ça !

        // --- Paramètres de suivi caméra ---
        float CamOffsetY = 220f;
        float CamOffsetX = 50f;
        float CamSpeedFast = 5f;
        float CamSpeedSlow = 2f;
        float DeadZoneYUp = 100f;
        float DeadZoneX = 170f;

        // --- Position actuelle de la caméra ---
        float camX = worldCamera.position.x;
        float camY = worldCamera.position.y;

        // --- Cible (le joueur) ---
        float targetX = player.getPosition().x + CamOffsetX;
        float targetY = player.getPosition().y + CamOffsetY;

        // --- Différences ---
        float diffX = targetX - camX;
        float diffY = targetY - camY;

        // --- Mouvement horizontal ---
        if (Math.abs(diffX) > DeadZoneX) {
            camX += (diffX - Math.signum(diffX) * DeadZoneX) * CamSpeedFast * delta;
        } else {
            camX += diffX * CamSpeedSlow * delta;
        }

        // --- Mouvement vertical avec inertie ---
        float diffPosY = oldPosY - player.getPosition().y;
        oldPosY = player.getPosition().y;

        boolean isFalling = diffPosY > 0;

        if (!isFalling) {
            if (diffY > DeadZoneYUp + 10) {
                camY += (diffY - Math.signum(diffY) * DeadZoneYUp) * CamSpeedFast * delta;
            } else {
                camY += diffY * CamSpeedSlow * delta;
            }
            timer = 0;
        } else {
            timer += delta;
            if (timer > 0.4f && timer < 1.8f) {
                camY += diffY * CamSpeedFast * delta;
            } else if (timer >= 1.8f) {
                camY += diffY * CamSpeedFast * delta - 30f;
            } else {
                camY += diffY * CamSpeedSlow * delta;
            }
        }

        worldCamera.position.x = camX;
        worldCamera.position.y = camY;
        worldCamera.update();
    }


}
