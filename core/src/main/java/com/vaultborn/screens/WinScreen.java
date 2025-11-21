package com.vaultborn.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.vaultborn.MainGame;

public class WinScreen implements Screen {
    private MainGame game;
    private Stage stage;
    private Skin skin;
    private Table table;

    private BitmapFont titleFont;
    private BitmapFont messageFont;
    private BitmapFont buttonFont;

    private Texture background;
    private float time = 0f;

    public WinScreen(MainGame game, Skin skin) {
        this.skin = skin;
        this.game = game;

        stage = new Stage(new ScreenViewport());

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        try {
            background = new Texture("backgrounds/screenbg.png");
        } catch (Exception e) {
            background = null;
        }

        createFonts();
        createUI();
    }

    private void createFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
            Gdx.files.internal("menu/gameOverFont/Outline Style.ttf")
        );

        FreeTypeFontParameter titleParam = new FreeTypeFontParameter();
        titleParam.size = 120;
        titleParam.color = Color.GOLD;
        titleParam.borderWidth = 4;
        titleParam.borderColor = Color.valueOf("8B4513");
        titleParam.shadowOffsetX = 3;
        titleParam.shadowOffsetY = 3;
        titleParam.shadowColor = new Color(0, 0, 0, 0.7f);
        titleFont = generator.generateFont(titleParam);

        FreeTypeFontParameter messageParam = new FreeTypeFontParameter();
        messageParam.size = 32;
        messageParam.color = Color.WHITE;
        messageParam.borderWidth = 2;
        messageParam.borderColor = new Color(0, 0, 0, 0.8f);
        messageParam.shadowOffsetX = 2;
        messageParam.shadowOffsetY = 2;
        messageParam.shadowColor = new Color(0, 0, 0, 0.5f);
        messageFont = generator.generateFont(messageParam);

        FreeTypeFontParameter buttonParam = new FreeTypeFontParameter();
        buttonParam.size = 40;
        buttonParam.color = Color.WHITE;
        buttonParam.borderWidth = 1;
        buttonParam.borderColor = Color.BLACK;
        buttonFont = generator.generateFont(buttonParam);

        generator.dispose();
    }

    private void createUI() {
        Table container = new Table();
        container.setBackground(createSemiTransparentBackground());
        container.setTouchable(Touchable.childrenOnly);
        container.pad(50);

        Label.LabelStyle titleStyle = new Label.LabelStyle(titleFont, Color.GOLD);
        Label titleLabel = new Label("VICTOIRE", titleStyle);
        titleLabel.setAlignment(Align.center);

        titleLabel.addAction(Actions.forever(
            Actions.sequence(
                Actions.scaleTo(1.1f, 1.1f, 0.8f),
                Actions.scaleTo(1.0f, 1.0f, 0.8f)
            )
        ));

        Label separator = new Label("★ ═══════════════ ★", new Label.LabelStyle(messageFont, Color.GOLD));
        separator.setAlignment(Align.center);

        Label.LabelStyle messageStyle = new Label.LabelStyle(messageFont, Color.WHITE);
        Label messageLabel = new Label("Tu as vaincu le grand Tengu !\nFelicitations, Heros !", messageStyle);
        messageLabel.setAlignment(Align.center);
        messageLabel.setWrap(true);

        Table buttonTable = new Table();
        buttonTable.defaults().pad(10).width(300).height(80);

        TextButton retryButton = new TextButton("Rejouer", skin);
        retryButton.getLabel().setFontScale(1.3f);
        retryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SelectPlayerScreen(game, skin));
            }
        });

        TextButton menuButton = new TextButton("Menu principal", skin);
        menuButton.getLabel().setFontScale(1.3f);
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainScreen(game));
            }
        });

        TextButton quitButton = new TextButton("Quitter", skin);
        quitButton.getLabel().setFontScale(1.3f);
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        buttonTable.add(retryButton).row();
        buttonTable.add(menuButton).row();
        buttonTable.add(quitButton);

        container.add(titleLabel).padBottom(20).row();
        container.add(separator).padBottom(30).width(600).row();
        container.add(messageLabel).width(700).padBottom(20).row();
        container.add(buttonTable);

        table.add(container);

        container.setColor(1, 1, 1, 0);
        container.addAction(Actions.fadeIn(1.0f));
    }

    private com.badlogic.gdx.scenes.scene2d.utils.Drawable createSemiTransparentBackground() {
        com.badlogic.gdx.graphics.Pixmap pixmap = new com.badlogic.gdx.graphics.Pixmap(1, 1,
            com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888);
        pixmap.setColor(0.1f, 0.1f, 0.15f, 0.85f);
        pixmap.fill();

        com.badlogic.gdx.graphics.Texture texture = new com.badlogic.gdx.graphics.Texture(pixmap);
        pixmap.dispose();

        return new com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable(
            new com.badlogic.gdx.graphics.g2d.TextureRegion(texture)
        );
    }

    @Override
    public void render(float delta) {
        // CORRECTION PRINCIPALE : setInputProcessor dans render() comme GameOverScreen
        Gdx.input.setInputProcessor(stage);

        time += delta;

        float pulse = (float) Math.sin(time * 2f) * 0.1f + 0.9f;
        Gdx.gl.glClearColor(0.05f * pulse, 0.05f * pulse, 0.1f * pulse, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (background != null) {
            stage.getBatch().begin();
            stage.getBatch().setColor(0.5f, 0.5f, 0.5f, 0.3f);
            stage.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            stage.getBatch().setColor(Color.WHITE);
            stage.getBatch().end();
        }

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {}

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        stage.dispose();
        titleFont.dispose();
        messageFont.dispose();
        buttonFont.dispose();
        if (background != null) {
            background.dispose();
        }
    }
}
