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
import com.vaultborn.MainGame;
import com.vaultborn.entities.characters.Character;
import com.vaultborn.entities.characters.mobs.Gorgon;
import com.vaultborn.entities.characters.mobs.Mob;
import com.vaultborn.entities.characters.players.Player;
import com.vaultborn.entities.characters.players.Warrior;
import com.vaultborn.entities.projectiles.Projectile;
import com.vaultborn.entities.stuff.GameObject;
import com.vaultborn.entities.stuff.trigger.SpecialDoor;
import com.vaultborn.factories.Factory;
import com.vaultborn.managers.AssetManager;
import com.vaultborn.screens.GameScreen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class BaseWorld {
    private float damageTimer = 0f;
    private float damageCooldown = 0.5f;
    private Class<?> boss = Gorgon.class;
    private boolean isDeadBoss = false;

    protected final AssetManager assetsManager;
    protected final Factory factory;
    protected Player player;
    public List<Mob> mobs = new ArrayList<>();
    protected List<GameObject> gameObjects = new ArrayList<>();
    public List<Projectile> projectiles = new ArrayList<>();

    protected TiledMap map;
    protected TiledMapTileLayer collisionLayer;
    //    protected TiledMapTileLayer deadLayer;
    protected OrthogonalTiledMapRenderer tiledMapRenderer;
    protected OrthographicCamera worldCamera, uiCamera;

    protected Texture background;
    protected float tileSize;
    protected float mapHeightInPixels;

    protected String levelName;
    protected GameScreen screen;
    protected float oldPosY = 0f;
    protected float timer = 0f;
    public MainGame game;

    public BaseWorld(MainGame game, String levelName, String backgroundPath) {
        this.levelName = levelName;
        this.assetsManager = new AssetManager();
        this.factory = new Factory();
        this.background = new Texture(backgroundPath);
        this.game = game;
        loadMap();
        initCameras();
//        initPlayer();
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
        if (game.player != null) {
            this.player = game.player;
            this.player.setWorld(this);
        } else {
            System.out.println("⚠ initPlayer(): aucun joueur trouvé (normal si monde créé avant selection)");
        }
    }

    protected abstract void initMobs();

    protected abstract void initObjects();

    public void setBoss(Class<?> boss) {
        this.boss = boss;
    }

    public Class<?> getBoss() {
        return boss;
    }

    public void update(float delta) {
        player.update(delta);
        checkDeadTiles(player);
        checkDamageTiles(player);

        Iterator<Mob> it = mobs.iterator();
        while (it.hasNext()) {
            Mob mob = it.next();
            mob.update(delta);
            if (mob.isDead && mob.getAnimation("dead") != null &&
                mob.getAnimation("dead").isAnimationFinished(mob.stateTime)) {
                if (boss.isInstance(mob)) {
                    isDeadBoss = true;
                }
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


                if (isDeadBoss) {
                    door.setAnimation("open");
                    if (door.getTriggerZone().overlaps(player.getHitbox()) && door.getTargetWorld() != null) {
                        changeToWorld(door.getTargetWorld(), door.getSpawnPosition());
                        break;
                    }
                }
            }
        }

        Iterator<Projectile> projIt = projectiles.iterator();
        while (projIt.hasNext()) {
            Projectile proj = projIt.next();
            proj.update(delta);
            if (proj.toRemove) {
                projIt.remove();
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
        for (Projectile proj : projectiles) proj.render(batch);

        batch.end();

        float px = player.getPosition().x;
        float py = player.getPosition().y;


        if (collisionLayer != null) {
            int startX = Math.max(0, (int) (px / tileSize) - 10);
            int endX = Math.min(collisionLayer.getWidth(), (int) (px / tileSize) + 10);
            int startY = Math.max(0, (int) (py / tileSize) - 10);
            int endY = Math.min(collisionLayer.getHeight(), (int) (py / tileSize) + 10);

            for (int x = startX; x < endX; x++) {
                for (int y = startY; y < endY; y++) {
                    TiledMapTileLayer.Cell cell = collisionLayer.getCell(x, y);
                    if (cell != null && cell.getTile() != null &&
                        cell.getTile().getProperties().containsKey("blocked")) {
                    }
                }
            }
        }
    }

    public boolean isCellBlocked(float worldX, float worldY) {
        if (collisionLayer == null) return false;

        float x = (float) ((worldX / (tileSize)) + 0.8f);
        float y = ((worldY / tileSize) + 0.3f);

        if (x < 0 || y < 0 || x >= collisionLayer.getWidth() || y >= collisionLayer.getHeight()) {
            return false;
        }

        TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) x, (int) y);
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked");
    }

    /**
     * Vérifie si le personnage est proche d'une tile marquée "dead"
     */
    private void checkDeadTiles(Character character) {
        if (character.isDead || collisionLayer == null) return;

        float px = character.getPosition().x;
        float py = character.getPosition().y;
        float cw = character.characterWidth;
        float ch = character.characterHeight;

        float margin = 10f;
        boolean nearDeath =
            isCellDead(px - margin, py - margin) ||
                isCellDead(px + cw / 2, py - margin) ||
                isCellDead(px + cw + margin, py - margin) ||

                isCellDead(px - margin, py + ch / 2) ||
                isCellDead(px + cw + margin, py + ch / 2) ||

                isCellDead(px - margin, py + ch + margin) ||
                isCellDead(px + cw / 2, py + ch + margin) ||
                isCellDead(px + cw + margin, py + ch + margin) ||

                isCellDead(px + cw / 2, py + ch / 2);

        if (nearDeath) {
            character.setHp(0);
            character.takeDamage(0);
            System.out.println(character.getName() + " est tombé dans la lave !");
        }
    }

    /**
     * Vérifie si une tile a la propriété "dead"
     */
    public boolean isCellDead(float worldX, float worldY) {
        if (collisionLayer == null) return false;

        float x = (float) ((worldX / (tileSize)) + 0.8f);
        float y = ((worldY / tileSize) + 0.3f);

        if (x < 0 || y < 0 || x >= collisionLayer.getWidth() || y >= collisionLayer.getHeight()) {
            return false;
        }

        TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) x, (int) y);
        if (cell == null || cell.getTile() == null) return false;

        Object deadProp = cell.getTile().getProperties().get("dead");
        return deadProp instanceof Boolean && (Boolean) deadProp;
    }

    /**
     * Vérifie si le personnage est proche d'une tile marquée "rmlife"
     */
    private void checkDamageTiles(Character character) {
        if (character.isDead || collisionLayer == null) return;


        if (damageTimer > 0) {
            damageTimer -= Gdx.graphics.getDeltaTime();
            return;
        }

        float px = character.getPosition().x;
        float py = character.getPosition().y;
        float cw = character.characterWidth;
        float ch = character.characterHeight;

        float margin = 10f;

        boolean nearDamage =
            isCellDamaging(px - margin, py - margin) ||
                isCellDamaging(px + cw / 2, py - margin) ||
                isCellDamaging(px + cw + margin, py - margin) ||
                isCellDamaging(px - margin, py + ch / 2) ||
                isCellDamaging(px + cw + margin, py + ch / 2) ||
                isCellDamaging(px - margin, py + ch + margin) ||
                isCellDamaging(px + cw / 2, py + ch + margin) ||
                isCellDamaging(px + cw + margin, py + ch + margin) ||
                isCellDamaging(px + cw / 2, py + ch / 2);

        if (nearDamage) {
            character.takeDamage(1);
            damageTimer = damageCooldown;
        }
    }

    /**
     * Vérifie si une tile a la propriété "rmlife"
     */
    public boolean isCellDamaging(float worldX, float worldY) {
        if (collisionLayer == null) return false;

        float x = (float) ((worldX / (tileSize)) + 0.8f);
        float y = ((worldY / tileSize) + 0.3f);

        if (x < 0 || y < 0 || x >= collisionLayer.getWidth() || y >= collisionLayer.getHeight()) {
            return false;
        }

        TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) x, (int) y);
        if (cell == null || cell.getTile() == null) return false;

        Object rmlifeProp = cell.getTile().getProperties().get("rmlife");
        return rmlifeProp instanceof Boolean && (Boolean) rmlifeProp;
    }


    public Character getNearestEnemy(Character attacker, float range) {
        Character nearest = null;
        float nearestDist = Float.MAX_VALUE;

        if (attacker == player || attacker instanceof Player) {
            for (Mob mob : mobs) {
                if (mob.isDead) continue;

                float dx = mob.getPosition().x - attacker.getPosition().x;

                if ((attacker.facingRight && dx < 0) || (!attacker.facingRight && dx > 0)) continue;

                float dist = mob.getPosition().dst(attacker.getPosition());
                if (dist < nearestDist && dist <= range * 64f) {
                    nearest = mob;
                    nearestDist = dist;
                }
            }
        } else if (attacker instanceof Mob) {
            if (!player.isDead) {
                float dx = player.getPosition().x - attacker.getPosition().x;

                if ((attacker.facingRight && dx > 0) || (!attacker.facingRight && dx < 0)) {
                    float dist = player.getPosition().dst(attacker.getPosition());
                    if (dist <= range * 64f) {
                        nearest = player;
                    }
                }
            }
        }

        return nearest;
    }

    public Player getPlayer() {
        return player;
    }

    public Player setPlayer(Player player) {
        this.player = player;
        if (player != null) {
            player.setWorld(this);
        }
        return player;
    }

    public void dispose() {
        assetsManager.dispose();
        map.dispose();
        background.dispose();
    }

    public void setScreen(GameScreen screen) {
        this.screen = screen;
    }

    public void changeToWorld(BaseWorld newWorld, Vector2 spawnPosition) {
        if (screen != null) {
            newWorld.setPlayer(this.player);
            newWorld.game.player = this.player;
            this.player.setWorld(newWorld);

            if (spawnPosition != null) {
                this.player.getPosition().set(spawnPosition);
            }

            screen.changeWorld(newWorld);
        }
    }


    public boolean isMobAt(Vector2 pos, Mob self) {
        for (Mob mob : mobs) {
            if (mob == self || mob.isDead) continue;
            if (mob.getBounds().overlaps(new Rectangle(pos.x, pos.y, mob.characterWidth, mob.characterHeight))) {
                return true;
            }
        }
        return false;
    }

    public void linkWorlds() {

    }
}
