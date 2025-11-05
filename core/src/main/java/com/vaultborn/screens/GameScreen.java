package com.vaultborn.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.vaultborn.MainGame;
import com.vaultborn.world.World;

public class GameScreen implements Screen {

    private MainGame game;
    private SpriteBatch batch;
    private World world;

    private Stage pauseStage;
    private boolean paused = false;

    public GameScreen(MainGame game) {
        this.game = game;
        this.batch = new SpriteBatch();


        world = new World();
        pauseStage = new Stage(new ScreenViewport());
        Skin skin = new Skin(Gdx.files.internal("menu/neon/skin/neon-ui.json"));

        Table pauseTable = new Table();
        pauseTable.setFillParent(true);
        pauseStage.addActor(pauseTable);

        TextButton playButton = new TextButton("Continuer", skin);
        TextButton settingsButton = new TextButton("Parametres", skin);
        TextButton exitButton = new TextButton("Sortir", skin);

        pauseTable.center();
        pauseTable.add(playButton).pad(10).width(200).row();
        pauseTable.add(settingsButton).pad(10).width(200).row();
        pauseTable.add(exitButton).pad(10).width(200);

        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                paused=false;
            }
        });

        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //game.setScreen(new SettingsScreen(game));
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            paused = !paused;
            pauseStage.getActors().first().setVisible(paused);

            if (paused) {
                Gdx.input.setInputProcessor(pauseStage); 
            } else {
                Gdx.input.setInputProcessor(null);
            }
            System.out.println("Bouton escape");
            System.out.println(paused);
        }
        if(!paused){
            world.update(delta);
            batch.begin();
            world.render(batch);
            batch.end();            
        }
        else{
            pauseStage.act(delta);
            pauseStage.draw();
        }

        

        
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void show() {}
    @Override public void dispose() { batch.dispose(); }
}
