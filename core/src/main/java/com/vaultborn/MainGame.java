package com.vaultborn;

import com.badlogic.gdx.Game;
import com.vaultborn.screens.GameScreen;

public class MainGame extends Game {

    @Override
    public void create() {
        setScreen(new GameScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        getScreen().dispose();
    }
}
