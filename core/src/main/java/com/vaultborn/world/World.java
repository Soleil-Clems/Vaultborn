package com.vaultborn.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.players.Warrior;
import com.vaultborn.entities.characters.mobs.Gorgon;
import com.vaultborn.entities.characters.mobs.Mob;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.vaultborn.factories.Factory;
import com.vaultborn.managers.AssetManager;
import com.vaultborn.entities.characters.Character;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class World {

    private final AssetManager assetsManager;
    private final Factory factory;
    private final Warrior player;
    private final List<Mob> mobs = new ArrayList<>();
    private TiledMap map;

    private OrthographicCamera worldCamera;
    private OrthographicCamera uiCamera;
    private TiledMapRenderer tiledMapRenderer;
    private String levelName = "ForestMap/map";
    private Texture background;
    private float tileSize;
    private TiledMapTileLayer collisionLayer;

    private float mapHeightInPixels;

    public World() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        background = new Texture("backgrounds/background.png");
        factory = new Factory();
        assetsManager = new AssetManager();

        map = new TmxMapLoader().load("maps/" + levelName + ".tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map, 1f);
        collisionLayer = (TiledMapTileLayer) map.getLayers().get("Collision");

        int mapWidth = map.getProperties().get("width", Integer.class);
        int mapHeight = map.getProperties().get("height", Integer.class);
        tileSize = map.getProperties().get("tilewidth", Integer.class);
        mapHeightInPixels = mapHeight * tileSize;

        worldCamera = new OrthographicCamera(w, h);
        worldCamera.update();

        uiCamera = new OrthographicCamera(w, h);
        uiCamera.position.set(w / 2f, h / 2f, 0);
        uiCamera.update();


        player = (Warrior) factory.createPlayer("warrior", 350, 580, this);


        mobs.add(factory.createMob("gorgon", 330, 580, this));
        mobs.add(factory.createMob("gorgon", 600, 580, this));
        mobs.add(factory.createMob("gorgon", 800, 580, this));
    }

    public void update(float delta) {
        player.update(delta);

        // Mise à jour des mobs + suppression des morts
        Iterator<Mob> iterator = mobs.iterator();
        while (iterator.hasNext()) {
            Mob mob = iterator.next();
            mob.update(delta);

            // On supprime le mob mort une fois son animation terminée
            if (mob.isDead) {
                if (mob.getAnimation("dead") != null &&
                    mob.getAnimation("dead").isAnimationFinished(mob.stateTime)) {
                    iterator.remove();
                    System.out.println("💀 Mob supprimé du monde !");
                }
            }
        }

        // Caméra centrée sur le joueur
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        float cameraX = player.getPosition().x + 100;
        float cameraY = 730f;
        cameraY = Math.max(cameraY, h / 2f);

        worldCamera.position.set(cameraX, cameraY, 0);
        worldCamera.update();
    }

    public void render(SpriteBatch batch) {
        // Fond
        batch.setProjectionMatrix(uiCamera.combined);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        // Carte
        tiledMapRenderer.setView(worldCamera);
        tiledMapRenderer.render();

        // Joueur et mobs
        batch.setProjectionMatrix(worldCamera.combined);
        batch.begin();
        player.render(batch);
        for (Mob mob : mobs) {
            mob.render(batch);
        }
        batch.end();
    }

    public void dispose() {
        assetsManager.dispose();
        map.dispose();
        background.dispose();
    }

    public Warrior getPlayer() {
        return player;
    }

    public boolean isCellBlocked(float worldX, float worldY) {
        if (collisionLayer == null) return false;

        int x = (int) (worldX / tileSize);
        int y = (int) (worldY / tileSize);

        if (x < 0 || y < 0 || x >= collisionLayer.getWidth() || y >= collisionLayer.getHeight()) return false;

        TiledMapTileLayer.Cell cell = collisionLayer.getCell(x, y);
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked");
    }

    public Character getNearestEnemy(Character attacker, float range) {

        if (attacker == player) {
            Character nearest = null;
            float nearestDist = Float.MAX_VALUE;

            for (Mob mob : mobs) {
                if (mob.isDead) continue;
                float dist = mob.getPosition().dst(player.getPosition());
                if (dist < nearestDist && dist <= range * 64f) {
                    nearest = mob;
                    nearestDist = dist;
                }
            }
            return nearest;
        }


        if (attacker instanceof Mob) {
            if (player != null && !player.isDead) {
                float dist = player.getPosition().dst(attacker.getPosition());
                if (dist <= range * 64f) {
                    return player;
                }
            }
        }

        return null;
    }
}
