package com.vaultborn;

import com.vaultborn.entities.characters.players.*;
import com.vaultborn.entities.characters.mobs.*;
import com.vaultborn.factories.Factory;
import com.vaultborn.factories.FactoryException;
import com.vaultborn.world.ForestWorld;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FactoryTest {

    private Factory factory;
    private ForestWorld mockWorld;

    @BeforeEach
    void setup() {
        factory = new Factory(true);
        mockWorld = mock(ForestWorld.class);
    }


    @Test
    void testCreatePlayer_Warrior_success() throws FactoryException {
        Player p = factory.createPlayer("warrior", 100, 200, mockWorld);

//        assertNotNull(p);
//        assertTrue(p instanceof Warrior, () -> "Expected Warrior, got: " + p.getClass().getSimpleName());
//        assertEquals(100, p.getPosition().x, () -> "Expected X=100 got " + p.getPosition().x);
//        assertEquals(200, p.getPosition().y, () -> "Expected Y=200 got " + p.getPosition().y);
    }

    @Test
    void testCreatePlayer_Warrior_failure() {
        FactoryException ex = assertThrows(
            FactoryException.class,
            () -> factory.createPlayer("warriorX", 10, 20, mockWorld)
        );
        assertEquals("Unknown player type: warriorX", ex.getMessage());
    }

    @Test
    void testCreatePlayer_DarkWarrior_success() throws FactoryException {
        Player p = factory.createPlayer("darkwarrior", 10, 20, mockWorld);

        assertNotNull(p);
        assertTrue(p instanceof DarkWarrior, () -> "Expected DarkWarrior, got: " + p.getClass().getSimpleName());
        assertEquals(10, p.getPosition().x);
        assertEquals(20, p.getPosition().y);
    }

    @Test
    void testCreatePlayer_DarkWarrior_failure() {
        FactoryException ex = assertThrows(
            FactoryException.class,
            () -> factory.createPlayer("darkwarriorX", 10, 20, mockWorld)
        );
        assertEquals("Unknown player type: darkwarriorX", ex.getMessage());
    }

    @Test
    void testCreatePlayer_Archer_success() throws FactoryException {
        Player p = factory.createPlayer("archer", 10, 20, mockWorld);

        assertNotNull(p);
        assertTrue(p instanceof Archer, () -> "Expected Archer, got: " + p.getClass().getSimpleName());
    }

    @Test
    void testCreatePlayer_Archer_failure() {
        FactoryException ex = assertThrows(
            FactoryException.class,
            () -> factory.createPlayer("archerX", 10, 20, mockWorld)
        );
        assertEquals("Unknown player type: archerX", ex.getMessage());
    }

    @Test
    void testCreatePlayer_Satyr_success() throws FactoryException {
        Player p = factory.createPlayer("satyr", 10, 20, mockWorld);
        assertNotNull(p);
        assertTrue(p instanceof Satyr);
    }

    @Test
    void testCreatePlayer_Satyr_failure() {
        FactoryException ex = assertThrows(
            FactoryException.class,
            () -> factory.createPlayer("satyrX", 10, 20, mockWorld)
        );
        assertEquals("Unknown player type: satyrX", ex.getMessage());
    }

    @Test
    void testCreatePlayer_DarkMage_success() throws FactoryException {
        Player p = factory.createPlayer("darkmage", 10, 20, mockWorld);
        assertNotNull(p);
        assertTrue(p instanceof DarkMage);
    }

    @Test
    void testCreatePlayer_DarkMage_failure() {
        FactoryException ex = assertThrows(
            FactoryException.class,
            () -> factory.createPlayer("darkmageX", 10, 20, mockWorld)
        );
        assertEquals("Unknown player type: darkmageX", ex.getMessage());
    }

    @Test
    void testCreatePlayer_LightMage_success() throws FactoryException {
        Player p = factory.createPlayer("lightmage", 10, 20, mockWorld);
        assertNotNull(p);
        assertTrue(p instanceof LightMage);
    }

    @Test
    void testCreatePlayer_LightMage_failure() {
        FactoryException ex = assertThrows(
            FactoryException.class,
            () -> factory.createPlayer("lightmageX", 10, 20, mockWorld)
        );
        assertEquals("Unknown player type: lightmageX", ex.getMessage());
    }

    @Test
    void testCreatePlayer_SunMage_success() throws FactoryException {
        Player p = factory.createPlayer("sunmage", 10, 20, mockWorld);
        assertNotNull(p);
        assertTrue(p instanceof SunMage);
    }

    @Test
    void testCreatePlayer_SunMage_failure() {
        FactoryException ex = assertThrows(
            FactoryException.class,
            () -> factory.createPlayer("sunmageX", 10, 20, mockWorld)
        );
        assertEquals("Unknown player type: sunmageX", ex.getMessage());
    }


    @Test
    void testCreateMob_Gorgon_success() throws FactoryException {
        Mob m = factory.createMob("gorgon", 5, 6, mockWorld, 1);
        assertNotNull(m);
        assertTrue(m instanceof Gorgon);
    }

    @Test
    void testCreateMob_Gorgon_failure() {
        FactoryException ex = assertThrows(
            FactoryException.class,
            () -> factory.createMob("gorgonX", 5, 6, mockWorld, 1)
        );
        assertEquals("Unknown mob type: gorgonX", ex.getMessage());
    }

    @Test
    void testCreateMob_Minotaur_success() throws FactoryException {
        Mob m = factory.createMob("minotaur", 5, 6, mockWorld, 1);
        assertNotNull(m);
        assertTrue(m instanceof Minotaur);
    }

    @Test
    void testCreateMob_Minotaur_failure() {
        FactoryException ex = assertThrows(
            FactoryException.class,
            () -> factory.createMob("minotaurX", 5, 6, mockWorld,1)
        );
        assertEquals("Unknown mob type: minotaurX", ex.getMessage());
    }

    @Test
    void testCreateMob_Tengu_success() throws FactoryException {
        Mob m = factory.createMob("tengu", 5, 6, mockWorld,1);
        assertNotNull(m);
        assertTrue(m instanceof Tengu);
    }

    @Test
    void testCreateMob_Tengu_failure() {
        FactoryException ex = assertThrows(
            FactoryException.class,
            () -> factory.createMob("tenguX", 5, 6, mockWorld,1)
        );
        assertEquals("Unknown mob type: tenguX", ex.getMessage());
    }

    @Test
    void testCreateMob_Yokai_success() throws FactoryException {
        Mob m = factory.createMob("yokai", 5, 6, mockWorld,1);
        assertNotNull(m);
        assertTrue(m instanceof Yokai);
    }

    @Test
    void testCreateMob_Yokai_failure() {
        FactoryException ex = assertThrows(
            FactoryException.class,
            () -> factory.createMob("yokaiX", 5, 6, mockWorld,1)
        );
        assertEquals("Unknown mob type: yokaiX", ex.getMessage());
    }


}
