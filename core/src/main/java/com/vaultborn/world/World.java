package com.vaultborn.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.players.Warrior;
import com.vaultborn.managers.AssetManager;

public class World {

    private final AssetManager assets;
    private final Warrior player;

    public World() {

        assets = new AssetManager();


        Texture portrait = new Texture("warrior/Idle.png");
        TextureRegion region = new TextureRegion(portrait);

        player = new Warrior(new Vector2(100, 100), region);
        player.loadAnimations();
    }

    public void update(float delta) {
        player.update(delta);
    }

    public void render(SpriteBatch batch) {
        player.render(batch);
    }

    public void dispose() {
        assets.dispose();
    }

    public Warrior getPlayer() {
        return player;
    }
}
