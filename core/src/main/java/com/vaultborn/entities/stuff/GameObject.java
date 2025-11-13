package com.vaultborn.entities.stuff;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.Entity;

public abstract  class GameObject  extends Entity implements Stuff{

    public GameObject(Vector2 position, TextureRegion texture) {
        super(position, texture);

    }
}
