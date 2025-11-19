package com.vaultborn.screens;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


import com.vaultborn.MainGame;


public class MainScreen implements Screen {
    private MenuScreen MainMenuScreen;
    private SettingScreen SettingMenuScreen;
    private static final List<String> buttonMain = Arrays.asList("Poursuivre","Jouer", "Parametres", "Exit");
    Skin btnSkin = new Skin(Gdx.files.internal("menu/neon/skin/neon-ui.json"));

    public MainScreen(MainGame game) {
        MainMenuScreen = new MenuScreen(game, btnSkin, buttonMain);
        SettingMenuScreen = new SettingScreen(game,btnSkin);
    }

    @Override
    public void render(float delta) {
        if (!SettingMenuScreen.isActivated()) {
            SettingMenuScreen.setActivated(MainMenuScreen.isSettings());
            Gdx.input.setInputProcessor(MainMenuScreen.getStage());
            MainMenuScreen.rdMenu(delta);
        } else {
            MainMenuScreen.setSettings(false);
            Gdx.input.setInputProcessor(SettingMenuScreen.getStage());
            SettingMenuScreen.rdMenu(delta);
        }
    }

    @Override
    public void resize(int width, int height) {
        if (!SettingMenuScreen.isActivated()) {
            MainMenuScreen.rsMenu(width, height);
        } else {
            SettingMenuScreen.rsMenu(width, height);
        }
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
        MainMenuScreen.dpMenu();
    }


}
