package com.vaultborn.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.Character;
import com.vaultborn.entities.characters.mobs.Mob;
import com.vaultborn.entities.characters.players.Warrior;
import com.vaultborn.entities.stuff.GameObject;
import com.vaultborn.entities.stuff.trigger.SpecialDoor;
import com.vaultborn.factories.Factory;
import com.vaultborn.managers.AssetManager;
import com.vaultborn.screens.GameScreen;

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

    protected String levelName;
    protected GameScreen screen;
    protected float oldPosY = 0f;
    protected float timer = 0f;

    public BaseWorld(String levelName, String backgroundPath) {
        this.levelName = levelName;
        this.assetsManager = new AssetManager();
        this.factory = new Factory();
        this.background = new Texture(backgroundPath);

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

        System.out.println("=== MAP INFO ===");
        System.out.println("Tile size: " + tileSize);
        System.out.println("Map dimensions: " + mapWidth + "x" + mapHeight);
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

    protected abstract void initMobs();
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

            if (distance < 70f && !(obj instanceof SpecialDoor)) {
                obj.pickUp(player);
                objectIterator.remove();
            }

            if (obj instanceof SpecialDoor) {
                SpecialDoor door = (SpecialDoor) obj;

                if (mobs.isEmpty()) {
                    door.setAnimation("open");
                }

                if (mobs.isEmpty() && door.getTriggerZone().overlaps(player.getHitbox()) && door.getTargetWorld() != null) {
                    changeToWorld(door.getTargetWorld());
                    break;
                }
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
        for (GameObject obj : gameObjects) obj.render(batch);
        player.render(batch);
        for (Mob mob : mobs) mob.render(batch);
        batch.end();

        // === DEBUG VISUEL ===
        ShapeRenderer sr = new ShapeRenderer();
        sr.setProjectionMatrix(worldCamera.combined);

        // Hitbox complète du joueur (Rectangle bounds)
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.GREEN);
        Rectangle playerBounds = player.getBounds();
        sr.rect(playerBounds.x, playerBounds.y, playerBounds.width, playerBounds.height);
        sr.end();

        // Les 4 coins utilisés pour la collision
        sr.begin(ShapeRenderer.ShapeType.Filled);
        float px = player.getPosition().x;
        float py = player.getPosition().y;
        float cw = player.characterWidth;
        float ch = player.characterHeight;

        sr.setColor(Color.YELLOW);
        sr.circle(px, py, 5); // Bas gauche
        sr.circle(px + cw, py, 5); // Bas droit
        sr.circle(px, py + ch, 5); // Haut gauche
        sr.circle(px + cw, py + ch, 5); // Haut droit
        System.out.println("Gauche: "+py+" Droit: "+cw + px);
        System.out.println("pGauche: "+cw+" pDroit: " + ch);
        sr.end();

        // Afficher les tiles de collision en ROUGE
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.RED);
        if (collisionLayer != null) {
            int startX = Math.max(0, (int)(px / tileSize) - 10);
            int endX = Math.min(collisionLayer.getWidth(), (int)(px / tileSize) + 10);
            int startY = Math.max(0, (int)(py / tileSize) - 10);
            int endY = Math.min(collisionLayer.getHeight(), (int)(py / tileSize) + 10);

            for (int x = startX; x < endX; x++) {
                for (int y = startY; y < endY; y++) {
                    TiledMapTileLayer.Cell cell = collisionLayer.getCell(x, y);
                    if (cell != null && cell.getTile() != null &&
                        cell.getTile().getProperties().containsKey("blocked")) {
                        float tileX = x * tileSize;
                        float tileY = y * tileSize;
                        sr.rect(tileX, tileY, tileSize, tileSize);
                    }
                }
            }
        }
        sr.end();
        sr.dispose();
    }

    public boolean isCellBlocked(float worldX, float worldY) {
        if (collisionLayer == null) return false;

        float x = (float) ((worldX / (tileSize)) + 0.8f);
        float y =  ((worldY / tileSize) + 0.3f);

        if (x < 0 || y < 0 || x >= collisionLayer.getWidth() || y >= collisionLayer.getHeight()) {
            return false;
        }

        TiledMapTileLayer.Cell cell = collisionLayer.getCell((int)x, (int) y);
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

    public void setScreen(GameScreen screen) {
        this.screen = screen;
    }

    public void changeToWorld(BaseWorld newWorld) {
        if (screen != null) {
            screen.changeWorld(newWorld);
        }
    }
}
