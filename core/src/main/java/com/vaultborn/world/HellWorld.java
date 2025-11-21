package com.vaultborn.world;

import com.vaultborn.MainGame;
import com.vaultborn.entities.characters.mobs.Gorgon;
import com.vaultborn.entities.characters.mobs.Minotaur;
import com.vaultborn.entities.characters.mobs.Mob;
import com.vaultborn.entities.characters.mobs.Tengu;
import com.vaultborn.entities.characters.players.*;
import com.vaultborn.entities.stuff.GameObject;
import com.vaultborn.entities.stuff.trigger.SpecialDoor;
import com.vaultborn.factories.FactoryException;

/**
 * Classe représentant le monde "HellWorld" du jeu.
 *
 * <p>
 * HellWorld est un monde spécifique hérité de {@link BaseWorld}. Il configure :
 * <ul>
 *   <li>La carte du monde ("HellMap/map") et le fond ("backgrounds/hell.png")</li>
 *   <li>Le joueur, les mobs et les objets spécifiques à ce niveau</li>
 *   <li>Le boss du monde (ici {@link com.vaultborn.entities.characters.mobs.Tengu})</li>
 *   <li>Les positions de spawn et de sortie du monde</li>
 *   <li>Les interactions avec les portes spéciales {@link SpecialDoor}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Ce monde contient de nombreux mobs (Gorgons, Minotaures) répartis sur la carte,
 * ainsi que plusieurs armes (épées) et au moins une porte spéciale menant à {@link ForestWorld}.
 * </p>
 */

public class HellWorld extends BaseWorld {

    /**
     * Constructeur de HellWorld.
     *
     * @param game Référence au jeu principal
     * @throws FactoryException si la création d'un mob ou objet échoue
     */
    public HellWorld(MainGame game) throws FactoryException {
        super(game, "HellMap/map", "backgrounds/hell.png");
        this.game = game;
        setBoss(Tengu.class);
        levelName = "hell";
        this.spawnX = 550;
        this.spawnY = 3800;
        this.isEnd = true;

    }

    @Override
    protected void initPlayer() {

        player = this.getPlayer();
    }

