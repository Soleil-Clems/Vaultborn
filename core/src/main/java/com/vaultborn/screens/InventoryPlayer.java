package com.vaultborn.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.particles.values.WeightMeshSpawnShapeValue;
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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.vaultborn.MainGame;
import com.vaultborn.entities.Entity;
import com.vaultborn.entities.characters.players.Player;
import com.vaultborn.entities.characters.players.Warrior;
import com.vaultborn.entities.stuff.Item;
import com.vaultborn.entities.stuff.Stuff;
import com.vaultborn.entities.stuff.weapon.Sword;
import com.vaultborn.entities.stuff.weapon.Weapon;
import com.vaultborn.entities.stuff.armor.Armor;
import com.vaultborn.entities.stuff.armor.Breastplate;
import com.vaultborn.entities.stuff.armor.Hat;
import com.vaultborn.entities.stuff.armor.Robe;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.SuppressWarnings;

public class InventoryPlayer {
    private Player player;
    private boolean putIn;
    Item<? extends Stuff> nonItemEquip = new Item<>(null, Item.Type.EQUIPMENT);

    //list
    private LinkedHashMap<Item<? extends Stuff>,Integer> InventoryItem;
    private List<Item<? extends Stuff>> InventoryItemList = new ArrayList<>();
    private LinkedHashMap<String,Item<? extends Stuff>> equipeItem = new LinkedHashMap<String,Item<? extends Stuff>>(){{
    put("Head",nonItemEquip);
    put("Torso",nonItemEquip);
    put("Arm",nonItemEquip);
    put("Leg",nonItemEquip);
    put("Foot",nonItemEquip);
    put("Weapon",nonItemEquip);
    }};
    //stat du joueur
    LinkedHashMap<String,Integer> nameValueStat = new LinkedHashMap<String,Integer>(){{
        put("Statistique",0);
        put("Niveau",1);
        put("Exp",0);
        put("Point disponible",10);
        put("HP",1);
        put("Defense",1);
        put("Attaque",1);
        put("Agilite",1);
        put("Endurence",1);
        put("Mana",1);
    }};

    private Stage stage;
    //les tables
    private Table rootTable;
    private Table equipeTable;
    private Table statTable;
    private Table invTable;

    private boolean showInventory;
    private Skin skin;
    private float WidthCalculation;
    private float HeightCalculation;


    private int numberOfSlot = 15;

    public ImageButton.ImageButtonStyle styleEmptySlot =  new ImageButton.ImageButtonStyle();


    private Pixmap Pix;
    public InventoryPlayer(boolean testUnit){
        skin = new Skin(Gdx.files.internal("menu/neon/skin/neon-ui.json"));
        InventoryItem = new LinkedHashMap<Item<? extends Stuff>,Integer>();
        int count = 0;
        for (int value : nameValueStat.values()){
            if(count <4){}
            else{
            statExploitable.add(value);}
            count ++;

        }
        this.putIn = false;
        verifyPointDisponibility();
        if(!testUnit){
        skin =  new Skin(Gdx.files.internal("menu/neon/skin/neon-ui.json"));
        WidthCalculation= Gdx.graphics.getWidth() / 3f;
        HeightCalculation= Gdx.graphics.getHeight()-60;
        stage = new Stage(new ScreenViewport());
        rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);
        //rootTable.setDebug(true);

        //les compartiment de l'inventaire
        equipeTable = new Table();
        statTable = new Table();
        invTable = new Table();

         Pix = new Pixmap(1,1,Pixmap.Format.RGB888);
        Pix.setColor(new Color(26 / 255f, 21 / 255f, 20 / 255f, 100 / 255f));
        Pix.fill();
        //rootTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Pix))));
        rootTable.pad(30);
        rootTable.defaults().padBottom(100).padTop(100).width(WidthCalculation).height(HeightCalculation).maxWidth(WidthCalculation).maxHeight(HeightCalculation);
        rootTable.add(equipeTable).expand().padLeft(10);
        rootTable.add(statTable).expand().width(WidthCalculation).pad(0, 10, 0, 10).maxHeight(HeightCalculation);
        rootTable.add(invTable).expand().width(WidthCalculation).padRight(10);

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

    }

