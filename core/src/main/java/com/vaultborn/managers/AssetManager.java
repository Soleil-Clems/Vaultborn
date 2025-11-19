package com.vaultborn.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;
import java.util.HashMap;


public class AssetManager implements Disposable {

    private final HashMap<String, Texture> textures = new HashMap<>();
    private final HashMap<String, Sound> sounds = new HashMap<>();


    public Texture getTexture(String path) {
        if (!textures.containsKey(path)) {
            textures.put(path, new Texture(path));
        }
        return textures.get(path);
    }

    public Sound getSound(String path) {
        if (!sounds.containsKey(path)) {
            sounds.put(path, Gdx.audio.newSound(Gdx.files.internal(path)));
        }
        return sounds.get(path);
    }


    @Override
    public void dispose() {
        for (Texture tex : textures.values()) tex.dispose();
        for (Sound snd : sounds.values()) snd.dispose();
        textures.clear();
        sounds.clear();
    }

}
