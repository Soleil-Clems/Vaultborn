package com.vaultborn.screens;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import com.vaultborn.MainGame;
import com.vaultborn.world.World;

public class GameScreen implements Screen {

    
    private SpriteBatch batch;
    private World world;
    private MainGame game;
    private MenuScreen MainMenuScreen;
    private MenuScreen PauseMenuScreen;
    private static final List<String> buttonMain = Arrays.asList("Jouer", "Parametres", "Exit");
    private static final List<String> buttonPause = Arrays.asList("Continuer", "Parametres", "Exit");
    private Skin btnSkin = new Skin(Gdx.files.internal("menu/neon/skin/neon-ui.json"));


    public GameScreen(MainGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        world = new World();
        MainMenuScreen = new MenuScreen(game, btnSkin, buttonMain);  
        MainMenuScreen.setActivated(true);
        PauseMenuScreen = new MenuScreen(game, btnSkin, buttonPause);  
        
    }

    @Override
    public void render(float delta) {
        // if(MainMenuScreen.isActivated()){
        //     MainMenuScreen.rdMenu(delta);
        //     Gdx.input.setInputProcessor(MainMenuScreen.getStage());
        // }
        // else{
            if (!PauseMenuScreen.isActivated()){
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                world.update(delta);
                batch.begin();
                world.render(batch);
                batch.end(); 
                Gdx.input.setInputProcessor(null);
                
            }
            else{
                PauseMenuScreen.rdMenu(delta);
                Gdx.input.setInputProcessor(PauseMenuScreen.getStage());
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
                    PauseMenuScreen.setActivated(!PauseMenuScreen.isActivated());
            }
        // }
                
    }

    @Override public void resize(int width, int height) {
         if(MainMenuScreen.isActivated()){
            //MainMenuScreen.rsMenu(width,height);
        }
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void show() {}
    @Override public void dispose() {
         if(MainMenuScreen.isActivated()){
            MainMenuScreen.dpMenu();
        }
        else{
            batch.dispose(); }
        }
}
