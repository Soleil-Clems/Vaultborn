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
    
    private MenuScreen PauseMenuScreen;
    private InventoryPlayer inv;
    
    
    private static final List<String> buttonPause = Arrays.asList("Continuer", "Parametres", "Exit");
    private Skin btnSkin = new Skin(Gdx.files.internal("menu/neon/skin/neon-ui.json"));


    public GameScreen(MainGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        world = new World();
        
        PauseMenuScreen = new MenuScreen(game, btnSkin, buttonPause);
        inv = new InventoryPlayer();
        
        
    }

    @Override
    public void render(float delta) {
        /*
        //exit fast
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
                    Gdx.app.exit();
            }
        */
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
        //fait les input du inventory
        inv.InventoryInput();
        if (inv.isShowInventory()){
            inv.rdMenu(delta);
            Gdx.input.setInputProcessor(inv.getStage());
            
        }
                
    }

    @Override public void resize(int width, int height) {
            
            PauseMenuScreen.rsMenu(width, height);  
            inv.rsMenu(width, height);
        
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void show() {}
    @Override public void dispose() {
            batch.dispose(); 
        inv.dpMenu();}
}
