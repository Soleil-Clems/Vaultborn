package com.vaultborn.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.Character;
import com.vaultborn.entities.characters.mobs.Mob;
import com.vaultborn.entities.characters.players.Warrior;
import com.vaultborn.entities.stuff.GameObject;
import com.vaultborn.factories.Factory;
import com.vaultborn.managers.AssetManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class BaseWorld {

    protected final AssetManager assetsManager;
    protected final Factory factory;
    protected Warrior player;
    protected List<Mob> mobs = new ArrayList<>();
    protected List<GameObject> gameObjects = new ArrayList<>();

    protected TiledMap map;
    protected TiledMapTileLayer collisionLayer;
    protected OrthogonalTiledMapRenderer tiledMapRenderer;
    protected OrthographicCamera worldCamera, uiCamera;

    protected Texture background;
    protected float tileSize;
    protected float mapHeightInPixels;

    protected float offsetX;
    protected float offsetY;

    protected String levelName;

    public BaseWorld(String levelName, String backgroundPath) {
        this.levelName = levelName;
        this.assetsManager = new AssetManager();
        this.factory = new Factory();
        this.background = new Texture(backgroundPath);
        offsetX = -30f;
        offsetY = -10f;
        loadMap();
        initCameras();
        initPlayer();
        initMobs();
        initObjects();
    }

    public OrthographicCamera getUiCamera() {
        return uiCamera;
    }

    protected void loadMap() {
        map = new TmxMapLoader().load("maps/" + levelName + ".tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map, 1f);

        collisionLayer = (TiledMapTileLayer) map.getLayers().get("Collision");

        int mapWidth = map.getProperties().get("width", Integer.class);
        int mapHeight = map.getProperties().get("height", Integer.class);
        tileSize = map.getProperties().get("tilewidth", Integer.class);
        mapHeightInPixels = mapHeight * tileSize;
    }

    protected void initCameras() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        worldCamera = new OrthographicCamera(w, h);
        worldCamera.update();

        uiCamera = new OrthographicCamera(w, h);
        uiCamera.position.set(w / 2f, h / 2f, 0);
        uiCamera.update();
    }

    protected void initPlayer() {
        player = (Warrior) factory.createPlayer("warrior", 350, 800, this);
    }

    /** 💡 Méthode abstraite à implémenter dans chaque sous-monde */
    protected abstract void initMobs();
    /** 💡 Méthode abstraite à implémenter dans chaque sous-monde */
    protected abstract void initObjects();

    public void update(float delta) {
        player.update(delta);

        Iterator<Mob> it = mobs.iterator();
        while (it.hasNext()) {
            Mob mob = it.next();
            mob.update(delta);
            if (mob.isDead && mob.getAnimation("dead") != null &&
                mob.getAnimation("dead").isAnimationFinished(mob.stateTime)) {
                it.remove();
            }
        }

        Iterator<GameObject> objectIterator = gameObjects.iterator();
        while (objectIterator.hasNext()) {
            GameObject obj = objectIterator.next();

            float px = player.getPosition().x;
            float py = player.getPosition().y;
            float ox = obj.getPosition().x;
            float oy = obj.getPosition().y;

            float distance = Vector2.dst(px, py, ox, oy);

            if (distance < 40f) {
                obj.pickUp(player);
                objectIterator.remove();
            }
        }

        updateCamera();
    }

    protected void updateCamera() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        float cameraX = player.getPosition().x + 100;
        float cameraY = 730f;
        cameraY = Math.max(cameraY, h / 2f);

        worldCamera.position.set(cameraX, cameraY, 0);
        worldCamera.update();
    }

    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(uiCamera.combined);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        tiledMapRenderer.setView(worldCamera);
        tiledMapRenderer.render();

        batch.setProjectionMatrix(worldCamera.combined);
        batch.begin();
        player.render(batch);
        for (Mob mob : mobs) mob.render(batch);

        for (GameObject obj : gameObjects) {
            obj.render(batch);
            //System.out.println("arm");
        }
        batch.end();
    }

    public boolean isCellBlocked(float worldX, float worldY) {
        if (collisionLayer == null) return false;
        float adjustedX = worldX - offsetX;
        float adjustedY = worldY - offsetY;
        int x = (int) (adjustedX / tileSize);
        int y = (int) (adjustedY / tileSize);
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

        if (attacker instanceof Mob && !player.isDead) {
            float dist = player.getPosition().dst(attacker.getPosition());
            if (dist <= range * 64f) {
                return player;
            }
        }
        return null;
    }

    public Warrior getPlayer() { return player; }

    public void dispose() {
        assetsManager.dispose();
        map.dispose();
        background.dispose();
    }
}
