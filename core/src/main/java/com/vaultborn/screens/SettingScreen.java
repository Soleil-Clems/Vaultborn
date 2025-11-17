package com.vaultborn.screens;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.Actor;

import com.vaultborn.MainGame;


public class SettingScreen {

    protected MainGame game;
    protected Stage stage;
    protected Skin skin;
    private boolean activated;
    private Table table;


    public SettingScreen(MainGame game, Skin skin) {
        this.game = game;
        this.skin = skin;
        this.activated = false;
        stage = new Stage(new ScreenViewport());
        //initie les inputs
        Gdx.input.setInputProcessor(stage);
        //System.out.println(Gdx.files.internal(skin).exists());

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        TextButton Button = new TextButton("Retour", skin);
        Button.getLabel().setFontScale(2f);
        table.add(Button).pad(10).width(500).height(Gdx.graphics.getHeight() * 0.2f).row();

        Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Bouton : " + "Retour");
                onClick("Retour");
            }
        });
    }

    public boolean isActivated() {
        return activated;
    }

    public Stage getStage() {
        return stage;
    }

    public void setActivated(boolean a) {
        this.activated = a;
    }

    public void onClick(String name) {
        switch (name) {
            case "Retour":
                this.activated = !this.activated;
                System.out.println(activated);
                break;
            default:
                break;
        }
    }

    public void rdMenu(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    public void dpMenu() {
        stage.dispose();
        skin.dispose();
    }

    public void rsMenu(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void shMenu() {
    }

    public void pMenu() {
    }

    public void rMenu() {
    }

    public void hMenu() {
    }
}
