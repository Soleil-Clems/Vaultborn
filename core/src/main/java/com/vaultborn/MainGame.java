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
import com.vaultborn.save.SaveData;
import com.vaultborn.save.SaveManager;
import com.vaultborn.screens.GameScreen;
import com.vaultborn.screens.InventoryPlayer;
import com.vaultborn.screens.MainScreen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.vaultborn.world.BaseWorld;
import com.vaultborn.world.ForestWorld;
import com.vaultborn.world.HellWorld;

import java.util.LinkedHashMap;
import java.util.Map;

public class MainGame extends Game {
    private static Texture whitePixel;
    private static BitmapFont font;

    private Music backgroundMusic;

    public BaseWorld hellWorld;
    public BaseWorld forestWorld;
    public Player player;
    public BaseWorld currentWorld;


    
    public Music getBackgroundMusic(){
        return this.backgroundMusic;
    }


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


        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/land_of_snow_background_music.mp3"));
        
        // 2. Définir si la musique doit boucler (loop)
        backgroundMusic.setLooping(true);
        
        // 3. (Optionnel) Définir le volume (entre 0.0 et 1.0)
        backgroundMusic.setVolume(0.5f); 
        
        // 4. Lancer la lecture
        backgroundMusic.play();

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


    public void setWorld(BaseWorld newWorld,Skin skin) {
        setScreen(new GameScreen(this, newWorld,skin));
    }

    public void saveGame() {
        if (player == null || currentWorld == null) {
           System.out.println("Player or CurrentWorld is null");
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

        // Sauvegarder l'inventaire (si vous voulez l'activer plus tard)
        // LinkedHashMap<Item<? extends Stuff>, Integer> inv = player.getInventory().getInventory();
        // for (Map.Entry<Item<? extends Stuff>, Integer> entry : inv.entrySet()) {
        //     Item<? extends Stuff> item = entry.getKey();
        //     int quantity = entry.getValue();
        //     data.inventory.add(item.getObject().getName() + ":" + quantity);
        // }

        SaveManager.save(data);
        System.out.println("Partie sauvegardee ! "+ data.playerClass + " - " + data.playerX + " - " + data.playerY + " - "+ data.worldName);
    }

    public void loadGame() {
        SaveData data = SaveManager.load();
        if (data == null) {
            System.out.println("Aucune sauvegarde trouvee !");
            return;
        }

        System.out.println("Chargement de la sauvegarde : " + data.worldName);


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
//        player = factory.createPlayer("warrior", currentWorld.spawnX, currentWorld.spawnY, currentWorld);

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

        setScreen(new GameScreen(this, currentWorld));

    }
}
