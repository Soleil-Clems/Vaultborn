package com.vaultborn.screens;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


import com.vaultborn.MainGame;


public class MainScreen implements Screen{
    private MenuScreen MainMenuScreen;
    private static final List<String> buttonMain = Arrays.asList("Jouer", "Parametres", "Exit");
    Skin btnSkin = new Skin(Gdx.files.internal("menu/neon/skin/neon-ui.json"));

    public MainScreen(MainGame game){
        MainMenuScreen = new MenuScreen(game, btnSkin, buttonMain);
    }

    @Override public void render(float delta) {
        MainMenuScreen.rdMenu(delta);

        }

    @Override public void resize(int width, int height) {MainMenuScreen.rsMenu(width, height);}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void show() {}
    @Override public void dispose() {MainMenuScreen.dpMenu();}



}
