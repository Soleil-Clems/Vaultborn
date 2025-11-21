package com.vaultborn.screens;

import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.vaultborn.MainGame;

public class IntroScreen implements Screen {

    private final MainGame game;
    private final Texture intro1;
    private final Texture intro2;
    private final Texture intro3;
    private Label.LabelStyle style;
    private int whichPic;
    private Stage stage;
    private Table table;
    private Table tableBtn;
    private Skin skin;
    private TextButton btn;

    private String textIntro1P1 =
        "Quelque part, dans un lieu ou toute liaison avec le monde reel est impossible…";
        
    private String textIntro1P2 =
        "quelque chose se prepare. Un nouvel individu naquit dans le Vault.";
    private String textIntro1P3 =
        "Guide par ses instincts, il ouvrit un coffre et chercha a se renforcer";
    private String textIntro1P4 =
        "au plus vite, au plus profond du Vault.";

    private String textIntro2P1 =
        "C'est alors qu'il apercut une epee legendaire. Il s'approcha de l'epee,";
    private String textIntro2P2 =
        "tendit la main…";
        
    private String textIntro3P1 =
        "mais une entite bien plus rapide s'en empara avant lui. Il tenta de l'attaquer,";
    private String textIntro3P2 =
        "mais... mais... mais...";
    private String textIntro3P3 =
        "rien. L'entite ne broncha pas, comme si aucune attaque ne pouvait l'atteindre.";
    private String textIntro3P4 =
        "Terrifie, il decida de fuir vers la surface pour devenir plus fort...";
    private String textIntro3P5 =
        "et revenir un jour affronter cette entite au nom de Tengu";

    public IntroScreen(MainGame game){
        this.game = game;
        this.intro1 = new Texture(Gdx.files.internal("introScreen/intro1.png"));
        this.intro2 = new Texture(Gdx.files.internal("introScreen/intro2.jpg"));
        this.intro3 = new Texture(Gdx.files.internal("introScreen/intro3.jpg"));
        this.skin = new Skin(Gdx.files.internal("menu/neon/skin/neon-ui.json"));
        style = new Label.LabelStyle(skin.get("default",Label.LabelStyle.class));
        style.font = skin.getFont("font");
        style.fontColor= Color.valueOf("#ffffffff");
        style.font.getData().setScale(2f);
        this.whichPic = 1;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        stage.addActor(table);
        table.setFillParent(true);
        table.top();
        
        
        tableBtn = new Table();
        stage.addActor(tableBtn);
        tableBtn.setFillParent(true);
        tableBtn.bottom().right();
        btn = new TextButton("Suivant", skin);
        tableBtn.add(btn);
    }

    private void showTxt1(){
        table.clear();
        table.setFillParent(true);
        table.add(new Label(textIntro1P1, style)).fill().row();
        table.add(new Label(textIntro1P2, style)).fill().row();
        table.add(new Label(textIntro1P3, style)).fill().row();
        table.add(new Label(textIntro1P4, style)).fill().row();
        table.setBackground(new TextureRegionDrawable(new TextureRegion((intro1))));
    }
    private void showTxt2(){
        table.clear();
        table.setFillParent(true);
        table.add(new Label(textIntro2P1, style)).fill().row();
        table.add(new Label(textIntro2P2, style)).fill().row();
        table.setBackground(new TextureRegionDrawable(new TextureRegion((intro2))));
    }
    private void showTxt3(){
        table.clear();
        table.setFillParent(true);
        table.add(new Label(textIntro3P1, style)).fill().row();
        table.add(new Label(textIntro3P2, style)).fill().row();
        table.add(new Label(textIntro3P3, style)).fill().row();
        table.add(new Label(textIntro3P4, style)).fill().row();
        table.add(new Label(textIntro3P5, style)).fill().row();
        table.setBackground(new TextureRegionDrawable(new TextureRegion((intro3))));
    }


    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY) || Gdx.input.justTouched()){
            whichPic ++;
        }
        switch (whichPic) {
            case 1:
                showTxt1();
                break;
            case 2:
                showTxt2();
                break;
            case 3:
                showTxt3();
                break;
            case 4 :
                game.setScreen(new MainScreen(game));
                break;
        
            default:
                break;
        }
        stage.act(delta);
        stage.draw();
    }
    public void resize(int width, int height) {stage.getViewport().update(width, height, true);}
    public void show() {}
    public void pause() {}
    public void resume() {}
    public void hide() {}
    public void dispose() {
        stage.dispose();        
        intro1.dispose();
        intro2.dispose();
        intro3.dispose();
    }
    
}
