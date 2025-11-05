package com.vaultborn.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.players.Warrior;
//import com.vaultborn.entities.stuff.weapon.Sword;
import com.vaultborn.managers.AssetManager;

public class World {

    private final AssetManager assets;
    private final Warrior player;
    //private final Sword sword;
    //private final Wand wand;

    public World() {

        assets = new AssetManager();


        Texture portrait = new Texture("warrior/Idle.png");
        TextureRegion region = new TextureRegion(portrait);

        player = new Warrior(new Vector2(100, 100), region);
        player.loadAnimations();

        //TextureRegion regionSword = new TextureRegion(new Texture("objects/sword.png"));
        //sword = new Sword(new Vector2(100, 100), regionSword);
    }

    public void update(float delta) {
        player.update(delta);
        //sword.update(delta);
    }

    public void render(SpriteBatch batch) {
        player.render(batch);
        //sword.render(batch);
    }

    public void dispose() {
        assets.dispose();
    }

    public Warrior getPlayer() {
        return player;
    }
}
