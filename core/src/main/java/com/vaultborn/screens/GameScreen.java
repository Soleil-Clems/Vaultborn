package com.vaultborn.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.vaultborn.MainGame;
import com.vaultborn.world.World;

public class GameScreen implements Screen {

    private MainGame game;
    private SpriteBatch batch;
    private World world;

    public GameScreen(MainGame game) {
        this.game = game;
        this.batch = new SpriteBatch();


        world = new World();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.update(delta);

        batch.begin();
        world.render(batch);
        batch.end();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void show() {}
    @Override public void dispose() { batch.dispose(); }
}
