package com.vaultborn.entities.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.vaultborn.entities.characters.mobs.Mob;
import com.vaultborn.entities.characters.players.Player;
import com.vaultborn.world.BaseWorld;

import java.util.HashMap;
import java.util.Map;

public abstract class Character extends Entity {

    protected String name;
    protected int hp;
    protected int maxHp = 100;
    protected int defense;
    protected int damage;
    protected int level;
    protected int agility;
    protected int range;
    public boolean facingRight = true;
    protected float speed = 200f;
    protected boolean isPlayerControlled = false;
    protected BaseWorld world;
    public float characterWidth = 32f;
    public float characterHeight = 48f;

    protected float velocityY = 0f;
    protected float gravity = -1000f;
    protected float jumpSpeed = 650f;
    protected boolean onGround = false;
    protected String attack = "";
    protected boolean isProtected = false;
    protected boolean isAttacking = false;
    protected float attackTimer = 0f;
    protected float attackCooldown = 0.5f;
    protected boolean hasHit = false;

    public boolean isDead = false;
    protected float deadTimer = 0f;
    protected float deadDuration = 5f;
    protected boolean readyToRemove = false;

    protected boolean isHurt = false;
    protected float hurtTimer = 0f;
    protected float hurtDuration = 0.5f;
    protected Rectangle hitbox;
    private Player player=null;
    private Mob mob=null;


    protected TextureRegion portrait;
    protected Map<String, Animation<TextureRegion>> animations = new HashMap<>();
    public float stateTime = 0f;
    protected String currentAnimation = "idle";

    public Character(Vector2 position, TextureRegion texture, String name) {
        super(position, texture);
        this.name = name;
        this.level = 1;
        this.portrait = texture;
        this.bounds.set(position.x, position.y, characterWidth, characterHeight);
        this.hitbox = new Rectangle(position.x, position.y, 90, 60);

        if (this instanceof Player) {
            player = (Player) this;
        }

    }