    //setter dans inventory
    public void addInventory(Stuff obj){
        Item<? extends Stuff> object;
        if(obj instanceof Weapon){
            object = new Item<>((Weapon)obj,Item.Type.EQUIPMENT);
        }
        else if(obj instanceof Armor){
            object = new Item<>((Armor)obj,Item.Type.EQUIPMENT);
        }
        else{
            return;
        }
        if(object.getType().equals(Item.Type.EQUIPMENT) && !InventoryItem.containsKey(object)){
            if(InventoryItem.size()<=15){
                InventoryItem.put(object,1);
            }

        }

        if (object.getType().equals(Item.Type.CONSUMABLE)){
            this.putIn = false;
            for (Item<? extends Stuff> itm : InventoryItem.keySet()){
                if (itm.getObject().getName().equals(object.getObject().getName())){
                    object = itm;
                    InventoryItem.put(itm,InventoryItem.get(itm)+1);
                    this.putIn = true;
                    break;
                }
            }
            if (!putIn){
                InventoryItem.put(object,1);
                this.putIn = false;
            }

        }
    }

    public void setPlayer(Player player){
        this.player = player;
    }
    public void setInventory(LinkedHashMap<Item<? extends Stuff>,Integer> InventoryItem){
        this.InventoryItem = InventoryItem;
    }
    public void setEquipeItem(LinkedHashMap<String,Item<? extends Stuff>> equipeItem){
        this.equipeItem = equipeItem;
    }
    public void setNameValueStat(LinkedHashMap<String,Integer> nameValueStat){
        LinkedHashMap<String,Integer> temp = this.nameValueStat;
        this.nameValueStat = nameValueStat;
        addExp(0);
        verifyPointDisponibility();
        if(nameValueStat.get("Point disponible")<0){
            this.nameValueStat = temp;
            return;
        }
        int count =0;
        for (int value : nameValueStat.values()){
            if(count <4){}else{
            statExploitable.set(count-4, value);
            }
            count++;
        }
    }
    public void addValueStat(String name){
        verifyPointDisponibility();
        if(nameValueStat.get("Point disponible")>0){
        switch (name.toLowerCase()) {
            case "hp":
                nameValueStat.replace("HP", nameValueStat.get("HP")+1);
                break;
            case "attaque":
                nameValueStat.replace("Attaque", nameValueStat.get("Attaque")+1);
                break;
            case "defense":
                nameValueStat.replace("Defense", nameValueStat.get("Defense")+1);
                break;
            case "agilite":
                nameValueStat.replace("Agilite", nameValueStat.get("Agilite")+1);
                break;
            case "endurence":
                nameValueStat.replace("Endurence", nameValueStat.get("Endurence")+1);
                break;
            case "mana":
                nameValueStat.replace("Mana", nameValueStat.get("Mana")+1);
                break;

            default:
                break;
        }
        nameValueStat.replace("Point disponible", nameValueStat.get("Point disponible")-1);
        }


    }

    public void setShowInventory(boolean showInventory){
        this.showInventory = showInventory;
    }
    public void setEquipeItem(String key,Item<? extends Stuff> item){
        equipeItem.replace(key,item);
    }
    //getter inventory global
    //liste
    public LinkedHashMap<Item<? extends Stuff>,Integer> getInventory(){
        return InventoryItem;
    }
    public LinkedHashMap<String,Item<? extends Stuff>> getEquipeItem(){
        for (String bodyPart : equipeItem.keySet()) {
            if(equipeItem.get(bodyPart).getObject() == null){
                continue;
            }
            System.out.println(equipeItem.get(bodyPart));
        }
        return equipeItem;
    }
    public LinkedHashMap<String,Integer> getNameValueStat(){
        return nameValueStat;
    }
    public List<Integer> getStatExploitable(){
        return statExploitable;
    }
    //autre getter
    public boolean isShowInventory(){
        return showInventory;
    }
    public Stage getStage(){
        return this.stage;
    }
    public Stage getObjectStage(){
        return this.objectStage;
    }

    public boolean getObjectInfoMenu(){
        return this.objectInfoMenu;
    }
    private int getIndexKeyStat(String key){
        int index=0;
        for (String k : nameValueStat.keySet()){
            if (k.equals(key)){
                return index;
            }
            index ++;
        }
        return -1;
    }

