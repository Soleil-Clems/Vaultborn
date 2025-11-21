package com.vaultborn.screens;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.vaultborn.MainGame;
import com.vaultborn.entities.characters.players.Player;
import com.vaultborn.factories.Factory;
import com.vaultborn.managers.InputManager;
import com.vaultborn.world.BaseWorld;
import com.vaultborn.world.HellWorld;

public class GameScreen implements Screen {

    private final MainGame game;
    private final SpriteBatch batch;
    private BaseWorld world;
    private Factory factory;

    private MenuScreen PauseMenuScreen;
    private InventoryPlayer inv;
    private InputManager inputManager;


    private static final List<String> buttonPause = Arrays.asList("Continuer",  "Menu principal", "Parametres", "Exit");
    private Skin btnSkin = new Skin(Gdx.files.internal("menu/neon/skin/neon-ui.json"));

    private SettingScreen SettingMenuScreen;
    private SelectPlayerScreen selectPlayerScreen;

    public GameScreen(MainGame game, BaseWorld world,Skin skin) {
        btnSkin = skin;
        this.game = game;
        this.batch = new SpriteBatch();

        PauseMenuScreen = new MenuScreen(game, btnSkin, buttonPause);
        SettingMenuScreen = new SettingScreen(game,skin);
        inv = new InventoryPlayer(false);
        inputManager = game.inputManager;

        this.world = world;
        this.world.setScreen(this);
        factory = new Factory();

        if(world.getPlayer() != null){
            inv.setPlayer(world.getPlayer());
            world.getPlayer().setInventory(inv);
        }
        selectPlayerScreen = new SelectPlayerScreen(game, btnSkin);

    }

    @Override
    public void render(float delta) {
        if(world.getPlayer().isDead){game.setScreen(new GameOverScreen(game,btnSkin));}
        if(world.getPlayer().isWin){game.setScreen(new WinScreen(game,btnSkin));}
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            game.saveGame();
//            System.out.println("Partie sauvegardée !" + game);
        }

        if (!PauseMenuScreen.isActivated()) {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            world.update(delta);
            world.render(batch);

            batch.begin();
            batch.setProjectionMatrix(world.getUiCamera().combined);
            renderPlayerHealthBar(batch);
            batch.end();

            Gdx.input.setInputProcessor(null);

        } else {
            if(!SettingMenuScreen.isActivated()){

                if(btnSkin != SettingMenuScreen.getSkin()){
                    this.btnSkin = SettingMenuScreen.getSkin();
                    PauseMenuScreen.reloadMenu(game,this.btnSkin, buttonPause,inputManager);
                    PauseMenuScreen.setActivated(true);
                    PauseMenuScreen.setSettings(false);
                }

                SettingMenuScreen.setActivated(PauseMenuScreen.isSettings());
                PauseMenuScreen.rdMenu(delta);
                Gdx.input.setInputProcessor(PauseMenuScreen.getStage());
                if(inv.isShowInventory()){inv.setShowInventory(false);inv.InventoryReload();}
            }
            else{
                PauseMenuScreen.setSettings(false);
                Gdx.input.setInputProcessor(SettingMenuScreen.getStage());
                SettingMenuScreen.rdMenu(delta);
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
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

    private void renderPlayerHealthBar(SpriteBatch batch) {
        int hp = world.getPlayer().getHp();
        int maxHp = world.getPlayer().getMaxHp();
        String playerName = world.getPlayer().getName();
        float ratio = Math.max(0f, Math.min(1f, (float) hp / maxHp));

        float x = 20f;
        float y = Gdx.graphics.getHeight() - 60f;
        float width = 300f;
        float height = 24f;

        BitmapFont font = MainGame.getFont();

        batch.setColor(Color.WHITE);
        font.draw(batch, playerName, x, y + height + 20);


        batch.setColor(Color.BLACK);
        batch.draw(MainGame.getWhitePixel(), x - 2, y - 2, width + 4, height + 4);


        batch.setColor(0.4f, 0f, 0f, 1f);
        batch.draw(MainGame.getWhitePixel(), x, y, width, height);


        batch.setColor(0f, 0.8f, 0f, 1f);
        batch.draw(MainGame.getWhitePixel(), x, y, width * ratio, height);

        batch.setColor(Color.WHITE);
        font.draw(batch, hp + " / " + maxHp, x + 8, y + height - 6);
    }

    @Override
    public void resize(int width, int height) {
        PauseMenuScreen.rsMenu(width, height);
        inv.rsMenu(width, height);

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void show() {
    }

    @Override
    public void dispose() {
        batch.dispose();
//        world.dispose();
        inv.dpMenu();
    }

    public void changeWorld(BaseWorld newWorld) {
        this.world = newWorld;
        this.world.setScreen(this);
    }

}
