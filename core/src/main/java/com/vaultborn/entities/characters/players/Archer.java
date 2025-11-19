package com.vaultborn.entities.characters.players;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.Character;
import com.vaultborn.entities.projectiles.Projectile;


public class Archer extends Mage{
    private float meleeRange = 80f;

    public Archer(Vector2 position, TextureRegion texture) {
        super(position, texture, "Perceval");
        this.maxHp = 100;
        this.hp = this.maxHp;
        this.damage = 80;
        this.defense = 8;
        this.level = 1;
        this.agility = 2;
        this.range = 10;
    }

    @Override
    public void attack(Character target) {
        attackSound.play(0.5f);

        if (world == null) {
            return;
        }

        if(this.attack.equals("attack4")){
            Vector2 projPos = new Vector2(position.x + characterWidth / 2, position.y + characterHeight / 2);
            TextureRegion projTexture = new TextureRegion(new Texture(charge));

            Vector2 targetPos = target != null ? target.getPosition() : new Vector2(position.x + (facingRight ? 1000 : -1000), position.y);

            Projectile proj = new Projectile(projPos, projTexture, this.facingRight, this.damage, targetPos, chargeFrameCount);
            proj.setWorld(world);
            world.projectiles.add(proj);
        }else{
            float distance = position.dst(target.getPosition());

            if (distance <= meleeRange) {
                int dmg = Math.max(1, this.damage - target.getDefense());
                target.takeDamage(dmg);
            }
        }

    }

    @Override
    protected void handleInput(float delta) {
        attackSound = Gdx.audio.newSound(Gdx.files.internal("sounds/sword.mp3"));

        this.charge = "archer/Arrow.png";
        this.chargeFrameCount =1 ;
        float moveX = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("left")))) {
            moveX -= 1f;
            facingRight = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("right")))) {
            moveX += 1f;
            facingRight = true;
        }


        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("attack")))){
            attack = "attack";
        }
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("attack2")))) attack = "attack2";
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("attack3")))) attack = "attack3";
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("attack4")))){
            attack = "attack4";
            attackSound = Gdx.audio.newSound(Gdx.files.internal("sounds/arrow.mp3"));

        }

        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) isProtected = true;

        if (Gdx.input.isKeyJustPressed(Input.Keys.valueOf(inputList.get("jump"))) && onGround) {
            velocityY = jumpSpeed + agility;
            onGround = false;
        }

        moveAndCollide(moveX * speed * delta, velocityY * delta);
    }

    public void loadAnimations() {
        String asset = "archer/";
        addAnimation("idle", new Texture(asset+"Idle.png"), 9, 0.1f);
        addAnimation("walk", new Texture(asset+"Walk.png"), 8, 0.1f);
        addAnimation("attack", new Texture(asset+"Attack_1.png"), 5, 0.05f);
        addAnimation("attack2", new Texture(asset+"Attack_2.png"), 5, 0.05f);
        addAnimation("attack3", new Texture(asset+"Attack_3.png"), 6, 0.05f);
        addAnimation("attack4", new Texture(asset+"Shot.png"), 14, 0.05f);
        addAnimation("run", new Texture(asset+"Run.png"), 8, 0.01f);
        addAnimation("jump", new Texture(asset+"Jump.png"), 9, 0.09f);
        addAnimation("protect", new Texture(asset+"Idle.png"), 9, 2f);
        addAnimation("dead", new Texture(asset+"Dead.png"), 5, 0.15f);
        addAnimation("hurt", new Texture(asset+"Hurt.png"), 3, 0.09f);
    }

}