    public void addExp(int nbGain){
        nameValueStat.replace("Exp",nameValueStat.get("Exp")+nbGain);
        while (nameValueStat.get("Exp")>nameValueStat.get("Niveau")*100) {
            nameValueStat.replace("Niveau",nameValueStat.get("Niveau")+1);
            nameValueStat.replace("Exp",nameValueStat.get("Exp")-nameValueStat.get("Niveau")*100+100);
            this.player.setHp(this.player.getMaxHp());
        }
        verifyPointDisponibility();
    }

    private boolean reload = false;


    List<Integer> statExploitable = new ArrayList<>();
    public void InventoryInput(){
        //afficher l'inventaire
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)||reload == true){
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
                        final int inventoryCount = count;
                        if(spaceInRowAvailable<ItemSizeInv){
                            invTable.row();
                            spaceInRowAvailable = Math.round(WidthCalculation+100);
                        }
                        TextureRegion currentTexture = InventoryItemList.get(inventoryCount).getObject().getTexture();
                        //bouton avec item
                        //ImageButton.ImageButtonStyle styleFullSlot = new ImageButton.ImageButtonStyle();
                        final ImageButton.ImageButtonStyle styleFullSlot =  new ImageButton.ImageButtonStyle();
                        styleFullSlot.up = new TextureRegionDrawable(currentTexture);
                        styleFullSlot.down = new TextureRegionDrawable(currentTexture).tint(new Color (1f,1f,1f,0.5f));
                        ImageButton itemImageButton = new ImageButton(styleFullSlot);
                        itemImageButton.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x,float y) {
                            objectInfo(InventoryItemList.get(inventoryCount));
                            System.out.println(InventoryItemList.get(inventoryCount).getObject().getName());
                            System.out.println(inventoryCount);
                            System.out.println("objet selectionner");
                            }
                        });
                        invTable.add(itemImageButton).size(ItemSizeInv).fill().pad(2);
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
                for (String key : equipeItem.keySet()){
                    //System.out.println(key);
                    if(equipeItem.get(key).equals(nonItemEquip)){
                        equipeTable.add(new ImageButton(styleEmptySlot)).height(HeightCalculation/6-20).width(WidthCalculation-20).pad(2);
                        equipeTable.row();
                    }
                    else{
                        TextureRegion currentTexture = equipeItem.get(key).getObject().getTexture();
                        //bouton avec item
                        //ImageButton.ImageButtonStyle styleFullSlot = new ImageButton.ImageButtonStyle();
                        final ImageButton.ImageButtonStyle styleFullSlot =  new ImageButton.ImageButtonStyle();
                        styleFullSlot.up = new TextureRegionDrawable(currentTexture);
                        styleFullSlot.down = new TextureRegionDrawable(currentTexture).tint(new Color (1f,1f,1f,0.5f));
                        ImageButton itemImageButton = new ImageButton(styleFullSlot);
                        equipeTable.add(itemImageButton).width(ItemSizeInv).height(HeightCalculation/6-20).pad(2);
                        equipeTable.row();
                    }
                }

                //afficher la partie Stat du joueur
                Pix.setColor(new Color(66 / 255f, 55 / 255f, 54 / 255f, 100 / 255f));
                statTable.setBackground(new TextureRegionDrawable(new Texture(Pix)).tint(new Color (1f,1f,1f,0.5f)));

                statTable.defaults().pad(5).expandX();

                int nbAdded =0;
                //statTable.setDebug(true);

                verifyPointDisponibility();

                for (String name : nameValueStat.keySet()) {
                    Label actualName = new Label(name, skin);
                    Label actualValue = new Label(Integer.toString(nameValueStat.get(name)), skin);
                    TextButton actualAddingStat = new TextButton("+", skin);
                    actualAddingStat.setName(name);
                    actualAddingStat.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x,float y){
                                String statName = ((Actor) event.getListenerActor()).getName();
                                if (nameValueStat.get("Point disponible") >0){
                                    nameValueStat.replace(statName,nameValueStat.get(statName)+1);
                                    statExploitable.set(getIndexKeyStat(name)-4,statExploitable.get(getIndexKeyStat(name)-4)+1);
                                    InventoryReload();
                                    if(name.equals("HP")){player.setHp(player.getHp()+10);}
                                }
                            }
                        });

                    if (name.equals("Statistique")) {
                        statTable.add(actualName).colspan(3).center();
                        statTable.row();
                    }
                    else if (name.equals("Niveau")){
                        statTable.add(actualName).left();
                        statTable.add(actualValue).center();
                        statTable.add(new Label(Integer.toString(nameValueStat.get("Exp"))+" / "+Integer.toString(nameValueStat.get("Niveau")*100),skin)).right();
                        statTable.row();
                    }
                    else if (name.equals("Point disponible")) {
                        statTable.add(actualName).left();
                        statTable.add(actualValue).center();
                        statTable.row();
                    }
                    else if(name.equals("Exp")){
                        continue;
                    }
                    else {
                        statTable.add(actualName).left();
                        statTable.add(actualValue).center();
                        statTable.add(actualAddingStat).right();
                        switch(name){
                            case "HP":
                                nbAdded = 10;
                                break;
                            case "Defense":
                                nbAdded = 3;
                                break;
                            case "Attaque":
                                nbAdded = 5;
                                break;
                            case "Agilite":
                                nbAdded = 1;
                                break;
                            default:
                                nbAdded = 0;
                                break;
                        }
                        Label addingValue = new Label("(+ "+Integer.toString(nbAdded)+")", skin);
                        statTable.add(addingValue).left();
                        statTable.row();
                        statExploitable.add(nameValueStat.get(name));
                    }
                }


            }
            else{
                invTable.clear();
                equipeTable.clear();
                statTable.clear();
                InventoryItemList.clear();
            }
            this.reload = false;
            this.showInventory = !showInventory;
        }


    }

    public void applyStat(){
        int Hp = 90;
        int Def = 3;
        int dmg = 5;
        int agi = 1;

        Hp = Hp + (statExploitable.get(0)*10);
        Def = Def +(statExploitable.get(1)*3);
        dmg = dmg +(statExploitable.get(2)*5);
        agi = agi +(statExploitable.get(3));

        for(String name : equipeItem.keySet()){


            if(equipeItem.get(name).getObject() == null){

            }
            else{
                if(equipeItem.get(name).getObject() instanceof Weapon){
                    Weapon w = (Sword) equipeItem.get(name).getObject();
                    dmg = dmg + w.getDamage();
                }
                else if(equipeItem.get(name).getObject() instanceof Armor){
                    Armor a = (Armor) equipeItem.get(name).getObject();
                    Hp = Hp + a.getHealth();
                    Def = Def + a.getDefense();
                    agi = agi + a.getAgility();
                    //a.getStamina();
                    //a.getMana();
                }
            }


            //Hp = Hp +
        }
        this.player.setMaxHp(Hp);

        this.player.setDefense(Def);
        this.player.setDamage(dmg);
        this.player.setAgility(agi);
        //player.setStamina(statExploitable.get(4));
        //player.setMana(statExploitable.get(5));

        System.out.println("---- Stat du joueur ----");
        System.out.println("HP : "+player.getHp());
        System.out.println("Def : "+player.getDefense());
        System.out.println("Dégat : "+player.getDamage());
        System.out.println("Agilite : "+player.getAgility());
        //System.out.println(player.getHp());
        //System.out.println(player.getDefense());
        //System.out.println(player.getDamage());
        //System.out.println(player.getAgility());
    }

    public void verifyPointDisponibility(){
        int count=1;
        int AllPointAdded =0;
        for (int value : nameValueStat.values()){
            if (count <=4){
                count ++;
                continue;
            }
            AllPointAdded += value;

        }
        nameValueStat.put("Point disponible",nameValueStat.get("Niveau")*10+6-AllPointAdded);
    }

    private boolean objectInfoMenu = false;
    private Stage objectStage;
    //variable des stuff
    HashMap<String,String> statStuff = new HashMap<String,String>(){{
        put("oldName",null);
        put("oldType",null);
        put("oldDurability",null);
        put("oldDamage",null);
        put("oldHealth",null);
        put("oldDefense",null);
        put("oldAgility",null);
        put("oldStamina",null);
        put("oldMana",null);
        put("oldSpecialPerk",null);
        put("newName",null);
        put("newType",null);
        put("newDurability",null);
        put("newDamage",null);
        put("newHealth",null);
        put("newDefense",null);
        put("newAgility",null);
        put("newStamina",null);
        put("newMana",null);
        put("newSpecialPerk",null);

    }};
    //quand pas de stuff équipé
    HashMap<String,String> statStuffVide = new HashMap<>(statStuff);
    private String category;
