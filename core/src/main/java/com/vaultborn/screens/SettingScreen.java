package com.vaultborn.screens;

import java.util.LinkedHashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;

import com.vaultborn.MainGame;




public class SettingScreen {
      
    protected MainGame game;
    protected Stage stage;
    protected Skin Globalskin = new Skin(Gdx.files.internal("menu/neon/skin/neon-ui.json"));
    private boolean activated;
    private Music backgroundMusic;
    private Table SkinTable;
    private Table SliderTable;
    private Table bottomRightTable;
    LinkedHashMap<String,Skin> SkinList = new LinkedHashMap<String,Skin>(){{
        put("neon",new Skin(Gdx.files.internal("menu/neon/skin/neon-ui.json")));
        put("comic",new Skin(Gdx.files.internal("menu/comic/skin/comic-ui.json")));
        put("craft",new Skin(Gdx.files.internal("menu/craftacular/skin/craftacular-ui.json")));
        put("orange",new Skin(Gdx.files.internal("menu/orange/skin/uiskin.json")));
        put("quantum",new Skin(Gdx.files.internal("menu/quantum-horizon/skin/quantum-horizon-ui.json")));
        put("rainbow",new Skin(Gdx.files.internal("menu/rainbow/skin/rainbow-ui.json")));
        put("star",new Skin(Gdx.files.internal("menu/star-soldier/skin/star-soldier-ui.json")));
        put("tracer",new Skin(Gdx.files.internal("menu/tracer/skin/tracer-ui.json")));
    }};


    
    
    
    
    
    
    
    
    


    public SettingScreen(MainGame game,Skin skin){

        System.out.println(Gdx.files.internal("menu/neon/skin/neon-ui.json").exists());
        System.out.println(Gdx.files.internal("menu/comic/skin/comic-ui.json").exists());
        System.out.println(Gdx.files.internal("menu/craftacular/skin/craftacular-ui.json").exists());
        System.out.println(Gdx.files.internal("menu/orange/skin/uiskin.json").exists());
        System.out.println(Gdx.files.internal("menu/quantum-horizon/skin/quantum-horizon-ui.json").exists());
        System.out.println(Gdx.files.internal("menu/rainbow/skin/rainbow-ui.json").exists());
        System.out.println(Gdx.files.internal("menu/star-soldier/skin/star-soldier-ui.json").exists());
        System.out.println(Gdx.files.internal("menu/tracer/skin/tracer-ui.json").exists());
        
        this.game = game;
        this.Globalskin = skin;
        this.backgroundMusic = game.getBackgroundMusic();
        this.activated = false;
        stage = new Stage(new ScreenViewport());
        //initie les inputs
        Gdx.input.setInputProcessor(stage);
        //System.out.println(Gdx.files.internal(skin).exists());
        SkinTable = new Table();
        SliderTable = new Table();
        bottomRightTable = new Table();
        activateElement();
        

    }

    public void activateElement(){
        SkinTable.clear();
        SliderTable.clear();
        bottomRightTable.clear();
        //ensemble des table
        
        SkinTable.top();
        stage.addActor(SkinTable);
        SkinTable.setFillParent(true);

        //gestion des skins de bouton
        Table txtSkin = new Table();
        txtSkin.top().padTop(20);
        SkinTable.add(txtSkin).padBottom(10).row();
        Label txtSkinChanging = new Label("Changer le style des boutons ",Globalskin);
        txtSkinChanging.setFontScale(1.5f);
        txtSkinChanging.setColor(1, 1, 1, 1);
        txtSkin.add(txtSkinChanging);
        //SkinTable.setDebug(true);
        
        Table btnSkin = new Table();
        for (String skinName : SkinList.keySet()) {
            TextButton btnText = createUniformButton(skinName, Globalskin);
            //TextButton btnText = new TextButton(skinName, Globalskin);
            btnText.getLabel().setFontScale(1.5f);

            btnText.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    System.out.println("Skin choisi : " + skinName);
                    Globalskin = SkinList.get(skinName);
                    activateElement();
                    System.out.println(Globalskin);
                }
            });
            btnSkin.add(btnText).width(250).height(50).pad(5).row();
        }
        
        ScrollPane scrollPane = new ScrollPane(btnSkin,Globalskin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setForceScroll(false, true); 
        SkinTable.add(scrollPane).width(300).height(250).row();;


        //slider musique
        Table txtSlider = new Table();
        txtSlider.padTop(10);
        SkinTable.add(txtSlider).padBottom(10).row();
        Label txtSkinSlider = new Label("volume de la musique",Globalskin);
        txtSkinSlider.setFontScale(1.5f);
        txtSkinSlider.setColor(1, 1, 1, 1);
        txtSlider.add(txtSkinSlider).row();

        Slider volumeSlider = new Slider(0, 1, 0.1f, false, Globalskin);
        volumeSlider.setValue(backgroundMusic.getVolume());
        txtSlider.add(volumeSlider);
        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                double volumeDouble = Math.round(volumeSlider.getValue()*10)/10.0;
                float volumeFloat= (float) volumeDouble;
                if(backgroundMusic != null){
                    backgroundMusic.setVolume(volumeFloat);
                }

                System.out.println(volumeSlider.getValue());

            }
        });

        //stage.addActor(SliderTable);
        //SliderTable.setFillParent(true);
        //SliderTable.top().padTop(300).center();

        //retour bouton
        
        bottomRightTable.setFillParent(true);
        bottomRightTable.bottom().right();
        
        
        TextButton Button = new TextButton("Retour", Globalskin);
            Button.getLabel().setFontScale(2f);
            bottomRightTable.add(Button).left().pad(10).width(500).height(Gdx.graphics.getHeight()*0.2f);
        
            Button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Bouton : " + "Retour");
                onClick("Retour");
                }
                });

        stage.addActor(bottomRightTable);
    }

    private TextButton createUniformButton(String text, Skin skin) {

        TextButton.TextButtonStyle base = skin.get(TextButton.TextButtonStyle.class);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = base.up;
        style.down = base.down;
        style.over = base.over;
        style.checked = base.checked;
        style.disabled = base.disabled;
        style.font = skin.getFont("font");
        style.font.getData().setScale(1.3f);

        style.fontColor = base.fontColor;
        style.downFontColor = base.downFontColor;
        style.overFontColor = base.overFontColor;
        style.disabledFontColor = base.disabledFontColor;

        style.font.getData().setScale(1.3f);

        TextButton button = new TextButton(text, style);

        button.pad(10);

        return button;
    }

    
public boolean isActivated(){
        return activated;
    }
    public Stage getStage(){
        return stage;
    }
    public void setActivated(boolean a){
        this.activated = a;
    }

    public Skin getSkin(){
        return this.Globalskin;
    }

    public void onClick(String name){
        switch (name) {
            case"Retour":
                this.activated = false;
                break;
            default:
                break;
        }
    }
    public void rdMenu(float delta) {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            stage.act(delta);
            stage.draw();
        
    }
    public void dpMenu(){stage.dispose(); Globalskin.dispose();}   

    public void rsMenu(int width, int height) { stage.getViewport().update(width, height, true);}
    public void shMenu() {}
    public void pMenu() {}
    public void rMenu() {}
    public void hMenu() {}
}