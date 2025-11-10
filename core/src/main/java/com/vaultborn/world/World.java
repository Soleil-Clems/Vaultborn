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



import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.vaultborn.managers.AssetManager;
import com.vaultborn.entities.characters.Character;

public class World {

    private final AssetManager assetsManager;
    private final Warrior player;

    private final Gorgon mob;

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

        assetsManager = new AssetManager();

        Texture portrait = new Texture("warrior/Idle.png");
        TextureRegion region = new TextureRegion(portrait);

        Texture portraitMob = new Texture("gorgon/Idle.png");
        TextureRegion regionMob = new TextureRegion(portraitMob);

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

        player = new Warrior(new Vector2(350, 580), region);
        player.loadAnimations();

        player.setWorld(this);


        mob = new Gorgon(new Vector2(330, 580), regionMob);
        mob.loadAnimations();
        mob.setWorld(this);


    }

    public void update(float delta) {
        player.update(delta);
        mob.update(delta);


        if (checkCollision(player, mob)) {
//            System.out.println("Collision avec le mob ! "+checkCollision(player, mob));

//            player.getPosition().x -= 5f;
        }


        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        float cameraX = player.getPosition().x + 100;
        float cameraY = 730f;
        cameraY = Math.max(cameraY, h / 2f);

        worldCamera.position.set(cameraX, cameraY, 0);
        worldCamera.update();

    }

    private boolean checkCollision(Character a, Character b) {
        if (!mob.isDead && !player.isDead) {
//            player.attack(mob);
//            mob.attack(player);
        }
        return a.getBounds().overlaps(b.getBounds());
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

        mob.render(batch);

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
        if (collisionLayer == null) {
            return false;
        }

        int x = (int) (worldX / tileSize);
        int y = (int) (worldY / tileSize);

        if (x < 0 || y < 0 || x >= collisionLayer.getWidth() || y >= collisionLayer.getHeight()) {
            return false;
        }

        TiledMapTileLayer.Cell cell = collisionLayer.getCell(x, y);

        if (cell == null || cell.getTile() == null) {
            return false;
        }

        return cell.getTile().getProperties().containsKey("blocked");
    }

    public Character getNearestEnemy(Character attacker, float range) {
        // 💡 Pour plus tard : ici on bouclera sur une liste de mobs.
        // for (Character c : mobs) {
        //     if (c == attacker || c.isDead) continue;
        //     float dist = c.getPosition().dst(attacker.getPosition());
        //     if (dist <= range * 64f) {
        //         return c;
        //     }
        // }
        // return null;


        if (attacker == player && mob != null && !mob.isDead) {
            float dist = mob.getPosition().dst(player.getPosition());
            if (dist <= range * 64f) {
                return mob;
            }
        }

        if (attacker == mob && player != null && !player.isDead) {
            float dist = player.getPosition().dst(mob.getPosition());
            if (dist <= range * 64f) {
                return player;
            }
        }

        return null;
    }


}
