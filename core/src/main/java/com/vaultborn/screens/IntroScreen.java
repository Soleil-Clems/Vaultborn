package com.vaultborn.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.vaultborn.MainGame;

public class IntroScreen implements Screen {

    private final MainGame game;
    private final SpriteBatch batch;
    private final Texture intro1;
    private final Texture intro2;
    private final Texture intro3;
    private boolean showIntro2 = false;
    private boolean showIntro3 = false;

    private Stage stage;
    private Label textLabel;
    private String textIntro1 =
        "Dans les profondeurs oubliées du Vault, loin de toute communication avec le monde réel, un coffre scellé s’ouvre enfin. " +
            "Une petite créature en émerge, née d’une lumière qu’elle ne comprend pas.";

    private String textIntro2 =
        "À peine éveillée, elle aperçoit une épée ancienne, attiré par sa puissance, la petite creature tente de s'en approcher...";

    private String textIntro3 =
        "Mais une ombre surgit. Tengu, l'un des terribles Seigneurs du Vault, s’empare de l’arme avant elle. Trop faible et apeurée, la créature fuit… " +
            "avec un seul but : revenir un jour la reprendre.";


    public IntroScreen(MainGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.intro1 = new Texture(Gdx.files.internal("introscreen/intro1.png"));
        this.intro2 = new Texture(Gdx.files.internal("introscreen/intro2.jpg"));
        this.intro3 = new Texture(Gdx.files.internal("introscreen/intro3.jpg"));

        stage = new Stage(new ScreenViewport());
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        labelStyle.fontColor = Color.WHITE;

        textLabel = new Label(textIntro1, labelStyle);
        textLabel.setFontScale(2.0f);
        textLabel.setWrap(true); // ⚠ indispensable pour que le texte revienne à la ligne
        textLabel.setAlignment(1); // 1 = center horizontally


        Table table = new Table();
        table.setFillParent(true);
        table.top();
        table.add(textLabel)
            .width(Gdx.graphics.getWidth() * 0.9f)  // marge de sécurité
            .center()
            .padBottom(50);

        stage.addActor(table);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // --- Affichage des images ---
        batch.begin();
        if (!showIntro2) {
            batch.draw(intro1, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        else if (!showIntro3) {
            batch.draw(intro2, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        else {
            batch.draw(intro3, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        batch.end();

        // --- UI ---
        stage.act(delta);
        stage.draw();

        // --- Gestion des transitions ---
        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY) || Gdx.input.justTouched()) {

            // 1 → 2
            if (!showIntro2) {
                showIntro2 = true;
                textLabel.setText(textIntro2);
            }

            // 2 → 3
            else if (!showIntro3) {
                showIntro3 = true;
                textLabel.setText(textIntro3);
            }

            // 3 → fin
            else {
                game.setScreen(new MainScreen(game));
                dispose();
            }
        }
    }


    @Override
    public void dispose() {
        batch.dispose();
        intro1.dispose();
        intro2.dispose();
        stage.dispose();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
}
