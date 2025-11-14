package com.vaultborn.entities.stuff.trigger;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.Character;
import com.vaultborn.entities.characters.players.Player;
import com.vaultborn.entities.stuff.GameObject;
import com.vaultborn.world.BaseWorld;

import java.util.HashMap;
import java.util.Map;

public class SpecialDoor extends GameObject {
    protected Map<String, Animation<TextureRegion>> animations = new HashMap<>();
    String currentAnimation = "close";
    private Rectangle triggerZone;
    private BaseWorld parentWorld;
    private BaseWorld targetWorld;

    public SpecialDoor(Vector2 position, TextureRegion texture) {
        super(position, texture);
        float activeWidth = 30;  // largeur réelle de la porte
        float activeHeight = 60; // hauteur réelle
        float x = position.x + (texture.getRegionWidth() - activeWidth) / 2f;
        float y = position.y + (texture.getRegionHeight() - activeHeight); // si porte du bas vers le haut
        triggerZone = new Rectangle(x, y, activeWidth, activeHeight);


    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    @Override
    public void update(float delta) {
        if (parentWorld == null || targetWorld == null) return;

        Player player = parentWorld.getPlayer();
        if (player == null) return;

        if (triggerZone.overlaps(player.getHitbox())) {
            parentWorld.changeToWorld(targetWorld);
        }
    }

    @Override
    public String getType() {
        return "";
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public int getDurability() {
        return 0;
    }

    @Override
    public String getSpecialPerk() {
        return "";
    }

    @Override
    public void pickUp(Character character) {

    }


    public BaseWorld getNewWorld() {
        return world;
    }

    public BaseWorld getTargetWorld() {
        return targetWorld;
    }

    public void setParentWorld(BaseWorld world) {
        this.parentWorld = world;
    }

    public void setTargetWorld(BaseWorld world) {
        this.targetWorld = world;
    }

    public Rectangle getTriggerZone() {
        return triggerZone;
    }

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


    public void loadAnimations() {
        addAnimation("open", new Texture("specialdoor/openeddoor.png"), 1, 0.1f);
        addAnimation("closed", new Texture("specialdoor/closeddoor.png"), 1, 0.1f);
    }

    public void setAnimation(String key) {
        if (animations.containsKey(key) && !currentAnimation.equals(key)) {
            currentAnimation = key;
            float stateTime = 0f;
        }
    }
}
