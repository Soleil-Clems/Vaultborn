package com.vaultborn.entities.characters.players;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vaultborn.entities.characters.Character;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WarriorTest {

    @Test
    void warriorInitialStatsAreCorrect() {
        Warrior w = new Warrior(new Vector2(0, 0), new TextureRegion());

        assertEquals(100, w.getHp(), "HP should start at 100");
        assertEquals(100, w.getMaxHp());
        assertEquals(20, w.getDamage());
        assertEquals(8, w.getDefense());
        assertEquals(1, w.getLevel());
        assertEquals(2, w.getAgility());
        assertEquals(1, w.getRange());
    }

    @Test
    void warriorAttackDealsCorrectDamage() {
        Warrior attacker = new Warrior(new Vector2(0, 0), new TextureRegion());

        // Dummy character to take damage
        Character target = new Character(new Vector2(0,0), new TextureRegion(), "Target") {
            @Override
            public void update(float delta) {
                // no logic needed
            }
        };

        target.setDefense(5);
        target.setHp(50);

        attacker.attack(target);

        // damage = max(1, 20 - 5) = 15
        assertEquals(35, target.getHp());
    }

    @Test
    void warriorAttackAlwaysDealsAtLeastOneDamage() {
        Warrior attacker = new Warrior(new Vector2(0, 0), new TextureRegion());

        Character tank = new Character(new Vector2(0,0), new TextureRegion(), "Tank") {
            @Override
            public void update(float delta) {}
        };

        tank.setDefense(999);
        tank.setHp(20);

        attacker.attack(tank);

        assertEquals(19, tank.getHp(), "Warrior should always deal at least 1 damage");
    }
}
