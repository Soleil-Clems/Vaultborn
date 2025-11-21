package com.vaultborn.entities.stuff.trigger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.players.Player;
import com.vaultborn.entities.stuff.GameObject;
import com.vaultborn.world.BaseWorld;

import java.util.HashMap;
import java.util.Map;

/**
 * Représente une porte spéciale qui agit comme un téléporteur entre deux mondes.
 * <p>
 * La porte possède une zone de déclenchement. Lorsqu'un joueur entre dans cette zone,
 * le joueur est téléporté dans un autre monde à une position prédéfinie.
 * Elle peut également jouer des animations et un son de téléportation.
 */
public class SpecialDoor extends GameObject {

    protected Map<String, Animation<TextureRegion>> animations = new HashMap<>();
    protected String currentAnimation = "closed";
    private Rectangle triggerZone;
    private BaseWorld parentWorld;
    private BaseWorld targetWorld;
    private Vector2 spawnPosition;
    private float stateTime = 0f;
    protected Sound teleportationSound;

    /**
     * Constructeur d'une porte spéciale.
     *
     * @param position Position de la porte dans le monde
     * @param texture  Texture de la porte
     */
    public SpecialDoor(Vector2 position, TextureRegion texture) {
        super(position, texture);

        float activeWidth = 30;
        float activeHeight = 60;

        float x = position.x + (texture.getRegionWidth() - activeWidth) / 2f;
        float y = position.y + (texture.getRegionHeight() - activeHeight); // zone verticale de la porte

        triggerZone = new Rectangle(x, y, activeWidth, activeHeight);
        loadAnimations();
        teleportationSound = Gdx.audio.newSound(Gdx.files.internal("sounds/teleportation.mp3"));
    }

    @Override
    public void render(SpriteBatch batch) {
        Animation<TextureRegion> anim = animations.get(currentAnimation);
        if (anim != null) {
            TextureRegion frame = anim.getKeyFrame(stateTime, false);
            batch.draw(frame, position.x, position.y);
        } else {
            batch.draw(texture, position.x, position.y); // fallback
        }
    }

    @Override
    public void update(float delta) {
        if (parentWorld == null || targetWorld == null) return;

        stateTime += delta;

        Player player = parentWorld.getPlayer();
        if (player == null) return;

        if (triggerZone.overlaps(player.getHitbox())) {
            parentWorld.changeToWorld(targetWorld, spawnPosition);
        }
    }


    @Override
    public String getType() { return ""; }

    @Override
    public String getName() { return ""; }

    @Override
    public int getDurability() { return 0; }

    @Override
    public String getSpecialPerk() { return ""; }

    @Override
    public boolean pickUp(Player character) {
        teleportationSound.play(1f);
        return true;

    }


    public void setSpawnPosition(float x, float y) { this.spawnPosition = new Vector2(x, y); }

    public Vector2 getSpawnPosition() { return spawnPosition; }

    public BaseWorld getTargetWorld() { return targetWorld; }

    public void setParentWorld(BaseWorld world) { this.parentWorld = world; }

    public void setTargetWorld(BaseWorld world) { this.targetWorld = world; }

    public Rectangle getTriggerZone() { return triggerZone; }


    /**
     * Ajoute une animation à la porte.
     *
     * @param key         Clé de l'animation
     * @param spriteSheet Texture contenant toutes les frames
     * @param frameCount  Nombre de frames
     * @param frameDuration Durée de chaque frame
     */
    protected void addAnimation(String key, Texture spriteSheet, int frameCount, float frameDuration) {
        int frameWidth = spriteSheet.getWidth() / frameCount;
        int frameHeight = spriteSheet.getHeight();
        TextureRegion[][] tmp = TextureRegion.split(spriteSheet, frameWidth, frameHeight);

        TextureRegion[] frames = new TextureRegion[frameCount];
        for (int i = 0; i < frameCount; i++) {
            frames[i] = tmp[0][i];
        }

        Animation<TextureRegion> anim = new Animation<>(frameDuration, frames);
        animations.put(key, anim);
    }

    /**
     * Charge les animations par défaut de la porte.
     */
    public void loadAnimations() {
        addAnimation("open", new Texture("specialdoor/openeddoor.png"), 1, 0.12f);
        addAnimation("closed", new Texture("specialdoor/closeddoor.png"), 1, 0.1f);
    }

    /**
     * Change l'animation actuelle de la porte.
     *
     * @param key Clé de l'animation à activer
     */
    public void setAnimation(String key) {
        if (animations.containsKey(key) && !currentAnimation.equals(key)) {
            currentAnimation = key;
            stateTime = 0f;
        }
    }
}