//bulle d'info de l'object selectionner dans l'inventaire
    private void objectInfo(Item<? extends Stuff> selected){
        this.objectInfoMenu = true;
        Item<? extends Stuff> objectSelected = selected;


        //table
        objectStage = new Stage(new ScreenViewport());
        Table objectRootTable = new Table();
        objectRootTable.align(Align.center);
        objectRootTable.setBounds((Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 2f)/2f, (Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 2f)/2f, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        objectStage.addActor(objectRootTable);
        Table objectLeftTable = new Table();
        Table objectRightTable = new Table();
        Table objectButtonTable = new Table();
        objectRootTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Pix))));
        objectLeftTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Pix))).tint(new Color (0.5f,0.5f,0.5f,1f)));
        objectRightTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Pix))).tint(new Color (0.2f,0.2f,0.2f,0.3f)));
        objectRootTable.add(objectLeftTable).width(Value.percentWidth(0.5f,objectRootTable)).maxWidth(Value.percentWidth(0.5f,objectRootTable)).fillY();//.left();
        objectRootTable.add(objectRightTable).width(Value.percentWidth(0.5f,objectRootTable)).maxWidth(Value.percentWidth(0.5f,objectRootTable)).fillY();//.right();
        objectRootTable.row();
        objectRootTable.add(objectButtonTable).colspan(2).fillX();
        objectButtonTable.defaults().expandX();

        TextButton stuffManageEquip = new TextButton("Equiper", skin);
        TextButton stuffManageCancel = new TextButton("Annuler", skin);
        TextButton stuffManagetrash = new TextButton("Jeter", skin);

        stuffManageEquip.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x,float y) {
                Item<? extends Stuff> temp = equipeItem.get(objectSelected.getObject().getClass().getSuperclass().getSimpleName());
                Stuff temp2 = temp.getObject();
                if (temp.getObject() != null){
                    addInventory(temp2);
                }
                equipeItem.replace(objectSelected.getObject().getClass().getSuperclass().getSimpleName(), objectSelected);
                InventoryItem.remove(objectSelected);
                System.out.println("objet équiper");
                InventoryReload();

            }
        });
        stuffManageCancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x,float y) {
                objectInfoMenu=false;
                System.out.println("objet annuler");
            }
        });
        stuffManagetrash.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x,float y) {
                System.out.println("objet jeter");
                InventoryItem.remove(objectSelected);
                InventoryReload();

            }
        });


        objectButtonTable.add(stuffManageEquip);
        objectButtonTable.add(stuffManageCancel);
        objectButtonTable.add(stuffManagetrash);
        objectRootTable.setDebug(true);

        //comparaison
        //String category = "";
        //verification de weapon + récupération des valeurs
        if(objectSelected.getObject().getClass().getSuperclass().getSimpleName().equals("Weapon")){
            Item<? extends Stuff> oldEquiped = equipeItem.get("Weapon");
            if (oldEquiped.getObject() instanceof Weapon){
                Weapon oldWeapon = (Weapon) equipeItem.get("Weapon").getObject();
                statStuff.put("oldName", oldWeapon.getName());
                statStuff.put("oldType", oldWeapon.getType());
                statStuff.put("oldDurability", Integer.toString(oldWeapon.getDurability()));
                statStuff.put("oldDamage", Integer.toString(oldWeapon.getDamage()));
                statStuff.put("oldSpecialPerk", oldWeapon.getSpecialPerk());

            }
            Weapon newWeapon = (Weapon) selected.getObject();
            statStuff.put("newName", newWeapon.getName());
            statStuff.put("newType", newWeapon.getType());
            statStuff.put("newDurability", Integer.toString(newWeapon.getDurability()));
            statStuff.put("newDamage", Integer.toString(newWeapon.getDamage()));
            statStuff.put("newSpecialPerk", newWeapon.getSpecialPerk());
            category = "Weapon";
            //objectLeftTable.add(new Label("Actuellement équipé", skin));
            //System.out.println(selected.getObject().getClass().getSuperclass().getSimpleName());
        }
        //verification de armor + récupération des valeurs
        else if (objectSelected.getObject().getClass().getSuperclass().getSuperclass().getSimpleName().equals("Armor")){
            System.out.println(objectSelected.getObject() instanceof Armor);
            Item<? extends Stuff> oldEquiped = equipeItem.get(selected.getObject().getClass().getSuperclass().getSimpleName());
            if (oldEquiped.getObject() instanceof Armor){
                Armor oldArmor = (Armor) equipeItem.get(selected.getObject().getClass().getSuperclass().getSimpleName()).getObject();
                statStuff.put("oldName", oldArmor.getName());
                statStuff.put("oldType", oldArmor.getType());
                statStuff.put("oldDurability", Integer.toString(oldArmor.getDurability()));
                statStuff.put("oldHealth", Integer.toString(oldArmor.getHealth()));
                statStuff.put("oldDefense", Integer.toString(oldArmor.getDefense()));
                statStuff.put("oldAgility", Integer.toString(oldArmor.getAgility()));
                statStuff.put("oldStamina", Integer.toString(oldArmor.getStamina()));
                statStuff.put("oldMana", Integer.toString(oldArmor.getMana()));
                statStuff.put("oldSpecialPerk", oldArmor.getSpecialPerk());
            }
            Armor newArmor = (Armor) selected.getObject();
            statStuff.put("newName", newArmor.getName());
            statStuff.put("newType", newArmor.getType());
            statStuff.put("newDurability", Integer.toString(newArmor.getDurability()));
            statStuff.put("newHealth", Integer.toString(newArmor.getHealth()));
            statStuff.put("newDefense", Integer.toString(newArmor.getDefense()));
            statStuff.put("newAgility", Integer.toString(newArmor.getAgility()));
            statStuff.put("newStamina", Integer.toString(newArmor.getStamina()));
            statStuff.put("newMana", Integer.toString(newArmor.getMana()));
            statStuff.put("newSpecialPerk",newArmor.getSpecialPerk());
            category = "Armor";
        }

        //ancien item
        {objectLeftTable.add(new Label("Ancien",skin)).right();
        objectLeftTable.row();
        objectLeftTable.add(new Label("Nom : ",skin)).left().padLeft(0);
        objectLeftTable.add(new Label(statStuff.get("oldName"),skin)).center();
        objectLeftTable.row();
        objectLeftTable.add(new Label("Type : ",skin)).left().padLeft(0);
        objectLeftTable.add(new Label(statStuff.get("oldType"),skin)).center();
        objectLeftTable.row();
        objectLeftTable.add(new Label("Durabilite : ",skin)).left().padLeft(0);
        objectLeftTable.add(new Label(statStuff.get("oldDurability"),skin)).center();
        objectLeftTable.row();
        if(category == "Weapon"){
            objectLeftTable.add(new Label("Attaque : ",skin)).left().padLeft(0);
            objectLeftTable.add(new Label(statStuff.get("oldDamage"),skin)).center();
            objectLeftTable.row();
        }
        if(category == "Armor"){

            objectLeftTable.add(new Label("HP : ",skin)).left().padLeft(0);
            objectLeftTable.add(new Label(statStuff.get("oldHealth"),skin)).center();
            objectLeftTable.row();
            objectLeftTable.add(new Label("Defense : ",skin)).left().padLeft(0);
            objectLeftTable.add(new Label(statStuff.get("oldDefense"),skin)).center();
            objectLeftTable.row();
            objectLeftTable.add(new Label("Agilite : ",skin)).left().padLeft(0);
            objectLeftTable.add(new Label(statStuff.get("oldAgility"),skin)).center();
            objectLeftTable.row();
            objectLeftTable.add(new Label("Endurence : ",skin)).left().padLeft(0);
            objectLeftTable.add(new Label(statStuff.get("oldStamina"),skin)).center();
            objectLeftTable.row();
            objectLeftTable.add(new Label("Mana : ",skin)).left().padLeft(0);
            objectLeftTable.add(new Label(statStuff.get("oldMana"),skin)).center();
            objectLeftTable.row();
        }
        objectLeftTable.add(new Label("Perk Special : ",skin)).left().padLeft(0);
        objectLeftTable.add(new Label(statStuff.get("oldSpecialPerk"),skin)).center();
        objectLeftTable.row();
    }
    //nouveau item
    {objectRightTable.add(new Label("Nouveau",skin)).right();
    objectRightTable.row();
    objectRightTable.add(new Label("Nom : ",skin)).left().padLeft(0);
    objectRightTable.add(new Label(statStuff.get("newName"),skin)).center();
    objectRightTable.row();
    objectRightTable.add(new Label("Type : ",skin)).left().padLeft(0);
    objectRightTable.add(new Label(statStuff.get("newType"),skin)).center();
    objectRightTable.row();
    objectRightTable.add(new Label("Durabilite : ",skin)).left().padLeft(0);
    objectRightTable.add(new Label(statStuff.get("newDurability"),skin)).center();
    objectRightTable.row();
    if(category == "Weapon"){
        objectRightTable.add(new Label("Attaque : ",skin)).left().padLeft(0);
        objectRightTable.add(new Label(statStuff.get("newDamage"),skin)).center();
        objectRightTable.row();
    }
    if(category == "Armor"){

            objectRightTable.add(new Label("HP : ",skin)).left().padLeft(0);
            objectRightTable.add(new Label(statStuff.get("newHealth"),skin)).center();
            objectRightTable.row();
            objectRightTable.add(new Label("Defense : ",skin)).left().padLeft(0);
            objectRightTable.add(new Label(statStuff.get("newDefense"),skin)).center();
            objectRightTable.row();
            objectRightTable.add(new Label("Agilite : ",skin)).left().padLeft(0);
            objectRightTable.add(new Label(statStuff.get("newAgility"),skin)).center();
            objectRightTable.row();
            objectRightTable.add(new Label("Endurence : ",skin)).left().padLeft(0);
            objectRightTable.add(new Label(statStuff.get("newStamina"),skin)).center();
            objectRightTable.row();
            objectRightTable.add(new Label("Mana : ",skin)).left().padLeft(0);
            objectRightTable.add(new Label(statStuff.get("newMana"),skin)).center();
            objectRightTable.row();
        }
        objectRightTable.add(new Label("Perk Special : ",skin)).left().padLeft(0);
        objectRightTable.add(new Label(statStuff.get("newSpecialPerk"),skin)).center();
        objectRightTable.row();
    }
    //objectRootTable.setDebug(true);

    statStuff.putAll(statStuffVide);
    }

    //reload l'inventaire pour l'afficher
    public void InventoryReload(){
        objectInfoMenu=false;
        reload = true;
        InventoryInput();
        reload = true;
        InventoryInput();
        applyStat();
    }

//gestion d'affichage
public void rdMenu(float delta) {
    //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    stage.act(delta);
    stage.draw();
    if (objectInfoMenu){
        objectStage.act(delta);
        objectStage.draw();
    }
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



/*
//détection du bouton pressé
Gdx.input.setInputProcessor(new InputAdapter() {
@Override
public boolean keyDown(int keycode) {
    System.out.println("Touche pressée : " + Input.Keys.toString(keycode));
    return true;
    }
    });
    */
/*
    //equiper
    if (Gdx.input.isKeyJustPressed(Input.Keys.Z)){
        //System.out.println(InventoryItem.keySet());
        //System.out.println(equipeItem.keySet());
        for (Item<?> a:InventoryItem.keySet()){
            System.out.println(a.getObject().getClass().getSuperclass().getSimpleName());
            System.out.println(equipeItem.containsKey(a.getObject().getClass().getSuperclass().getSimpleName()));
            setEquipeItem(a.getObject().getClass().getSuperclass().getSimpleName(), a);
            InventoryItem.remove(a);
            break;

        }

    }*/
