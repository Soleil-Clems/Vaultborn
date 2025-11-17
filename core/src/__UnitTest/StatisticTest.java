package com.vaultborn.__UnitTest;

import java.util.LinkedHashMap;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.players.Player;
import com.vaultborn.entities.characters.players.Warrior;
import com.vaultborn.screens.InventoryPlayer;



public class StatisticTest {

    static LinkedHashMap<String,Integer> statBase = new LinkedHashMap<String,Integer>(){{
        put("Statistique",0);
        put("Niveau",1);
        put("Exp",0);
        put("Point disponible",0);
        put("HP",1);
        put("Defense",1);
        put("Attaque",1);
        put("Agilite",1);
        put("Endurence",1);
        put("Mana",1);
    }};
    static LinkedHashMap<String,Integer> statHighLvl = new LinkedHashMap<String,Integer>(){{
        put("Statistique",0);
        put("Niveau",100000000); //100 000 000
        put("Exp",0);
        put("Point disponible",0);
        put("HP",1);
        put("Defense",1);
        put("Attaque",1);
        put("Agilite",1);
        put("Endurence",1);
        put("Mana",1);
    }};
    static LinkedHashMap<String,Integer> statTooMuchPointDisplay = new LinkedHashMap<String,Integer>(){{
        put("Statistique",0);
        put("Niveau",10);
        put("Exp",0);
        put("Point disponible",0);
        put("HP",1);
        put("Defense",1);
        put("Attaque",1000);
        put("Agilite",1);
        put("Endurence",1);
        put("Mana",1);
    }};
    static LinkedHashMap<String,Integer> statTooMuchExperience = new LinkedHashMap<String,Integer>(){{
        put("Statistique",0);
        put("Niveau",1);
        put("Exp",100000000);
        put("Point disponible",0);
        put("HP",1);
        put("Defense",1);
        put("Attaque",100);
        put("Agilite",1);
        put("Endurence",1);
        put("Mana",1);
    }};

