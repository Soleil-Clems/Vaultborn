package com.vaultborn.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.Actor;

import com.vaultborn.MainGame;
import com.vaultborn.entities.Entity;
import com.vaultborn.entities.stuff.Stuff;
import com.vaultborn.entities.stuff.weapon.Sword;
import com.vaultborn.entities.stuff.armor.Hat;
import com.vaultborn.entities.stuff.armor.Robe;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList; 
import java.lang.SuppressWarnings;

public class InventoryPlayer {
    private boolean putIn;
    
    private LinkedHashMap<Item<? extends Stuff>,Integer> InventoryItem;
    private List<Item<? extends Stuff>> InventoryItemList = new ArrayList<>();
    
    private Stage stage;
    private Table rootTable;
    private Table invTable;
    private boolean showInventory;
    private Skin skin = new Skin(Gdx.files.internal("menu/neon/skin/neon-ui.json"));
    private float WidthCalculation = Gdx.graphics.getWidth() / 3f;
    private float HeightCalculation = Gdx.graphics.getHeight()-60;
    
    private int numberOfSlot = 15;
    public ImageButton.ImageButtonStyle styleFullSlot =  new ImageButton.ImageButtonStyle();
    public ImageButton.ImageButtonStyle styleEmptySlot =  new ImageButton.ImageButtonStyle();
    
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
        //rootTable.setDebug(true);

        //les compartiment de l'inventaire
        Table equipeTable = new Table();
        Table statTable = new Table();
        invTable = new Table();

        Pixmap Pix = new Pixmap(1,1,Pixmap.Format.RGB888);
        Pix.setColor(new Color(26 / 255f, 21 / 255f, 20 / 255f, 100 / 255f));
        Pix.fill();
        rootTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Pix))));
        rootTable.pad(30);
        rootTable.defaults().padBottom(100).padTop(100).width(WidthCalculation).height(HeightCalculation).maxWidth(WidthCalculation).maxHeight(HeightCalculation);
        rootTable.add(equipeTable).expand().padLeft(10);
        rootTable.add(statTable).expand();
        rootTable.add(invTable).expand().width(WidthCalculation).padRight(10);
        

        //bouton avec item
        //ImageButton.ImageButtonStyle styleFullSlot = new ImageButton.ImageButtonStyle();
        styleFullSlot.up = new TextureRegionDrawable(new Texture("objects/sword.png"));
        styleFullSlot.down = new TextureRegionDrawable(new Texture("objects/sword.png")).tint(new Color (1f,1f,1f,0.5f));
        
        //bouton de couleur
        //choix de couleur
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(66 / 255f, 55 / 255f, 54 / 255f, 100 / 255f));
        pixmap.fill();
        Texture textureEmptySlot = new Texture(pixmap);
        pixmap.dispose();
        //pour faire le style du bouton
        TextureRegionDrawable drawableEmptySlot = new TextureRegionDrawable(new TextureRegion(textureEmptySlot));
        styleEmptySlot.up = drawableEmptySlot;
        
    }

    //setter dans inventory
    public void addInventory(Item<? extends Stuff> object){
        if(object.getType().equals(Item.Type.EQUIPMENT) && !InventoryItem.containsKey(object)){
            if(InventoryItem.size()<=15){
                System.err.println(object.getObject().getName()+ " récolté.");
                InventoryItem.put(object,1);
            }
            else{
                System.out.println("inventaire full");
            }
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
    public void setShowInventory(boolean showInventory){
        this.showInventory = showInventory;
    }
    
    
    public void InventoryInput(){
        //afficher l'inventaire
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)){
            WidthCalculation = Gdx.graphics.getWidth() / 3f;
            HeightCalculation = Gdx.graphics.getHeight()-60;
            long ItemSizeInv = Math.round(Math.sqrt((WidthCalculation*HeightCalculation)/numberOfSlot))-5;
            int spaceInRowAvailable = Math.round(WidthCalculation+100);
            if(!this.showInventory){
                //gestion de l'inventaire
                //savoir ce qu'il y a dans l'inventaire
                for (Item<? extends Stuff> itm : InventoryItem.keySet()){
                    if (InventoryItem.get(itm)>1){
                        System.out.println(itm.getObject().getName()+ " j'en ai "+ InventoryItem.get(itm));                    
                    }
                    else{
                        System.out.println(itm.getObject().getName());
                    }
                    InventoryItemList.add(itm);
                }
                //afficher la partie inventaire
                int inventorySlotOccuped = InventoryItem.size();
                for (int count = 0; count<numberOfSlot; count++){
                    if(count<inventorySlotOccuped){
                        if(spaceInRowAvailable<ItemSizeInv){
                            invTable.row();
                            spaceInRowAvailable = Math.round(WidthCalculation+100);
                        }
                        TextureRegion currentTexture = InventoryItemList.get(count).getObject().getTexture();
                        //bouton avec item
                        //ImageButton.ImageButtonStyle styleFullSlot = new ImageButton.ImageButtonStyle();
                        styleFullSlot.up = new TextureRegionDrawable(currentTexture);
                        styleFullSlot.down = new TextureRegionDrawable(currentTexture).tint(new Color (1f,1f,1f,0.5f));
                        invTable.add(new ImageButton(styleFullSlot)).size(ItemSizeInv).fill().pad(2);
                        spaceInRowAvailable -= ItemSizeInv +2;
                    }
                    else{
                        if(spaceInRowAvailable<ItemSizeInv){
                            invTable.row();
                            spaceInRowAvailable = Math.round(WidthCalculation+100);
                        }
                        invTable.add(new ImageButton(styleEmptySlot)).size(ItemSizeInv).fill().pad(2);
                        spaceInRowAvailable -= ItemSizeInv +2;
                    }
                    
                }
                //afficher la partie equipement
                
            }
            else{
                invTable.clear();
                InventoryItemList.clear();
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

    public void rsMenu(int width, int height) { 
        stage.getViewport().update(width, height, true);
    }
    public void shMenu() {}
    public void pMenu() {}
    public void rMenu() {}
    public void hMenu() {}


    
}
//class de gestion des items
class Item<T extends Entity & Stuff>{
    
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