    @Override
    protected void initMobs() throws FactoryException {
        mobs.add(factory.createMob("gorgon", 1000, 3800, this, 1));

        mobs.add(factory.createMob("minotaur", 1900, 3800, this, 1));
        mobs.add(factory.createMob("minotaur", 1070, 3600, this, 1));
        mobs.add(factory.createMob("minotaur", 2000, 3600, this, 1));

        mobs.add(factory.createMob("gorgon", 2900, 3600, this,1));
        mobs.add(factory.createMob("minotaur", 2950, 3600, this, 1));
        mobs.add(factory.createMob("gorgon", 3100, 3600, this, 1));
        mobs.add(factory.createMob("minotaur", 3300, 3600, this,1));
        mobs.add(factory.createMob("gorgon", 3400, 3600, this,1));

        mobs.add(factory.createMob("gorgon", 2900, 2800, this, 1));
        mobs.add(factory.createMob("gorgon", 3000, 2800, this, 1));
        mobs.add(factory.createMob("minotaur", 3100, 2800, this, 1));
        mobs.add(factory.createMob("minotaur", 3300, 2800, this, 1));
        mobs.add(factory.createMob("minotaur", 3700, 2800, this, 1));

        mobs.add(factory.createMob("gorgon", 100, 2500, this, 1));
        mobs.add(factory.createMob("minotaur", 200, 2500, this, 1));
        mobs.add(factory.createMob("minotaur", 300, 2500, this, 1));
        mobs.add(factory.createMob("minotaur", 800, 2800, this, 1));
        mobs.add(factory.createMob("minotaur", 900, 2800, this, 1));
        mobs.add(factory.createMob("gorgon", 1000, 2800, this, 1));
        mobs.add(factory.createMob("gorgon", 1100, 2800, this, 1));
        mobs.add(factory.createMob("gorgon", 1300, 2800, this, 1));


        mobs.add(factory.createMob("gorgon", 1100, 2300, this, 1));
        mobs.add(factory.createMob("gorgon", 1200, 2300, this, 1));
        mobs.add(factory.createMob("minotaur", 1300, 2300, this, 1));
        mobs.add(factory.createMob("minotaur", 1400, 2300, this, 1));
        mobs.add(factory.createMob("minotaur", 1500, 2300, this, 1));
        mobs.add(factory.createMob("minotaur", 1600, 2300, this, 1));
        mobs.add(factory.createMob("minotaur", 1700, 2300, this, 1));
        mobs.add(factory.createMob("minotaur", 1800, 2300, this, 1));
        mobs.add(factory.createMob("minotaur", 1900, 2300, this, 1));
        mobs.add(factory.createMob("minotaur", 2900, 2300, this, 1));
        mobs.add(factory.createMob("minotaur", 2950, 2300, this, 1));
        mobs.add(factory.createMob("gorgon", 3100, 2300, this, 1));
        mobs.add(factory.createMob("gorgon", 3300, 2300, this, 1));
        mobs.add(factory.createMob("gorgon", 3400, 2300, this, 1));


        mobs.add(factory.createMob("gorgon", 1100, 1900, this, 1));
        mobs.add(factory.createMob("gorgon", 1200, 1900, this, 1));
        mobs.add(factory.createMob("gorgon", 1300, 1900, this, 1));
        mobs.add(factory.createMob("gorgon", 1400, 1900, this, 1));

        mobs.add(factory.createMob("gorgon", 420, 800, this, 1));
        mobs.add(factory.createMob("gorgon", 520, 800, this, 1));
        mobs.add(factory.createMob("gorgon", 620, 800, this, 1));
        mobs.add(factory.createMob("gorgon", 760, 800, this, 1));
        mobs.add(factory.createMob("gorgon", 810, 800, this, 1));
        mobs.add(factory.createMob("gorgon", 1000, 800, this, 1));

        mobs.add(factory.createMob("gorgon", 420, 100, this, 1)); // le boss
        mobs.add(factory.createMob("gorgon", 520, 100, this, 1));
        mobs.add(factory.createMob("gorgon", 620, 100, this, 1));
        mobs.add(factory.createMob("gorgon", 760, 100, this, 1));
        mobs.add(factory.createMob("gorgon", 810, 100, this, 1));
        mobs.add(factory.createMob("gorgon", 1000, 100, this, 1));

        mobs.add(factory.createMob("minotaur", 1500, 100, this, 1));
        mobs.add(factory.createMob("minotaur", 1800, 100, this, 1));
        mobs.add(factory.createMob("minotaur", 2100, 100, this, 1));
        mobs.add(factory.createMob("minotaur", 2300, 100, this, 1));
        mobs.add(factory.createMob("minotaur", 2500, 100, this, 1));
        mobs.add(factory.createMob("minotaur", 3000, 100, this, 1));

        mobs.add(factory.createMob("gorgon", 3100, 100, this, 1));
        mobs.add(factory.createMob("gorgon", 3200, 100, this, 1));
        mobs.add(factory.createMob("minotaur", 3400, 100, this, 1));
        mobs.add(factory.createMob("minotaur", 3500, 100, this, 1));
        mobs.add(factory.createMob("gorgon", 3800, 100, this, 1));
        Mob boss = factory.createMob("tengu", 3900, 100, this, 1);
        boss.setBoss();
        mobs.add(boss);

    }

    @Override
    protected void initObjects() throws FactoryException {
        gameObjects.add(factory.createObject("sword", 3100, 4200, this,20));
        gameObjects.add(factory.createObject("helmet", 4100, 2500, this,10));
        gameObjects.add(factory.createObject("sword", 3050, 1750, this,10));
        gameObjects.add(factory.createObject("ironfoot", 450, 1400, this,10));
        gameObjects.add(factory.createObject("legplate", 150, 1200, this,10));
        gameObjects.add(factory.createObject("breastplate", 3150, 1050, this,10));
        SpecialDoor door = (SpecialDoor) factory.createSpecialDoor("special_door", 80, 50, this, game.forestWorld);
        door.setParentWorld(this);
        door.setSpawnPosition(500, 580);
       gameObjects.add(door);

    }



    @Override
    protected void updateCamera() {
        float delta = com.badlogic.gdx.Gdx.graphics.getDeltaTime();

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

    /**
     * Lie les portes spéciales de HellWorld à leur monde cible.
     * Ici, toutes les portes sont reliées à ForestWorld.
     */
    public void linkWorlds() {
        for (GameObject obj : gameObjects) {
            if (obj instanceof SpecialDoor) {
                SpecialDoor door = (SpecialDoor) obj;
                door.setTargetWorld(game.forestWorld);
            }
        }
    }

}
