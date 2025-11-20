package com.vaultborn.entities.characters.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.Character;
import com.vaultborn.entities.projectiles.Projectile;

public abstract  class Mage extends Player {
    public String charge = "satyr/charge.png";
    protected int chargeFrameCount = 8;

    public Mage(Vector2 position, TextureRegion texture, String name) {
        super(position, texture, name);
        attackSound = Gdx.audio.newSound(Gdx.files.internal("sounds/power.mp3"));

    }

    public Mage(Vector2 position,  String name) {
        super(position, name);

    }


    @Override
    public void attack(Character target) {
        attackSound.play(0.5f);

        if (world == null) {
            return;
        }

        Vector2 projPos = new Vector2(position.x + characterWidth / 2, position.y + characterHeight / 2);
        TextureRegion projTexture = new TextureRegion(new Texture(charge));

        Vector2 targetPos = target != null ? target.getPosition() : new Vector2(position.x + (facingRight ? 1000 : -1000), position.y);

        Projectile proj = new Projectile(projPos, projTexture, this.facingRight, this.damage, targetPos, chargeFrameCount);
        proj.setWorld(world);
        world.projectiles.add(proj);
    }
}
