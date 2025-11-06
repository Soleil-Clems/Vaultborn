package com.vaultborn.screens;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import com.vaultborn.MainGame;

import java.util.List;
import java.util.Arrays;

public class MenuScreen extends AbsMenu {

    private static final List<String> items = Arrays.asList("Jouer", "Parametres", "Exit");

    public MenuScreen(MainGame game) {
        
        super(game, "Menu", new Skin(Gdx.files.internal("menu/neon/skin/neon-ui.json")), items);
       
    }

   @Override
   protected void onClick(String name) {
       switch(name) {
            case "Jouer":
                game.setScreen(new GameScreen(game));
                break;
            case "Settings":
                //game.setScreen(new SettingsScreen(game));
                break;
            case "Exit":
                Gdx.app.exit();
                break;
        }
       
   }
}
