package com.vaultborn.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.Actor;

import com.vaultborn.MainGame;

import java.util.List;


public abstract class AbsMenu implements Screen{
    
    protected MainGame game;
    protected Stage stage;
    protected Skin skin;
    protected List<String> element;


    public AbsMenu(MainGame game,String typeMenu,Skin skin,List<String> element){
        this.game = game;
        this.skin = skin;
        this.element = element;
        stage = new Stage(new ScreenViewport());
        //initie les inputs
        Gdx.input.setInputProcessor(stage);
        //System.out.println(Gdx.files.internal(skin).exists());
        switch (typeMenu) {
            case "Menu":
                Table table = new Table();
                table.setFillParent(true);
                stage.addActor(table);
                for (String e : element){
                    TextButton Button = new TextButton(e, skin);
                    table.add(Button).pad(10).width(200).row();
                
                    Button.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                        System.out.println("Bouton : " + e);
                        onClick(e);

                        }
                    });
                }
                break;
        
            default:
                break;
        }
    
    }
    protected abstract void onClick(String name);

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); skin.dispose(); }
}
