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
import com.vaultborn.entities.characters.players.Player;
import com.vaultborn.world.BaseWorld;
import com.vaultborn.world.HellWorld;

public class GameScreen implements Screen {

    private final MainGame game;
    private final SpriteBatch batch;
    private final BaseWorld world;



    private MenuScreen PauseMenuScreen;
    private InventoryPlayer inv;


    private static final List<String> buttonPause = Arrays.asList("Continuer", "Parametres", "Exit");
    private Skin btnSkin = new Skin(Gdx.files.internal("menu/neon/skin/neon-ui.json"));



    public GameScreen(MainGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.world = new HellWorld();

        PauseMenuScreen = new MenuScreen(game, btnSkin, buttonPause);
        inv = new InventoryPlayer();
        if(world.getPlayer() instanceof Player){
            inv.setPlayer(world.getPlayer());
        }

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.update(delta);
        world.render(batch);

        /*
        //exit fast
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
                    Gdx.app.exit();
            }
        */
        if (!PauseMenuScreen.isActivated()){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            world.update(delta);
            world.render(batch);

            Gdx.input.setInputProcessor(null);

        }
        else{
            PauseMenuScreen.rdMenu(delta);
            Gdx.input.setInputProcessor(PauseMenuScreen.getStage());
            if(inv.isShowInventory()){inv.setShowInventory(false);inv.InventoryReload();}
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
                PauseMenuScreen.setActivated(!PauseMenuScreen.isActivated());
        }
        //fait les input du inventory
        inv.InventoryInput();
        if (inv.isShowInventory()&&!PauseMenuScreen.isActivated()){
            inv.rdMenu(delta);
            Gdx.input.setInputProcessor(inv.getStage());
            if(inv.getObjectInfoMenu()){
                Gdx.input.setInputProcessor(inv.getObjectStage());
            }   

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

    @Override
    public void dispose() {
        batch.dispose();
        world.dispose();
        inv.dpMenu();
    }
}
