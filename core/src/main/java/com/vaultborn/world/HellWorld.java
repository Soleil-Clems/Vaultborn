package com.vaultborn.world;

import com.vaultborn.entities.characters.mobs.Gorgon;
import com.vaultborn.entities.characters.players.Warrior;
import com.vaultborn.entities.stuff.trigger.SpecialDoor;

public class HellWorld extends BaseWorld {

    public HellWorld() {
        super("HellMap/map1", "backgrounds/background_hell.png");
    }

    @Override
    protected void initPlayer() {
        player = (Warrior) factory.createPlayer("warrior", 500, 3800, this);
    }

    @Override
    protected void initMobs() {
//        mobs.add(factory.createMob("gorgon", 300, 580, this));
//        mobs.add(factory.createMob("gorgon", 600, 580, this));
//        mobs.add(factory.createMob("gorgon", 900, 580, this));
    }

    @Override
    protected void initObjects() {
        gameObjects.add(factory.createObject("sword", 650, 600, this));
        SpecialDoor door = (SpecialDoor) factory.createSpecialDoor("special_door", 1120, 560, this, factory.createWorld("forest"));
//        door.setParentWorld(this);
//        door.setTargetWorld(factory.createWorld("forest"));
        gameObjects.add(door);

    }

    @Override
    protected void updateCamera() {
        float delta = com.badlogic.gdx.Gdx.graphics.getDeltaTime(); // ← tu avais oublié ça !

        // --- Paramètres de suivi caméra ---
        float CamOffsetY = 220f;
        float CamOffsetX = 50f;
        float CamSpeedFast = 5f;
        float CamSpeedSlow = 0.2f;
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
