package com.vaultborn;

import com.vaultborn.entities.characters.players.*;
import com.vaultborn.entities.characters.mobs.*;
import com.vaultborn.factories.Factory;
import com.vaultborn.factories.FactoryException;
import com.vaultborn.screens.InventoryPlayer;
import com.vaultborn.world.ForestWorld;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

public class CharacterTest {
    private Factory factory;
    private ForestWorld mockWorld;
    private InventoryPlayer inventory;
    private Player warrior;
    private Mob gorgon;

    @BeforeEach
    void setup() throws FactoryException {
        factory = new Factory(true);
        warrior = factory.createPlayer("warrior", 100, 200, mockWorld);
        warrior.isTest =  true;
        gorgon = factory.createMob("gorgon", 100, 200, mockWorld, 1);

        mockWorld = mock(ForestWorld.class);
    }

    @Test
    void testPlayerAttackMob() throws FactoryException {

        assertNotNull(warrior, "Warrior should not be null");
        assertNotNull(gorgon, "Gorgon should not be null");
        int warriorHp = warrior.getHp();
        int gorgonHp = gorgon.getHp();

        assertEquals(warriorHp, 100, "Warrior hp should be equal to 100");
        assertEquals(gorgonHp, 100, "Gorgon hp should be equal to 100");

        warrior.attack(gorgon);
        assertEquals(gorgon.getHp(), 90, "Gorgon hp should be equal to " );


    }

    @Test
    void testMobAttackPlayer_fail() throws FactoryException {

        assertNotNull(warrior, "Warrior should not be null");
        assertNotNull(gorgon, "Gorgon should not be null");
        int warriorHp = warrior.getHp();
        int gorgonHp = gorgon.getHp();

        assertEquals(warriorHp, 100, "Warrior hp should be equal to 100");
        assertEquals(gorgonHp, 100, "Gorgon hp should be equal to 100");

        gorgon.attack(warrior);
        assertNotEquals(warrior.getHp(), 92, "Warrior hp should be equal to 92");


    }
}
