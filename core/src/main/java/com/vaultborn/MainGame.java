package com.vaultborn;

import com.badlogic.gdx.Game;
import com.vaultborn.screens.GameScreen;
import com.vaultborn.screens.MenuScreen;

public class MainGame extends Game {

    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }

}
