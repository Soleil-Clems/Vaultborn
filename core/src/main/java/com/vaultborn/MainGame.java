package com.vaultborn;

import com.badlogic.gdx.Game;
//import com.vaultborn.screens.GameScreen;
//import com.vaultborn.screens.MenuScreen;
import com.vaultborn.entities.characters.players.Player;
import com.vaultborn.entities.stuff.GameObject;
import com.vaultborn.entities.stuff.trigger.SpecialDoor;
import com.vaultborn.screens.GameScreen;
import com.vaultborn.screens.MainScreen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.vaultborn.world.BaseWorld;
import com.vaultborn.world.ForestWorld;
import com.vaultborn.world.HellWorld;

public class MainGame extends Game {
    private static Texture whitePixel;
    private static BitmapFont font;
    public BaseWorld hellWorld;
    public BaseWorld forestWorld;
    public Player player;
    public BaseWorld currentWorld;

    @Override
    public void create() {
        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(1, 1, 1, 1);
        pix.fill();
        whitePixel = new Texture(pix);
        pix.dispose();

        font = new BitmapFont();
        font.getData().markupEnabled = true;
        font.setUseIntegerPositions(false);

//        hellWorld = new HellWorld(this);
//        forestWorld = new ForestWorld(this);
//        hellWorld.linkWorlds();
//        forestWorld.linkWorlds();

        setScreen(new MainScreen(this));
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


    public void setWorld(BaseWorld newWorld) {
        setScreen(new GameScreen(this, newWorld));
    }

}
