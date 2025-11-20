package com.vaultborn.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;



public class InputManager {
    private String left;
    private String right;
    private String jump;
    private String attack;
    private String inventory;
    private String attack2;
    private String attack3;
    private String attack4;

    private LinkedHashMap<String,String>inputList;



    public InputManager(){
        this.left = "Left";
        this.right = "Right";
        this.jump = "Space";
        this.attack = "A";
        this.inventory = "I";
        this.attack2 = "Q";
        this.attack3 = "D";
        this.attack4 = "S";
        inputList = new LinkedHashMap<String,String>(){{
            put("left", left);
            put("right", right);
            put("jump", jump);
            put("attack", attack);
            put("inventory", inventory);
            put("attack2", attack2);
            put("attack3", attack3);
            put("attack4", attack4);
        }};
    }

    public void setInput(String key,String input){
        switch (key.toLowerCase()) {
            case "left":
                inputList.put("left",input);
                break;
            case "right":
                inputList.put("right",input);
                break;
            case "jump":
                inputList.put("jump",input);
                break;
                case "attack":
                inputList.put("attack",input);
                break;
            case "inventory":
                inputList.put("inventory",input);
                break;
            case "attack2":
                inputList.put("attack2",input);
                break;
            case "attack3":
                inputList.put("attack3",input);
                break;
            case "attack4":
                inputList.put("attack4",input);
                break;

            default:
                break;
        }

    }
    public String getInput(String key){
        switch (key.toLowerCase()) {
            case "left":
                return this.left;
            case "right":
                return this.right;
            case "jump":
                return this.jump;
            case "attack":
                return this.attack;
            case "inventory":
                return this.inventory;
            case "attack2":
                return this.attack2;
            case "attack3":
                return this.attack3;
            case "attack4":
                return this.attack4;

            default:
                return null;
        }
    }

    public LinkedHashMap<String,String> allInput(){
        return inputList;
    }
}
