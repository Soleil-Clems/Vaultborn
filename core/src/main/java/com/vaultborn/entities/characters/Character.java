package com.vaultborn.entities.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.vaultborn.entities.characters.mobs.Mob;
import com.vaultborn.entities.characters.players.Player;
import com.vaultborn.entities.stuff.GameObject;
import com.vaultborn.factories.Factory;
import com.vaultborn.factories.FactoryException;
import com.vaultborn.world.BaseWorld;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Classe abstraite représentant un personnage dans le jeu.
 * <p>
 * Un Character peut être :
 * - un joueur
 * - un ennemi (Mob)
 * - un PNJ
 * <p>
 * Il gère :
 * - Les mouvements (physiques + collisions)
 * - La gravité
 * - Les attaques et animations
 * - Les dégâts, la mort, le knockback
 * - L'IA ou l’input joueur
 * - Les animations
 */
public abstract class Character extends Entity {
    public boolean isTest = false;
    Factory factory;
    /**
     * Nom du personnage.
     */
    protected String name;

    /**
     * Points de vie actuels.
     */
    protected int hp;

    /**
     * Points de vie maximum.
     */
    protected int maxHp = 100;

    /**
     * Défense réduisant les dégâts reçus.
     */
    protected int defense;

    /**
     * Dégâts infligés lors d'une attaque.
     */
    protected int damage;

    /**
     * Niveau du personnage.
     */
    protected int level;

    /**
     * Agilité influençant la vitesse ou le saut.
     */
    protected int agility;

    /**
     * Portée d’attaque.
     */
    protected int range;

    /**
     * Direction du personnage (true = droite).
     */
    public boolean facingRight = true;

    /**
     * Vitesse horizontale du personnage.
     */
    protected float speed = 400f;

    /**
     * Indique si ce Character est contrôlé par le joueur.
     */
    protected boolean isPlayerControlled = false;

    /**
     * Monde dans lequel vit ce personnage (override d’Entity.world).
     */
    protected BaseWorld world;

    /**
     * Dimensions du sprite du personnage.
     */
    public float characterWidth = 32f, characterHeight = 48f;

    /**
     * Physique du personnage.
     */
    protected float velocityY = 0f;
    protected float gravity = -1000f;
    protected float jumpSpeed = 650f;
    protected boolean onGround = false;

    /**
     * Nom de l’attaque actuelle.
     */
    protected String attack = "";

    /**
     * Indique si le personnage se protège.
     */
    protected boolean isProtected = false;

    /**
     * Indique si le personnage est en animation d’attaque.
     */
    protected boolean isAttacking = false;

    /**
     * Gestion du cooldown des attaques.
     */
    protected float attackTimer = 0f;
    protected float attackCooldown = 0.5f;
    protected boolean hasHit = false;

    /**
     * Variables liées à la mort.
     */
    public boolean isDead = false;
    public boolean isWin = false;
    protected float deadTimer = 0f;
    protected float deadDuration = 5f;
    protected boolean readyToRemove = false;

    /**
     * Son joué lors du game over du joueur.
     */
    protected Sound gameOverSound;

    /**
     * Gestion des dégâts subis.
     */
    protected boolean isHurt = false;
    protected float hurtTimer = 0f;
    protected float hurtDuration = 0.5f;

    /**
     * Knockback appliqué lorsqu’un ennemi frappe le joueur.
     */
    protected Vector2 knockbackVelocity = new Vector2(0, 0);
    protected float knockbackStrength = 300f;
    protected float knockbackDecay = 10f;

    /**
     * Hitbox du personnage (différente du sprite).
     */
    protected Rectangle hitbox;

    private Player player = null;
    private Mob mob = null;

    /**
     * Portrait du personnage (UI).
     */
    protected TextureRegion portrait;

    /**
     * Liste d’animations triées par nom.
     */
    protected Map<String, Animation<TextureRegion>> animations = new HashMap<>();

    /**
     * Temps d’avancement dans l’animation.
     */
    public float stateTime = 0f;

    /**
     * Animation actuelle ("idle", "walk", etc).
     */
    protected String currentAnimation = "idle";

    /**
     * Mappage des touches (modifiable par l'utilisateur).
     */
    protected LinkedHashMap<String, String> inputList = new LinkedHashMap<String, String>() {{
        put("left", "Left");
        put("right", "Right");
        put("jump", "Space");
        put("attack", "A");
        put("attack2", "Q");
        put("attack3", "D");
        put("attack4", "S");
        put("inventory", "I");
    }};

    /**
     * Constructeur principal pour les personnages jouables et ennemis.
     *
     * @param position position initiale dans le monde
     * @param texture  texture de base / portrait
     * @param name     nom du personnage
     */

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