    public static void main(String[] args)
    {   
        Player modeledWarior = new Warrior(new Vector2(0,0), new TextureRegion());
        System.out.println(modeledWarior+ " est né");
        InventoryPlayer inv = new InventoryPlayer(true);
        inv.setPlayer(modeledWarior);
        System.out.println("inventaire ajouter au Player");

        System.out.println("---- Stat du Player ----");
        System.out.println("HP : "+modeledWarior.getHp());
        System.out.println("Max HP : "+modeledWarior.getMaxHp());
        System.out.println("Def : "+modeledWarior.getDefense());
        System.out.println("Dégat : "+modeledWarior.getDamage());
        System.out.println("Agilite : "+modeledWarior.getAgility());
        
        System.out.println("---- boost des stats du Player ----");
        modeledWarior.setHp(100000000);
        modeledWarior.setMaxHp(100000000);
        modeledWarior.setDefense(100000000);
        modeledWarior.setDamage(100000000);
        modeledWarior.setAgility(100000000);
        
        System.out.println("---- Stat du Player ----");
        System.out.println("HP : "+modeledWarior.getHp());
        System.out.println("Max HP : "+modeledWarior.getMaxHp());
        System.out.println("Def : "+modeledWarior.getDefense());
        System.out.println("Dégat : "+modeledWarior.getDamage());
        System.out.println("Agilite : "+modeledWarior.getAgility());
        

        
        System.out.println("---- manipulation des Hp max et hp ----");
        System.out.println("    ---- hp et max hp à 0 ----");
        modeledWarior.setMaxHp(0);
        modeledWarior.setHp(0);
        System.out.println("    HP : "+modeledWarior.getHp());
        System.out.println("    Max HP : "+modeledWarior.getMaxHp());
        System.out.println("    ---- hp supérieur au Hp max (100) ----");
        modeledWarior.setMaxHp(100);
        modeledWarior.setHp(100000000);
        System.out.println("    HP : "+modeledWarior.getHp());
        System.out.println("    Max HP : "+modeledWarior.getMaxHp());
        System.out.println("    ---- perte des Hp max de moitier (50) ----");
        modeledWarior.setMaxHp(50);
        System.out.println("    HP : "+modeledWarior.getHp());
        System.out.println("    Max HP : "+modeledWarior.getMaxHp());



        System.out.println("---- réduction des stats du Player ----");
        modeledWarior.setHp(-100000000);
        modeledWarior.setMaxHp(-100000000);
        modeledWarior.setDefense(-100000000);
        modeledWarior.setDamage(-100000000);
        modeledWarior.setAgility(-100000000);

        System.out.println("---- Stat du Player ----");
        System.out.println("HP : "+modeledWarior.getHp());
        System.out.println("Max HP : "+modeledWarior.getMaxHp());
        System.out.println("Def : "+modeledWarior.getDefense());
        System.out.println("Dégat : "+modeledWarior.getDamage());
        System.out.println("Agilite : "+modeledWarior.getAgility());

        System.out.println("---- Réactualise les stats du joueur ----");
        inv.applyStat();
        System.out.println(inv.getNameValueStat());
        System.out.println("Stat exploitable : " +inv.getStatExploitable());
        modeledWarior.setHp(100000);

        System.out.println("---- Stat du Player ----");
        System.out.println("HP : "+modeledWarior.getHp());
        System.out.println("Max HP : "+modeledWarior.getMaxHp());
        System.out.println("Def : "+modeledWarior.getDefense());
        System.out.println("Dégat : "+modeledWarior.getDamage());
        System.out.println("Agilite : "+modeledWarior.getAgility());
        
        System.out.println("---- modification de stat \"naturel\" ----");
        
        System.out.println("inventaire : "+inv.getInventory());
        System.out.println("équipé : "+inv.getEquipeItem());
        System.out.println("Nom et valeur des stats : "+inv.getNameValueStat());
        inv.addValueStat("hp");
        System.out.println("Nom et valeur des stats : "+inv.getNameValueStat());
        inv.addValueStat("hp");
        inv.addValueStat("attaque");
        inv.addValueStat("DEFEnsE");
        inv.addValueStat("agilitE");
        inv.addValueStat("hp");
        inv.addValueStat("maNA");
        inv.addValueStat("endurence");
        inv.addValueStat("Stamina");
        inv.addValueStat("hp");
        inv.addValueStat("hp");
        inv.addValueStat("hp");
        System.out.println("Nom et valeur des stats : "+inv.getNameValueStat());
        inv.verifyPointDisponibility();
        

        System.out.println("---- boost de niveau ----");
        inv.setNameValueStat(statHighLvl);
        System.out.println("Stat exploitable : "+inv.getStatExploitable());
        System.out.println("Nom et valeur des stats : "+inv.getNameValueStat());

        System.out.println("---- point impossible ----");
        inv.setNameValueStat(statTooMuchPointDisplay);
        System.out.println("Stat exploitable : "+inv.getStatExploitable());
        System.out.println("Nom et valeur des stats : "+inv.getNameValueStat());

        System.out.println("---- trop d'experience + 100 point en attaque ----");
        inv.setNameValueStat(statTooMuchExperience);
        System.out.println("Stat exploitable : "+inv.getStatExploitable());
        System.out.println("Nom et valeur des stats : "+inv.getNameValueStat());

        System.out.println("---- ajout d'expérience ----");
        inv.setNameValueStat(statBase);
        System.out.println("Stat exploitable : "+inv.getStatExploitable());
        inv.addExp(10000);
        System.out.println("Nom et valeur des stats : "+inv.getNameValueStat());
        System.out.println("    ---- ajouter 1 d'attaque ----");
        inv.addValueStat("attaque");
        System.out.println("Nom et valeur des stats : "+inv.getNameValueStat());
        
        
    }

}

