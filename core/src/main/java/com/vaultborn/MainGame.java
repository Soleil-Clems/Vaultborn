package com.vaultborn;

import com.badlogic.gdx.Game;
//import com.vaultborn.screens.GameScreen;
//import com.vaultborn.screens.MenuScreen;
import com.vaultborn.screens.GameScreen;
import com.vaultborn.screens.MainScreen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.vaultborn.world.BaseWorld;

public class MainGame extends Game {
    private static Texture whitePixel;
    private static BitmapFont font;

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
        super.dispose();
    }

    public void setWorld(BaseWorld newWorld) {
        setScreen(new GameScreen(this, newWorld));
    }

}