        if (!Factory.IS_TEST) {
            this.factory = new Factory();
        }
    }

    /**
     * Constructeur utilisé pour les tests unitaires (sans texture).
     *
     * @param position position initiale
     * @param name     nom du personnage
     */
    public Character(Vector2 position, String name) {
        super(position);
        this.name = name;
        this.level = 1;
        this.portrait = texture;
        this.bounds.set(position.x, position.y, characterWidth, characterHeight);
        this.hitbox = new Rectangle(position.x, position.y, 90, 60);

        if (this instanceof Player) {
            player = (Player) this;
        }

        this.factory = new Factory(true);
        this.isTest = true;
    }

    /**
     * Méthode abstraite définissant le comportement d’une attaque.
     *
     * @param target cible de l'attaque
     */
    public abstract void attack(Character target);

    /**
     * @return nom du personnage
     */
    public String getName() {
        return name;
    }

    /**
     * @return points de vie actuels
     */
    public int getHp() {
        return hp;
    }

    /**
     * @return points de vie maximum
     */
    public int getMaxHp() {
        return maxHp;
    }

    /**
     * Définit les PV max et ajuste les PV actuels si nécessaire.
     */
    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
        if (this.hp >= maxHp) {
            this.hp = maxHp;
        }
        if (this.maxHp <= 1) {
            this.maxHp = 1;
        }
    }

    /**
     * Modifie les PV du personnage en respectant les limites.
     */
    public void setHp(int hp) {
        this.hp = hp;
        if (this.hp <= 0) this.hp = 0;
        if (this.hp >= this.maxHp) this.hp = maxHp;
    }

    /**
     * Gestion de la défense, dégâts, niveau, agilité, portée.
     */
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

    /**
     * Définit le mapping clavier personnalisé.
     *
     * @param inputList dictionnaire <action, touche>
     */
    public void setInput(LinkedHashMap<String, String> inputList) {
        this.inputList = inputList;
    }


    /**
     * Ajoute une animation pour un nom donné.
     *
     * @param key           identifiant ("walk", "attack"...)
     * @param spriteSheet   spritesheet à découper
     * @param frameCount    nombre de frames
     * @param frameDuration durée d'affichage par frame
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
     * Change l’animation active.
     *
     * @param key nom de l’animation
     */
    public void setAnimation(String key) {
        if (animations.containsKey(key) && !currentAnimation.equals(key)) {
            currentAnimation = key;
            stateTime = 0f;
        }
    }

    /**
     * @return animation associée à la clé
     */
    public Animation<TextureRegion> getAnimation(String key) {
        return animations.get(key);
    }


    /**
     * Lecture des inputs du joueur et déclenchement des actions.
     */

    protected void handleInput(float delta) {
        float moveX = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("left")))) {
            moveX -= 1f;
            facingRight = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("right")))) {
            moveX += 1f;
            facingRight = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("attack")))) attack = "attack";
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("attack2")))) attack = "attack2";
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("attack3")))) attack = "attack3";
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("attack4")))) isProtected = true;

        if (Gdx.input.isKeyJustPressed(Input.Keys.valueOf(inputList.get("jump"))) && onGround) {
            velocityY = jumpSpeed + agility;
            onGround = false;
        }

        moveAndCollide(moveX * speed * delta, velocityY * delta);
    }

    /**
     * Comportement automatique pour les ennemis (IA).
     */
    protected void handleAI(float delta) {
        moveAndCollide(0, velocityY * delta);
    }

    /**
     * Applique des dégâts au personnage.
     *
     * @param amount quantité de dégâts
     */
    public void takeDamage(int amount) {
        takeDamage(amount, null);
    }

    /**
     * Applique des dégâts et gère :
     * - animation hurt
     * - knockback
     * - mort
     *
     * @param amount   dégâts reçus
     * @param attacker attaquant (peut être null)
     */

    public void takeDamage(int amount, Character attacker) {
        if (isDead) return;

        if (this.defense >= amount) {
            this.hp -= 1;
        } else {
            this.hp -= amount - this.defense;
        }

        if (this.hp <= 0) {
            this.hp = 0;
            die();
        } else {
            isHurt = true;
            hurtTimer = hurtDuration;
            setAnimation("hurt");

            if (isPlayerControlled && attacker != null) {
                applyKnockback(attacker);
            }
        }
    }

    /**
     * Applique un knockback en fonction de la position de l’attaquant.
     */
    protected void applyKnockback(Character attacker) {
        float direction = position.x < attacker.position.x ? -1f : 1f;
        knockbackVelocity.set(direction * knockbackStrength, 0);

        if (onGround) {
            velocityY = jumpSpeed * 0.5f;
            onGround = false;
        }
    }

    /**
     * Traite la mort du personnage :
     * - animation "dead"
     * - freeze des mouvements
     */
    protected void die() {
        if (isDead) return;

        isDead = true;
        stateTime = 0f;
        velocityY = 0f;
        knockbackVelocity.set(0, 0);
        setAnimation("dead");
    }

    /**
     * Applique la gravité verticale.
     */
    protected void applyGravity(float delta) {
        velocityY += gravity * delta;
        if (velocityY < -800f) velocityY = -800f;
    }

    /**
     * Déplace le personnage et gère les collisions horizontales et verticales.
     */
    protected void moveAndCollide(float moveX, float moveY) {
        if (isDead) return;

        float totalMoveX = moveX + knockbackVelocity.x * Gdx.graphics.getDeltaTime();

        Vector2 move = new Vector2(totalMoveX, moveY);
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

        knockbackVelocity.scl(1f - knockbackDecay * Gdx.graphics.getDeltaTime());
        if (Math.abs(knockbackVelocity.x) < 10f) {
            knockbackVelocity.set(0, 0);
        }
    }

    /**
     * Update général appelé à chaque frame.
     * <p>
     * Gère :
     * - IA ou contrôle joueur
     * - attaques
     * - animations
     * - physique
     * - mort
     */
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

            if (isPlayerControlled) {
                handleInput(delta);
            } else {
                handleAI(delta);
            }

            applyGravity(delta);
            return;
        }

        if (isAttacking) {
            attackTimer += delta;

            if (attackTimer >= 0.2f && !hasHit) {
                if (world != null) {
                    Character target = world.getNearestEnemy(this, range);
                    if (!(target instanceof Player)) {
                        mob = (Mob) target;
                    }
                    if (target != null && !target.isDead && target != this) {
                        attack(target);
                        hasHit = true;
                        if (target.getHp() <= 0 && !(target instanceof Player)) {
                            player.expGain(mob.giveExp());
                            try {
                                looting(player, mob.getLevel());
                            } catch (FactoryException e) {
                                System.out.println(e);
                            }
                        }
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

    /**
     * System de looting, permet d'avoir les probabilité de drop un item
     */
    protected void looting(Player p, int lvl) throws FactoryException {

        double random = Math.random() * 100;
        String type = null;
        if (random < 40) {
            return;
        }
        if (random < 50) {
            type = "sword";
        } else if (random < 60) {
            type = "helmet";
        } else if (random < 70) {
            type = "breastplate";
        } else if (random < 80) {
            type = "legplate";
        } else if (random < 90) {
            type = "gauteletplate";
        } else if (random <= 100) {
            type = "ironfoot";
        }

        //gameObjects.add(factory.createObject("helmet",position.x, position.y,this.world,this.lvl));

        GameObject   a = factory.createObject(type, 1000, 200, null, lvl);

        a.pickUp(p);
    }

    /**
     * Détermine l’animation en fonction de l’état du personnage.
     */
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

    /**
     * Commence une attaque si possible.
     *
     * @param type type de l’attaque ("attack", "attack2"...)
     */
    protected void startAttack(String type) {
        if (isAttacking || isDead || isHurt) return;

        attack = type;
        isAttacking = true;
        hasHit = false;
        attackTimer = 0f;
        setAnimation(type);
    }

    /**
     * Vérifie la collision du personnage avec la map ou d'autres entités.
     */
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

    /**
     * Détermine si le personnage est en mouvement horizontal.
     */
    protected boolean isMovingHorizontally() {
        if (isPlayerControlled) {
            return Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("left"))) ||
                Gdx.input.isKeyPressed(Input.Keys.valueOf(inputList.get("right")));
        } else {
            if (world == null || world.getPlayer() == null) return false;

            Character player = world.getPlayer();
            if (player.getHp() <= 0) return false;

            float distanceX = Math.abs(player.getPosition().x - position.x);
            float distanceY = Math.abs(player.getPosition().y - position.y);
            float distance = (float) Math.sqrt(distanceX * distanceX + distanceY * distanceY);

            float attackRange = 50f;
            float followRange = 500f;

            return distance > attackRange && distance < followRange;
        }
    }

    /**
     * Affiche le personnage à l’écran selon son animation actuelle.
     */
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

    /**
     * Redéfinit le monde associé au personnage.
     */
    public void setWorld(BaseWorld world) {
        this.world = world;
    }

    /**
     * Met à jour la hitbox selon la position.
     */
    protected void updateHitbox() {
        hitbox.setPosition(position.x, position.y);
    }

    /**
     * @return hitbox du personnage
     */
    public Rectangle getHitbox() {
        return hitbox;
    }
}
