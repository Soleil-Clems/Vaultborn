package com.vaultborn.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.vaultborn.MainGame;
import com.vaultborn.factories.Factory;
import com.vaultborn.world.ForestWorld;
import com.vaultborn.world.BaseWorld;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.vaultborn.world.HellWorld;


public class SelectPlayerScreen implements Screen {

    private final MainGame game;
    private final Stage stage;
    private final Skin skin;
    private Factory factory;

    public SelectPlayerScreen(MainGame game, Skin skin) {
        this.game = game;
        this.skin = skin;
        factory = new Factory();

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label title = new Label("Choisissez votre héros", skin);
        title.setFontScale(2f);
        table.add(title).pad(20).row();

        addButton(table, "Warrior", "warrior");
        addButton(table, "Archer", "archer");
        addButton(table, "Dark mage", "darkMage");
        addButton(table, "Light mage", "lightMage");
        addButton(table, "Satyr", "satyr");
    }

    private void addButton(Table table, String text, String classKey) {
        TextButton btn = new TextButton(text, skin);
        btn.getLabel().setFontScale(1.6f);
        table.add(btn).width(400).height(100).pad(10).row();

        btn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startGame(classKey);
            }
        });
    }

    private void startGame(String classKey) {
        game.forestWorld = new ForestWorld(game);
        game.hellWorld = new HellWorld(game);

        game.player = factory.createPlayer(classKey, 450, 800, game.forestWorld);

        game.forestWorld.setPlayer(game.player);

        game.forestWorld.linkWorlds();
        game.hellWorld.linkWorlds();

        InventoryPlayer inv = new InventoryPlayer(false);
        game.player.setInventory(inv);
        inv.setPlayer(game.player);

        game.setScreen(new GameScreen(game, game.forestWorld));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void show() { }
    @Override public void hide() { }
    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void dispose() { stage.dispose(); }
}
