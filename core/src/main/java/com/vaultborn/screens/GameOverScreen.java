package com.vaultborn.screens;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.vaultborn.MainGame;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class GameOverScreen implements Screen{
    private MainGame game;
    private Stage stage;
    private Skin skin = new Skin(Gdx.files.internal("menu/neon/skin/neon-ui.json"));
    private Table table;

    private BitmapFont txtFont;
    private BitmapFont btnFont;

    public GameOverScreen(MainGame game,Skin theSkin){
        this.skin = theSkin;
        this.game = game;
        stage = new Stage(new ScreenViewport());

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        //taille txt
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("menu/gameOverFont/Outline Style.ttf"));

        FreeTypeFontParameter txt = new FreeTypeFontParameter();
        txt.size = 80;
        txt.color = Color.WHITE;
        txt.borderWidth = 3;
        txt.borderColor = Color.BLACK;

        txtFont = gen.generateFont(txt);

        FreeTypeFontParameter btn = new FreeTypeFontParameter();
        btn.size = 40;
        btn.color = Color.WHITE;

        btnFont = gen.generateFont(btn);

        gen.dispose();

        Label.LabelStyle goStyle = new Label.LabelStyle(txtFont, Color.RED);
        Label.LabelStyle txtStyle = new Label.LabelStyle(btnFont, Color.WHITE);
        /*TextButton.TextButtonStyle btnStyle = new TextButton.TextButtonStyle();
        btnStyle.font = btnFont;
        btnStyle.up = skin.getDrawable("button");
        btnStyle.over = skin.getDrawable("button-over-c");
        btnStyle.down = skin.getDrawable("button-pressed-c");*/


        Label gameOver = new Label("Game Over", goStyle);
        Label gameOverMessage = new Label("Le Vault a eu raison de toi ...", txtStyle);
        TextButton btnMainMenu = new TextButton("Menu principal", skin);
        btnMainMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x,float y) {
            game.setScreen(new MainScreen(game));
            }
        });

        table.add(gameOver);
        table.row();
        table.add(gameOverMessage);
        table.row();
        table.add(btnMainMenu).pad(10).width(500).height(Gdx.graphics.getHeight()*0.2f);


    }

    @Override public void render(float delta) {
        Gdx.input.setInputProcessor(stage);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) {

    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void show() {}
    @Override public void dispose() {

    }



}
