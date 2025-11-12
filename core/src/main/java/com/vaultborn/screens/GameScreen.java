package com.vaultborn.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.vaultborn.MainGame;
import com.vaultborn.world.BaseWorld;
import com.vaultborn.world.HellWorld;

public class GameScreen implements Screen {

    private final MainGame game;
    private final SpriteBatch batch;
    private final BaseWorld world;

    public GameScreen(MainGame game) {
        this.game = game;
        this.batch = new SpriteBatch();


        this.world = new HellWorld();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.update(delta);
        world.render(batch);
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void show() {}

    @Override
    public void dispose() {
        batch.dispose();
        world.dispose();
    }
}
