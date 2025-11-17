package com.vaultborn.entities.stuff;

import com.vaultborn.entities.Entity;


public class Item<T extends Entity & Stuff>{

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

