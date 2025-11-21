package com.vaultborn.save;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;

public class SaveManager {

    private static final String SAVE_NAME = "vaultborn_save";
    private static final Json json = new Json();

    public static void save(SaveData data) {
        Preferences prefs = Gdx.app.getPreferences(SAVE_NAME);
        prefs.putString("save", json.prettyPrint(data));
        prefs.flush();
    }

    public static SaveData load() {
        Preferences prefs = Gdx.app.getPreferences(SAVE_NAME);

        if (!prefs.contains("save"))
            return null;

        return json.fromJson(SaveData.class, prefs.getString("save"));
    }


    public static boolean hasSave() {
        Preferences prefs = Gdx.app.getPreferences(SAVE_NAME);
        return prefs.contains("save");
    }

    // Méthode pour supprimer une sauvegarde
    public static void deleteSave() {
        Preferences prefs = Gdx.app.getPreferences(SAVE_NAME);
        prefs.remove("save");
        prefs.flush();
    }

}