    public abstract void attack(Character target);

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }


    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
        if (this.hp >= maxHp) {
            this.hp = maxHp;
        }
        if (this.maxHp <= 1) {
            this.maxHp = 1;
        }
    }

    public void setHp(int hp) {
        this.hp = hp;
        if (this.hp <= 0) this.hp = 0;
        if (this.hp >= this.maxHp) this.hp = maxHp;
    }


    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
        if (this.defense <= 0) this.defense = 0;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
        if (this.agility <= 0) this.agility = 0;
    }
    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
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

    public void setAnimation(String key) {
        if (animations.containsKey(key) && !currentAnimation.equals(key)) {
            currentAnimation = key;
            stateTime = 0f;
        }
    }

    public Animation<TextureRegion> getAnimation(String key) {
        return animations.get(key);
    }

    protected void handleInput(float delta) {
        float moveX = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveX -= 1f;
            facingRight = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveX += 1f;
            facingRight = true;
        }


        if (Gdx.input.isKeyPressed(Input.Keys.A)) attack = "attack";
        if (Gdx.input.isKeyPressed(Input.Keys.D)) attack = "attack2";
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) attack = "attack3";
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) isProtected = true;

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && onGround) {
            velocityY = jumpSpeed + agility;
            onGround = false;
        }

        moveAndCollide(moveX * speed * delta, velocityY * delta);
    }

    protected void handleAI(float delta) {
        moveAndCollide(0, velocityY * delta);
    }

    public void takeDamage(int amount) {
        if (isDead) return;

        hp -= amount;

        if (hp <= 0) {
            hp = 0;
            die();
        } else {
            isHurt = true;
            hurtTimer = hurtDuration;
            setAnimation("hurt");
        }
    }

    protected void die() {
        if (isDead) return;

        isDead = true;
        stateTime = 0f;
        velocityY = 0f;
        setAnimation("dead");
    }


    protected void applyGravity(float delta) {
        velocityY += gravity * delta;
        if (velocityY < -800f) velocityY = -800f;
    }

    protected void moveAndCollide(float moveX, float moveY) {
        if (isDead) return;
        Vector2 move = new Vector2(moveX, moveY);
        Vector2 newPosition = new Vector2(position).add(move);

        Vector2 testX = new Vector2(newPosition.x, position.y);
        if (world != null && !isColliding(testX)) {
            position.x = testX.x;
        }

        Vector2 testY = new Vector2(position.x, newPosition.y);
        if (world != null && !isColliding(testY)) {
            position.y = testY.y;
            onGround = false;
        } else {
            if (velocityY < 0) onGround = true;
            velocityY = 0;
        }

        bounds.setPosition(position);
    }


    @Override
    public void update(float delta) {
        stateTime += delta;

        if (isDead) {
            Animation<TextureRegion> deadAnim = animations.get("dead");

            if (deadAnim != null && !deadAnim.isAnimationFinished(stateTime)) {
                setAnimation("dead");
            }

            deadTimer += delta;

            if (deadTimer >= deadDuration) {
                readyToRemove = true;
            }

            velocityY = 0;
            return;
        }

        if (isHurt) {
            hurtTimer -= delta;
            setAnimation("hurt");

            if (hurtTimer <= 0) {
                isHurt = false;
            }

            applyGravity(delta);
            moveAndCollide(0, velocityY * delta);
            return;
        }

        if (isAttacking) {
            attackTimer += delta;

            if (attackTimer >= 0.2f && !hasHit) {
                if (world != null) {
                    Character target = world.getNearestEnemy(this, range);
                    if (target != null && !target.isDead && target != this) {
                        attack(target);
                        hasHit = true;
                        if(target.getHp()<=0 && !(target instanceof Player)){player.expGain(mob.giveExp());}
                    }
                }
            }

            if (attackTimer >= attackCooldown) {
                isAttacking = false;
                attack = "";
            } else {
                setAnimation(attack);
            }

            applyGravity(delta);
            moveAndCollide(0, velocityY * delta);
            return;
        }

        if (isPlayerControlled) {
            handleInput(delta);
        } else {
            handleAI(delta);
        }

        applyGravity(delta);
        updateAnimationState();
        updateHitbox();
    }

    protected void updateAnimationState() {


        if (attack.equals("attack")) {
            startAttack("attack");
            return;
        } else if (attack.equals("attack2")) {
            startAttack("attack2");
            return;
        } else if (attack.equals("attack3")) {
            startAttack("attack3");
            return;
        } else if (attack.equals("attack4")) {
            startAttack("attack4");
            return;
        }

        // 2. Protection
        if (isProtected) {
            setAnimation("protect");
            isProtected = false;
            return;
        }

        if (!onGround) {
            setAnimation("jump");
            return;
        }


        if (isMovingHorizontally()) {
            setAnimation("walk");
            return;
        }


        setAnimation("idle");
    }

    protected void startAttack(String type) {
        if (isAttacking || isDead || isHurt) return;

        attack = type;
        isAttacking = true;
        hasHit = false;
        attackTimer = 0f;
        setAnimation(type);
    }

    private boolean isColliding(Vector2 pos) {
        if (world == null) return false;

        boolean mapCollide =
            world.isCellBlocked(pos.x, pos.y + characterHeight) ||
                world.isCellBlocked(pos.x + characterWidth, pos.y + characterHeight) ||
                world.isCellBlocked(pos.x, pos.y) ||
                world.isCellBlocked(pos.x + characterWidth, pos.y);

        if (mapCollide) return true;

        if (this instanceof Mob && world.isMobAt(pos, (Mob) this)) return true;

        return false;
    }


    protected boolean isMovingHorizontally() {
        if (isPlayerControlled) {
            return Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        }
        return false;
    }


    @Override
    public void render(SpriteBatch batch) {
        Animation<TextureRegion> anim = animations.get(currentAnimation);
        if (anim != null) {
            boolean looping = true;

            if (currentAnimation.equals("dead")) {
                looping = false;
            }

            TextureRegion frame = anim.getKeyFrame(stateTime, looping);

            if (currentAnimation.equals("dead") && anim.isAnimationFinished(stateTime)) {
                frame = anim.getKeyFrames()[anim.getKeyFrames().length - 1];
            }


            if ((facingRight && frame.isFlipX()) || (!facingRight && !frame.isFlipX())) {
                frame.flip(true, false);
            }

            batch.draw(frame, position.x, position.y);
        } else if (portrait != null) {
            batch.draw(portrait, position.x, position.y);
        }
    }

    public void setWorld(BaseWorld world) {
        this.world = world;
    }

    protected void updateHitbox() {
        hitbox.setPosition(position.x, position.y);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
