package com.vaultborn.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;
import java.util.HashMap;


public class AssetManager implements Disposable {

    private final HashMap<String, Texture> textures = new HashMap<>();


    public Texture getTexture(String path) {
        if (!textures.containsKey(path)) {
            textures.put(path, new Texture(path));
        }
        return textures.get(path);
    }


    @Override
    public void dispose() {
        for (Texture tex : textures.values()) {
            tex.dispose();
        }
        textures.clear();
    }
}
