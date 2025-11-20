package com.vaultborn.entities.characters.mobs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.vaultborn.entities.characters.Character;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.MainGame;

public abstract class Mob extends Character {

    protected int weapon;
    private float attackCooldown = 0.5f;
    private float attackTimer = 0f;
    private float aiOffset;
    private int lvl;
    private int exp;
    private boolean isBoss = false;

    public Mob(Vector2 position, TextureRegion texture, String name, int lvl, int exp) {
        super(position, texture, name);
        this.isPlayerControlled = false;
        this.exp = exp * lvl;
        this.lvl = lvl;
        aiOffset = (float) (Math.random() * 0.5f);
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bossdead.mp3"));

    }

    public Mob(Vector2 position, String name, int lvl, int exp) {
        super(position, name);
        this.isPlayerControlled = false;
        this.exp = exp * lvl;
        this.lvl = lvl;
        aiOffset = (float) (Math.random() * 0.5f);

    }


    @Override
    protected void handleAI(float delta) {
        if (world == null || world.getPlayer() == null) {
            moveAndCollide(0, velocityY * delta);
            return;
        }

        Character player = world.getPlayer();
        Vector2 playerPos = player.getPosition();

        float distanceX = playerPos.x - position.x;
        float distanceY = playerPos.y - position.y;
        float distance = (float) Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        float speed = 100f + aiOffset * 40f;
        float attackRange = 50f;
        float followRange = 400f + aiOffset * 25f;

        if (player.getHp() <= 0) {
            moveAndCollide(0, velocityY * delta);
            return;
        }

        facingRight = distanceX > 0;
        attackTimer -= delta;

        if (distance > attackRange && distance < followRange) {

            float moveX = Math.signum(distanceX) * speed * delta;
            moveAndCollide(moveX, velocityY * delta);

        } else if (distance <= attackRange) {

            if (attackTimer <= 0f) {
                startAttack("attack");
                attackTimer = attackCooldown;
            }

            moveAndCollide(0, velocityY * delta);
        } else {
            moveAndCollide(0, velocityY * delta);
        }
    }

    public void setBoss() {
        this.hp = this.hp*2;
        this.maxHp = this.maxHp*2;
        this.damage = this.damage*2;
        this.defense = this.defense*2;
        this.range = 5;
        this.isBoss = true;
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        if (!isDead) {
            renderHealthBar(batch);
        }
    }

    public int giveExp() {
        return exp;
    }

    private void renderHealthBar(SpriteBatch batch) {
        int hp = getHp();
        int maxHp = getMaxHp();
        float ratio = Math.max(0f, Math.min(1f, (float) hp / maxHp));


        float x = position.x + (characterWidth / 2) - 20;
        float y = position.y + characterHeight + 5;
        float width = 40f;
        float height = 6f;


        batch.setColor(Color.BLACK);
        batch.draw(MainGame.getWhitePixel(), x - 1, y - 1, width + 2, height + 2);


        batch.setColor(0.3f, 0.3f, 0.3f, 1f);
        batch.draw(MainGame.getWhitePixel(), x, y, width, height);


        batch.setColor(0.8f, 0f, 0f, 1f);
        batch.draw(MainGame.getWhitePixel(), x, y, width * ratio, height);

        batch.setColor(Color.WHITE);
    }

    @Override
    protected void die() {
        if (isDead) return;

        isDead = true;
        stateTime = 0f;
        velocityY = 0f;
        setAnimation("dead");
        if (isBoss) {
            gameOverSound.play(0.5f);
        }

    }

    public void loadAnimations() {
    }
}
