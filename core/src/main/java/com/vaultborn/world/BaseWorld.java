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
import com.vaultborn.factories.FactoryException;
import com.vaultborn.managers.AssetManager;
import com.vaultborn.screens.GameScreen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe abstraite représentant un monde du jeu.
 *
 * <p>
 * BaseWorld gère :
 * <ul>
 *   <li>Le joueur et ses interactions</li>
 *   <li>Les mobs et leur mise à jour</li>
 *   <li>Les objets, projectiles et triggers</li>
 *   <li>La caméra (monde et interface)</li>
 *   <li>La détection de collisions et de zones spéciales (morts, dégâts)</li>
 *   <li>Le passage entre mondes via {@link SpecialDoor}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Les classes concrètes doivent implémenter :
 * <ul>
 *   <li>{@link #initMobs()} : initialiser tous les mobs de ce monde</li>
 *   <li>{@link #initObjects()} : initialiser tous les objets et triggers de ce monde</li>
 * </ul>
 * </p>
 */
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

    public int spawnX = 0;
    public int spawnY = 0;
    protected TiledMap map;
    protected TiledMapTileLayer collisionLayer;
    protected OrthogonalTiledMapRenderer tiledMapRenderer;
    protected OrthographicCamera worldCamera, uiCamera;

    protected Texture background;
    protected float tileSize;
    protected float mapHeightInPixels;
    public boolean isEnd = false;

    public String levelName;
    public String mapName;
    protected GameScreen screen;
    protected float oldPosY = 0f;
    protected float timer = 0f;
    public MainGame game;

    /**
     * Constructeur principal du monde.
     *
     * @param game          Référence au jeu principal
     * @param mapName       Nom de la carte TMX
     * @param backgroundPath Chemin vers la texture de fond
     * @throws FactoryException si un problème survient lors de la création des mobs/objets
     */
    public BaseWorld(MainGame game, String mapName, String backgroundPath) throws FactoryException {
        this.mapName = mapName;
        this.assetsManager = new AssetManager();
        this.factory = new Factory();
        this.background = new Texture(backgroundPath);
        this.game = game;
        loadMap();
        initCameras();
        initMobs();
        initObjects();
    }

    public OrthographicCamera getUiCamera() {
        return uiCamera;
    }

    /**
     * Charge la carte TMX et la couche de collision.
     */
    protected void loadMap() {
        map = new TmxMapLoader().load("maps/" + mapName + ".tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map, 1f);

        collisionLayer = (TiledMapTileLayer) map.getLayers().get("Collision");

        int mapWidth = map.getProperties().get("width", Integer.class);
        int mapHeight = map.getProperties().get("height", Integer.class);
        tileSize = map.getProperties().get("tilewidth", Integer.class);
        mapHeightInPixels = mapHeight * tileSize;
    }

    /**
     * Initialise les caméras monde et UI.
     */
    protected void initCameras() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        worldCamera = new OrthographicCamera(w, h);
        worldCamera.update();

        uiCamera = new OrthographicCamera(w, h);
        uiCamera.position.set(w / 2f, h / 2f, 0);
        uiCamera.update();
    }

    /**
     * Initialise le joueur pour ce monde.
     * Utilise le joueur global du jeu si déjà créé.
     */
    protected void initPlayer() {
        if (game.player != null) {
            this.player = game.player;
            this.player.setWorld(this);
        } else {
            System.out.println("⚠ initPlayer(): aucun joueur trouvé (normal si monde créé avant selection)");
        }
    }

    /**
     * Initialise tous les mobs du monde.
     * Doit être implémenté par la classe concrète.
     */
    protected abstract void initMobs() throws FactoryException;

    /**
     * Initialise tous les objets et triggers du monde.
     * Doit être implémenté par la classe concrète.
     */
    protected abstract void initObjects() throws FactoryException;

    public void setBoss(Class<?> boss) {
        this.boss = boss;
    }

    public Class<?> getBoss() {
        return boss;
    }

    /**
     * Met à jour le monde : joueur, mobs, objets, projectiles et caméra.
     *
     * @param delta Temps écoulé depuis la dernière frame
     */

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
                if(obj.pickUp(player)){
                objectIterator.remove();}
            }

            if (obj instanceof SpecialDoor) {
                SpecialDoor door = (SpecialDoor) obj;

                if (isDeadBoss) {
                    door.setAnimation("open");
                    if (door.getTriggerZone().overlaps(player.getHitbox()) && door.getTargetWorld() != null) {
                        door.pickUp(player);

                        if (game != null) {
                            game.saveGame();
                            if (this.isEnd) {
                                player.isWin = true;
                            }
                        }
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

    /**
     * Met à jour la caméra du monde en suivant le joueur avec une zone morte.
     */
    protected void updateCamera() {
        float delta = com.badlogic.gdx.Gdx.graphics.getDeltaTime();

        float CamOffsetY = 220f;
        float CamOffsetX = 50f;
        float CamSpeedFast = 5f;
        float CamSpeedSlow = 3f;
        float DeadZoneYUp = 100f;
        float DeadZoneX = 170f;

        float camX = worldCamera.position.x;
        float camY = worldCamera.position.y;

        float targetX = player.getPosition().x + CamOffsetX;
        float targetY = player.getPosition().y + CamOffsetY;

        float diffX = targetX - camX;
        float diffY = targetY - camY;

        if (Math.abs(diffX) > DeadZoneX) {
            camX += (diffX - Math.signum(diffX) * DeadZoneX) * CamSpeedFast * delta;
        } else {
            camX += diffX * CamSpeedSlow * delta;
        }

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

    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(uiCamera.combined);
        batch.begin();
        batch.draw(background, 0, 0, uiCamera.viewportWidth, uiCamera.viewportHeight);
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

    /**
     * Méthode appelée lors du redimensionnement de la fenêtre
     */
    public void resize(int width, int height) {
        worldCamera.viewportWidth = width;
        worldCamera.viewportHeight = height;
        worldCamera.update();

        uiCamera.viewportWidth = width;
        uiCamera.viewportHeight = height;
        uiCamera.position.set(width / 2f, height / 2f, 0);
        uiCamera.update();
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
        if (game != null) {
            game.saveGame();
        }

        newWorld.setPlayer(this.player);
        game.player = this.player;
        this.player.setWorld(newWorld);

        if (spawnPosition != null) {
            this.player.getPosition().set(spawnPosition);
        }

        if (screen != null) {
            screen.changeWorld(newWorld);
        }

        game.currentWorld = newWorld;
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
