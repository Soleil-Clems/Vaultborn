package com.vaultborn.screens;

import java.security.Key;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.PauseableThread;
import com.vaultborn.MainGame;
import com.vaultborn.managers.InputManager;
import com.badlogic.gdx.audio.Music;



public class MainScreen implements Screen{
    private MenuScreen MainMenuScreen;
    private SettingScreen SettingMenuScreen;
    private InputManager inputManager;

    private MainGame game;

    private static final List<String> buttonMain = Arrays.asList("Poursuivre","Jouer", "Parametres", "Exit");

    Skin btnSkin = new Skin(Gdx.files.internal("menu/neon/skin/neon-ui.json"));

    public MainScreen(MainGame game){
        this.game = game;
        MainMenuScreen = new MenuScreen(game, btnSkin, buttonMain);
        SettingMenuScreen = new SettingScreen(game,btnSkin);
        inputManager = game.inputManager;
    }




    @Override public void render(float delta) {
        if(!SettingMenuScreen.isActivated()){
            SettingMenuScreen.setActivated(MainMenuScreen.isSettings());
            Gdx.input.setInputProcessor(MainMenuScreen.getStage());
            MainMenuScreen.rdMenu(delta);


        }
        else{
            MainMenuScreen.reloadMenu(this.game, SettingMenuScreen.getSkin(), buttonMain,inputManager);
            MainMenuScreen.setSettings(false);
            Gdx.input.setInputProcessor(SettingMenuScreen.getStage());
            SettingMenuScreen.rdMenu(delta);
        }
        }

    @Override public void resize(int width, int height) {
        if(!SettingMenuScreen.isActivated()){
            this.btnSkin = SettingMenuScreen.getSkin();
            MainMenuScreen.rsMenu(width, height);
        }
        else{
            SettingMenuScreen.rsMenu(width, height);
        }
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void show() {
        MainMenuScreen.shMenu();
}
    @Override public void dispose() {
        MainMenuScreen.dpMenu();

    }



}
