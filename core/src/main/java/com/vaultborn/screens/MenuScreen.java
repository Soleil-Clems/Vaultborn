package com.vaultborn.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.Color;

import com.vaultborn.MainGame;
import com.vaultborn.managers.InputManager;
import com.vaultborn.factories.FactoryException;
import com.vaultborn.save.SaveManager;

import java.util.List;

public class MenuScreen {

    protected MainGame game;
    protected Stage stage;
    protected Skin skin;
    protected List<String> element;
    private boolean activated;
    private Table table;
    private Music backgroundMusic;
    private boolean settings = false;
    private TextButton continueButton; // Référence au bouton Poursuivre
    private InputManager inputManager;

    public MenuScreen(MainGame game,Skin skin,List<String> element){
        createMenu(game, skin, element);
        game.getBackgroundMusic().play();
    }
    public MenuScreen(MainGame game,Skin skin,List<String> element,InputManager inputManager){
        createMenu(game, skin, element);
        game.getBackgroundMusic().play();
        this.inputManager = inputManager;
    }

    public Music getBackgroundMusic(){
        return this.backgroundMusic;
    }
    public InputManager getInputManager(){
        return this.inputManager;
    }


//    public boolean isActivated(){
//        return activated;
//    }
//    public Stage getStage(){
//        return stage;
//    }
//    public void setActivated(boolean a){
//        this.activated = a;
//    }
//    public boolean isSettings(){
//        return settings;
//    }
    public void setSettings(boolean a){
        this.settings = a;
    }

    public void reloadMenu(MainGame game,Skin skin,List<String> element){
        createMenu(game, skin, element);
        this.settings = true;
    }
    public void reloadMenu(MainGame game,Skin skin,List<String> element,InputManager inputManager){
        createMenu(game, skin, element);
        this.settings = true;
        this.inputManager = inputManager;
    }

    public void createMenu(MainGame game,Skin skin,List<String> element){
        this.game = game;
        this.skin = skin;
        this.element = element;
        this.activated = false;

        stage = new Stage(new ScreenViewport());
        //initie les inputs
        Gdx.input.setInputProcessor(stage);
        //System.out.println(Gdx.files.internal(skin).exists());

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        for (String e : element) {
            TextButton button = new TextButton(e, skin);
            button.getLabel().setFontScale(2f);
            table.add(button).pad(10).width(500).height(Gdx.graphics.getHeight() * 0.2f).row();

            // Garder une référence au bouton "Poursuivre"
            if (e.equals("Poursuivre")) {
                continueButton = button;

                // Désactiver le bouton si aucune sauvegarde n'existe
                if (!SaveManager.hasSave()) {
                    button.setDisabled(true);
                    button.getLabel().setColor(Color.GRAY);
                }
            }

            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Bouton : " + e);
                    try {
                        onClick(e);
                    } catch (FactoryException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            });
        }
//        SaveManager.deleteSave();
    }


    public void onClick(String name) throws FactoryException {
        switch (name) {
            case "Jouer":
                // Nouvelle partie
                game.setScreen(new SelectPlayerScreen(game, skin));
                break;

            case "Poursuivre":
                // Charger la sauvegarde
                if (SaveManager.hasSave()) {
                    game.loadGame();
                } else {
                    System.out.println("Aucune sauvegarde disponible !");
                }
                break;

            case "Continuer":
                this.activated = !this.activated;
                break;

            case "Parametres":
                this.settings = !this.settings;
                break;

            case "Exit":
                Gdx.app.exit();
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

 public boolean isActivated() {
        return activated;
    }

    public Stage getStage() {
        return stage;
    }

    public void setActivated(boolean a) {
        this.activated = a;
    }

    public boolean isSettings() {
        return settings;
    }

    public void dpMenu() {
        stage.dispose();
        skin.dispose();
    }

    public void rsMenu(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void shMenu() { }
    public void pMenu() { }
    public void rMenu() { }
    public void hMenu() { }
}
