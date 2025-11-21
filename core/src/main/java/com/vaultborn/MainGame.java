package com.vaultborn;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
//import com.vaultborn.screens.GameScreen;
//import com.vaultborn.screens.MenuScreen;
import com.vaultborn.entities.characters.players.Player;
import com.vaultborn.entities.stuff.GameObject;
import com.vaultborn.entities.stuff.Item;
import com.vaultborn.entities.stuff.Stuff;
import com.vaultborn.entities.stuff.trigger.SpecialDoor;
import com.vaultborn.factories.Factory;
import com.vaultborn.managers.InputManager;
import com.vaultborn.factories.FactoryException;

import com.vaultborn.save.SaveData;
import com.vaultborn.save.SaveManager;
import com.vaultborn.screens.GameScreen;
import com.vaultborn.screens.InventoryPlayer;
import com.vaultborn.screens.MainScreen;
import com.vaultborn.screens.IntroScreen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.vaultborn.world.BaseWorld;
import com.vaultborn.world.ForestWorld;
import com.vaultborn.world.HellWorld;
import com.vaultborn.screens.IntroScreen;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainGame extends Game {
    private static Texture whitePixel;
    private static BitmapFont font;

    private Music backgroundMusic;

    public BaseWorld hellWorld;
    public BaseWorld forestWorld;
    public Player player;
    private Skin btnSkin;
    public BaseWorld currentWorld;

    public InputManager inputManager;


    public Music getBackgroundMusic(){
        return this.backgroundMusic;
    }


    @Override
    public void create() {
        inputManager = new InputManager();
        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(1, 1, 1, 1);
        pix.fill();
        whitePixel = new Texture(pix);
        pix.dispose();

        font = new BitmapFont();
        font.getData().markupEnabled = true;
        font.setUseIntegerPositions(false);


        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/land_of_snow_background_music.mp3"));

        btnSkin = new Skin(Gdx.files.internal("menu/neon/skin/neon-ui.json"));

        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();

//        hellWorld = new HellWorld(this);
//        forestWorld = new ForestWorld(this);
//        hellWorld.linkWorlds();
//        forestWorld.linkWorlds();

        setScreen(new IntroScreen(this));
    }

    public static Texture getWhitePixel() {
        return whitePixel;
    }

    public static BitmapFont getFont() {
        return font;
    }

    @Override
    public void dispose() {
        if (whitePixel != null) whitePixel.dispose();
        if (font != null) font.dispose();
        if (hellWorld != null) hellWorld.dispose();
        if (forestWorld != null) forestWorld.dispose();
        super.dispose();
    }


    public void setWorld(BaseWorld newWorld,Skin skin) {
        setScreen(new GameScreen(this, newWorld,skin));
    }

    public void saveGame() {
        if (player == null || currentWorld == null) {
            return;
        }

        SaveData data = new SaveData();


        data.worldName = currentWorld.levelName;


        data.hp = player.getHp();
        data.maxHp = player.getMaxHp();
        data.level = player.getLevel();
        data.damage = player.getDamage();
        data.defense = player.getDefense();
        data.agility = player.getAgility();


        data.playerClass = player.getClass().getSimpleName().toLowerCase();


        data.playerX = player.getPosition().x;
        data.playerY = player.getPosition().y;


        SaveManager.save(data);
    }

    public void loadGame() throws FactoryException {
        SaveData data = SaveManager.load();
        if (data == null) {
            return;
        }



        forestWorld = new ForestWorld(this);
        hellWorld = new HellWorld(this);


        if (data.worldName.contains("Hell") || data.worldName.contains("hell")) {
            currentWorld = hellWorld;
        } else {
            currentWorld = forestWorld;
        }


        forestWorld.linkWorlds();
        hellWorld.linkWorlds();


        Factory factory = new Factory();
        player = factory.createPlayer(data.playerClass, currentWorld.spawnX, currentWorld.spawnY, currentWorld);
        this.player.setInput(this.inputManager.allInput());

        player.setHp(data.hp);
        player.setMaxHp(data.maxHp);
        player.setLevel(data.level);
        player.setDamage(data.damage);
        player.setDefense(data.defense);
        player.setAgility(data.agility);


        currentWorld.setPlayer(player);
        this.player = player;

        InventoryPlayer inv = new InventoryPlayer(false);
        player.setInventory(inv);
        inv.setPlayer(player);

        setScreen(new GameScreen(this, currentWorld, btnSkin));

    }
}
