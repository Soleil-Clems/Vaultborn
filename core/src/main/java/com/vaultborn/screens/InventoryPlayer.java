package com.vaultborn.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.Actor;

import com.vaultborn.MainGame;
import com.vaultborn.entities.stuff.Stuff;
import com.vaultborn.entities.stuff.weapon.Sword;
import com.vaultborn.entities.stuff.armor.Hat;
import com.vaultborn.entities.stuff.armor.Robe;

import java.util.LinkedHashMap;
import java.util.List;
import java.lang.SuppressWarnings;

public class InventoryPlayer {
    private boolean putIn;
    
    private LinkedHashMap<Item<? extends Stuff>,Integer> InventoryItem;
    //Hat foo = new Hat(new Vector2(100, 100), new TextureRegion(new Texture("objects/sword.png")), "hat", "mon Beau chapeau");
    
    private Stage stage;
    private Table rootTable;
    private boolean showInventory;
    private Skin skin = new Skin(Gdx.files.internal("menu/neon/skin/neon-ui.json"));
    
    Item<Sword> theSword = new Item<>(new Sword(new Vector2(100, 100), new TextureRegion(new Texture("objects/sword.png"))),Item.Type.EQUIPMENT);
    Item<Hat> theHat = new Item<>(new Hat(new Vector2(100, 100), new TextureRegion(new Texture("objects/sword.png")), "hat", "mon Beau chapeau"),Item.Type.EQUIPMENT);
    Item<Hat> theHat2 = new Item<>(new Hat(new Vector2(100, 100), new TextureRegion(new Texture("objects/sword.png")), "hat", "mon chapeau moche"),Item.Type.EQUIPMENT);
    Item<Robe> theRobe = new Item<>(new Robe(new Vector2(100, 100), new TextureRegion(new Texture("objects/sword.png")), "robe", "ma belle veste"),Item.Type.CONSUMABLE);
    Item<Robe> theRobe2 = new Item<>(new Robe(new Vector2(100, 100), new TextureRegion(new Texture("objects/sword.png")), "robe", "ma belle veste"),Item.Type.CONSUMABLE);

    public InventoryPlayer(){
        InventoryItem = new LinkedHashMap<Item<? extends Stuff>,Integer>();
        this.putIn = false;
        stage = new Stage(new ScreenViewport());
        rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        int rows = 5;
        int cols = 5;

        for(int r = 0; r < rows; r++) {
            for(int c = 0; c < cols; c++) {
                ImageButton slot = new ImageButton(skin);
                rootTable.add(slot).size(64).pad(5);
            }
            rootTable.row();
        }
    }

    //setter dans inventory
    public void addInventory(Item<? extends Stuff> object){
        if(object.getType().equals(Item.Type.EQUIPMENT) && !InventoryItem.containsKey(object)){
            System.err.println(object.getObject().getName()+ " récolté.");
            InventoryItem.put(object,1);
        }
        else if (object.getType().equals(Item.Type.EQUIPMENT)){
            System.out.println("objet déjà ajouté");
        }
        if (object.getType().equals(Item.Type.CONSUMABLE)){
            this.putIn = false;
            for (Item<? extends Stuff> itm : InventoryItem.keySet()){
                if (itm.getObject().getName().equals(object.getObject().getName())){
                    object = itm;
                    InventoryItem.put(itm,InventoryItem.get(itm)+1);
                    System.err.println(object.getObject().getName()+ " récolté.");
                    this.putIn = true;
                    break;
                }
            }
            if (!putIn){
                InventoryItem.put(object,1);
                System.err.println(object.getObject().getName()+ " récolté.");
                this.putIn = false;
            }
            
        }
    }
    //getter inventory global
    public LinkedHashMap<Item<? extends Stuff>,Integer> getInventory(){
        return InventoryItem;
    }
    public boolean isShowInventory(){
        return showInventory;
    }
    public Stage getStage(){
        return this.stage;
    }

    public void InventoryInput(){
        //afficher l'inventaire
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)){
            if(!this.showInventory){
                for (Item<? extends Stuff> itm : InventoryItem.keySet()){
                    if (InventoryItem.get(itm)>1){
                        System.out.println(itm.getObject().getName()+ " j'en ai "+ InventoryItem.get(itm));                    
                    }
                    else{
                        System.out.println(itm.getObject().getName());
                    }
                }
            }
            this.showInventory = !showInventory;
            
        }

        //test d'ajout
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)){
            addInventory(theSword);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)){
            addInventory(theHat);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)){
            addInventory(theHat2);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)){
            addInventory(theRobe);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.T)){
            addInventory(theRobe);
        }
        //reset l'inventaire
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)){
            InventoryItem.clear();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.V)){
            System.out.println(InventoryItem);
        }

        
        
    }

    
    //gestin d'affichage
    public void rdMenu(float delta) {
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }
    public void dpMenu(){stage.dispose(); /*skin.dispose();*/}   

    public void rsMenu(int width, int height) { stage.getViewport().update(width, height, true);}
    public void shMenu() {}
    public void pMenu() {}
    public void rMenu() {}
    public void hMenu() {}


    
}
//class de gestion des items
class Item<T>{
    
    public enum Type {
        CONSUMABLE,
        EQUIPMENT
    }

    private T object;
    private Type type;
    

    public Item(T object,Type type) {
        this.object = object;
        this.type = type;
        
    }

    public T getObject() { return object; }
    public Type getType() { return type; }
    
}


/*
//détection du bouton pressé
Gdx.input.setInputProcessor(new InputAdapter() {
@Override
public boolean keyDown(int keycode) {
    }
    System.out.println("Touche pressée : " + Input.Keys.toString(keycode));
    return true;
}
});
*/