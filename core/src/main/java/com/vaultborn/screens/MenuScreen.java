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

import com.vaultborn.MainGame;
import com.vaultborn.world.BaseWorld;
import com.vaultborn.world.HellWorld;
import com.vaultborn.world.ForestWorld;

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


    public MenuScreen(MainGame game,Skin skin,List<String> element){
        createMenu(game, skin, element);
        game.getBackgroundMusic().play();
    }

    public Music getBackgroundMusic(){
        return this.backgroundMusic;
    }
    
    public boolean isActivated(){
        return activated;
    }
    public Stage getStage(){
        return stage;
    }
    public void setActivated(boolean a){
        this.activated = a;
    }
    public boolean isSettings(){
        return settings;
    }
    public void setSettings(boolean a){
        this.settings = a;
    }

    public void reloadMenu(MainGame game,Skin skin,List<String> element){
        createMenu(game, skin, element);
        this.settings = true;
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
        for (String e : element){
            TextButton Button = new TextButton(e, skin);
            Button.getLabel().setFontScale(2f);
            table.add(Button).pad(10).width(500).height(Gdx.graphics.getHeight()*0.2f).row();
        
            Button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Bouton : " + e);
                onClick(e);
                }
                });
        }    
    }



    public void onClick(String name) {
        switch (name) {
            case "Jouer":
//                BaseWorld world = new HellWorld();
//                BaseWorld world = new ForestWorld(game);
//                game.setScreen(new GameScreen(game, world));

                game.setScreen(new SelectPlayerScreen(game, skin));

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
