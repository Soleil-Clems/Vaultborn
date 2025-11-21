package com.vaultborn.entities.projectiles;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.Entity;
import com.vaultborn.entities.characters.mobs.Mob;
import com.vaultborn.entities.characters.players.Player;
import com.vaultborn.entities.stuff.GameObject;
import com.vaultborn.factories.Factory;
import com.vaultborn.factories.FactoryException;

/**
 * Représente un projectile lancé par un personnage (Mage, Archer, etc.).
 * <p>
 * Le projectile peut infliger des dégâts aux mobs et être détruit lorsqu'il touche un obstacle ou dépasse certaines limites de la carte.
 * Il possède sa propre animation et gère sa physique (direction, vitesse, collision).
 */
public class Projectile extends Entity {
    Factory factory;

    /** Dégâts infligés par le projectile */
    public int damage;

    /** Direction du projectile : true si vers la droite, false sinon */
    public boolean facingRight;

    /** Indique si le projectile doit être supprimé du monde */
    public boolean toRemove = false;

    /** Vitesse du projectile en pixels par seconde */
    public float speed = 600f;

    /** Position cible pour le projectile (non utilisée directement pour la trajectoire) */
    private Vector2 target;

    /** Animation du projectile */
    private Animation<TextureRegion> animation;

    /** Temps écoulé depuis la création du projectile pour l’animation */
    private float stateTime = 0f;

    /** Position initiale du projectile pour vérifier la direction de collision */
    private Vector2 startPosition;

    /** Dimensions du projectile */
    private float projectileWidth = 16f;
    private float projectileHeight = 16f;

    /**
     * Constructeur principal.
     *
     * @param position      position initiale du projectile
     * @param texture       sprite du projectile (spritesheet pour animation)
     * @param facingRight   direction initiale du projectile
     * @param damage        dégâts infligés par le projectile
     * @param targetPosition position cible (optionnelle)
     * @param chargeFrameCount nombre de frames dans la spritesheet
     */
    public Projectile(Vector2 position, TextureRegion texture, boolean facingRight, int damage, Vector2 targetPosition, int chargeFrameCount) {
        super(position, texture);
        this.facingRight = facingRight;
        this.damage = damage;
        this.target = new Vector2(targetPosition);
        this.startPosition = new Vector2(position);

        Vector2 dir = facingRight ? new Vector2(1, 0) : new Vector2(-1, 0);
        this.velocity = dir.scl(speed);

        TextureRegion[][] tmp = TextureRegion.split(texture.getTexture(), texture.getRegionWidth() / chargeFrameCount, texture.getRegionHeight());
        TextureRegion[] frames = new TextureRegion[chargeFrameCount];
        for (int i = 0; i < chargeFrameCount; i++) frames[i] = tmp[0][i];
        animation = new Animation<>(0.09f, frames);

        bounds.setSize(projectileWidth, projectileHeight);
        if (!Factory.IS_TEST) {
            this.factory = new Factory();
        }
    }

    /**
     * Constructeur par défaut avec nombre de frames = 8.
     */
    public Projectile(Vector2 position, TextureRegion texture, boolean facingRight, int damage, Vector2 targetPosition) {
        this(position, texture, facingRight, damage, targetPosition, 8);
        this.factory = new Factory(true);
    }

    /**
     * Met à jour la position, l’animation et gère les collisions.
     *
     * @param delta temps écoulé depuis la dernière mise à jour
     */
    @Override
    public void update(float delta) {
        stateTime += delta;

        Vector2 movement = velocity.cpy().scl(delta);
        Vector2 newPosition = position.cpy().add(movement);

        if (world != null && checkMapCollision(newPosition)) {
            toRemove = true;
            return;
        }

        position.set(newPosition);
        bounds.setPosition(position);

        // Retirer le projectile si hors limites
        if (position.x < -2000 || position.x > 20000 || position.y < -2000 || position.y > 20000) {
            toRemove = true;
            return;
        }

        // Collision avec les mobs
        if (world != null) {
            for (Mob mob : world.mobs) {
                if (!mob.isDead && bounds.overlaps(mob.getBounds())) {
                    float dx = mob.getPosition().x - startPosition.x;
                    boolean isInCorrectDirection = (facingRight && dx > 0) || (!facingRight && dx < 0);

                    if (isInCorrectDirection) {
                        int oldHp = mob.getHp();
                        mob.takeDamage(damage);
                        if (oldHp > 0 && mob.getHp() <= 0 && world.getPlayer() != null) {
                            world.getPlayer().expGain(mob.giveExp());
                            mob.isDead = true;
                            try {
                                looting(world.getPlayer(), mob.getLevel());
                            } catch (FactoryException e) {
                                System.out.println(e);
                            }
                        }
                        toRemove = true;
                        break;
                    }
                }
            }
        }
    }
        /**
     * System de looting, permet d'avoir les probabilité de drop un item
     */
    protected void looting(Player p, int lvl) throws FactoryException {

        double random = Math.random() * 100;
        System.out.println(random);
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
     * Vérifie si le projectile touche un obstacle de la carte.
     *
     * @param pos position du projectile
     * @return true si collision détectée, false sinon
     */
    private boolean checkMapCollision(Vector2 pos) {
        if (world == null) return false;

        boolean topLeft = world.isCellBlocked(pos.x, pos.y + projectileHeight);
        boolean topRight = world.isCellBlocked(pos.x + projectileWidth, pos.y + projectileHeight);
        boolean bottomLeft = world.isCellBlocked(pos.x, pos.y);
        boolean bottomRight = world.isCellBlocked(pos.x + projectileWidth, pos.y);

        return topLeft || topRight || bottomLeft || bottomRight;
    }

    /**
     * Dessine le projectile à l’écran avec sa frame actuelle.
     *
     * @param batch sprite batch utilisé pour le rendu
     */
    @Override
    public void render(SpriteBatch batch) {
        if (animation != null) {
            TextureRegion frame = animation.getKeyFrame(stateTime, true);

            if ((facingRight && frame.isFlipX()) || (!facingRight && !frame.isFlipX())) {
                frame.flip(true, false);
            }

            batch.draw(frame, position.x, position.y);
        }
    }

    /**
     * Retourne la hitbox du projectile.
     *
     * @return rectangle représentant la hitbox
     */
    public Rectangle getHitbox() {
        return bounds;
    }
}
