package com.vaultborn;

import com.badlogic.gdx.Game;
//import com.vaultborn.screens.GameScreen;
//import com.vaultborn.screens.MenuScreen;
import com.vaultborn.screens.MainScreen;

public class MainGame extends Game {

    @Override
    public void create() {
        setScreen(new MainScreen(this));
    }

}
