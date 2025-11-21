package com.vaultborn.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.vaultborn.MainGame;
import com.vaultborn.factories.Factory;
import com.vaultborn.factories.FactoryException;
import com.vaultborn.world.ForestWorld;
import com.vaultborn.world.HellWorld;

public class SelectPlayerScreen implements Screen {

    private final MainGame game;
    private final Stage stage;
    private final Skin skin;
    private Factory factory;
    private Table selectedCard = null;
    private TextureRegionDrawable cardBackground;
    private TextureRegionDrawable portraitBackground;

    public SelectPlayerScreen(MainGame game, Skin skin) {
        this.game = game;
        this.skin = skin;
        factory = new Factory();

        createCustomBackgrounds();

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        Label title = new Label("CHOISISSEZ VOTRE HEROS", skin);
        title.setFontScale(2.0f);
        title.setColor(Color.GOLD);
        root.add(title).padTop(20).padBottom(10).row();

        Label subtitle = new Label("Selectionnez un personnage pour commencer", skin);
        subtitle.setFontScale(0.9f);
        subtitle.setColor(new Color(0.8f, 0.8f, 0.8f, 1f));
        root.add(subtitle).padBottom(15).row();

        Table grid = new Table();
        grid.defaults().pad(10);

        grid.add(createCharacterCard("Lancelot", "warrior", "Chevalier Noble")).width(200).height(240);
        grid.add(createCharacterCard("Juzo", "darkwarrior", "Guerrier Sombre")).width(200).height(240);
        grid.row();

        grid.add(createCharacterCard("Perceval", "archer", "Archer Precis")).width(200).height(240);
        grid.add(createCharacterCard("Mordred", "darkmage", "Mage Obscur")).width(200).height(240);
        grid.row();

        grid.add(createCharacterCard("Lisa", "lightmage", "Mage Lumineuse")).width(200).height(240);
        grid.add(createCharacterCard("Merline", "satyr", "Satyre Mystique")).width(200).height(240);
        grid.row();

        grid.add(createCharacterCard("Monet", "sunmage", "Mage Solaire")).width(200).height(240).colspan(2);

        ScrollPane scrollPane = new ScrollPane(grid, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setOverscroll(false, false);

        root.add(scrollPane).expand().fill().pad(10);
    }

    private void createCustomBackgrounds() {
        Pixmap cardPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        cardPixmap.setColor(0.15f, 0.15f, 0.2f, 0.9f);
        cardPixmap.fill();
        cardBackground = new TextureRegionDrawable(new TextureRegion(new Texture(cardPixmap)));
        cardPixmap.dispose();

        Pixmap portraitPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        portraitPixmap.setColor(0.05f, 0.05f, 0.1f, 1f);
        portraitPixmap.fill();
        portraitBackground = new TextureRegionDrawable(new TextureRegion(new Texture(portraitPixmap)));
        portraitPixmap.dispose();
    }

    private Table createCharacterCard(String name, String classKey, String description) {
        Table card = new Table();
        card.setBackground(cardBackground);

        TextureRegion region = getFirstIdleFrame(classKey);
        Image portrait = new Image(region);
        portrait.setScaling(Scaling.fit);

        Table portraitContainer = new Table();
        portraitContainer.setBackground(portraitBackground);

        Stack portraitStack = new Stack();
        portraitStack.add(portraitContainer);

        Table portraitInner = new Table();
        portraitInner.add(portrait).size(100, 100);
        portraitStack.add(portraitInner);

        Label nameLabel = new Label(name, skin);
        nameLabel.setFontScale(1f);
        nameLabel.setColor(Color.WHITE);

        Label descLabel = new Label(description, skin);
        descLabel.setFontScale(1.0f);
        descLabel.setColor(new Color(0.7f, 0.7f, 0.7f, 1f));
        descLabel.setWrap(true);
        descLabel.setAlignment(1);

        card.pad(1);
        card.add(portraitStack).size(100, 100).padBottom(2).row();
        card.add(nameLabel).padTop(5).row();
        card.add(descLabel).width(120).padTop(2).row();

        card.addListener(new ClickListener() {
            private Color originalColor = new Color(0.15f, 0.15f, 0.2f, 0.9f);
            private Color hoverColor = new Color(0.3f, 0.25f, 0.15f, 0.95f);
            private Color selectedColor = new Color(0.4f, 0.35f, 0.1f, 1f);

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (pointer == -1 && selectedCard != card) {
                    updateCardColor(card, hoverColor);
                    nameLabel.setColor(Color.GOLD);
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1 && selectedCard != card) {
                    updateCardColor(card, originalColor);
                    nameLabel.setColor(Color.WHITE);
                }
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedCard != null && selectedCard != card) {
                    updateCardColor(selectedCard, originalColor);
                    Label oldLabel = (Label) selectedCard.getChildren().get(1);
                    oldLabel.setColor(Color.WHITE);
                }

                selectedCard = card;
                updateCardColor(card, selectedColor);
                nameLabel.setColor(Color.ORANGE);

                stage.addAction(com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence(
                    com.badlogic.gdx.scenes.scene2d.actions.Actions.delay(0.01f),
                    com.badlogic.gdx.scenes.scene2d.actions.Actions.run(() -> {
                        try {
                            startGame(classKey);
                        } catch (FactoryException e) {
                            throw new RuntimeException(e);
                        }
                    })
                ));
            }
        });

        return card;
    }

    private void updateCardColor(Table card, Color color) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        card.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pixmap))));
        pixmap.dispose();
    }

    private TextureRegion getFirstIdleFrame(String classKey) {
        String path = classKey + "/Idle.png";
        Texture texture = new Texture(Gdx.files.internal(path));

        int frameWidth = texture.getWidth() / 8;
        int frameHeight = texture.getHeight();

        TextureRegion[][] tmp = TextureRegion.split(texture, frameWidth, frameHeight);
        return tmp[0][0];
    }

    private void startGame(String classKey) throws FactoryException {
        game.forestWorld = new ForestWorld(game);
        game.hellWorld = new HellWorld(game);

        game.player = factory.createPlayer(classKey, 250, 800, game.forestWorld);
        game.forestWorld.setPlayer(game.player);
        game.player.setInput(game.inputManager.allInput());

        game.forestWorld.linkWorlds();
        game.hellWorld.linkWorlds();

        InventoryPlayer inv = new InventoryPlayer(false);
        game.player.setInventory(inv);
        inv.setPlayer(game.player);

        game.setScreen(new GameScreen(game, game.forestWorld,skin));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.08f, 0.08f, 0.12f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void show() { }
    @Override public void hide() { }
    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void dispose() {
        stage.dispose();
    }
}
