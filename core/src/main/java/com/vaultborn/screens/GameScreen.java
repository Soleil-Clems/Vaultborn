package com.vaultborn.screens;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import com.vaultborn.MainGame;
import com.vaultborn.world.World;

public class GameScreen extends AbsMenu /*implements Screen*/ {

    
    private SpriteBatch batch;
    private World world;

    
    private boolean paused = false;
    private static final List<String> items = Arrays.asList("Continuer", "Parametres", "Exit");

    public GameScreen(MainGame game) {
        super(game, "Menu", new Skin(Gdx.files.internal("menu/neon/skin/neon-ui.json")), items);
        this.game = game;
        this.batch = new SpriteBatch();


        world = new World();
        
        
    }
    @Override
    protected void onClick(String name){
        switch (name) {
            case "Continuer" :
                paused = false;         
                break;
            case "Parametres":
                break;
            case "Exit" :
                Gdx.app.exit();
                break;
            default:
                break;
        }
    }

    @Override
    public void render(float delta) {
        if (!paused){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            world.update(delta);
            batch.begin();
            world.render(batch);
            batch.end(); 
            
        }
        else{
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            stage.act(delta);
            stage.draw();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
                paused = !paused;
            }

                
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void show() {}
    @Override public void dispose() { batch.dispose(); }
}
