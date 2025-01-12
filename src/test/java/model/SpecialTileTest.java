package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SpecialTileTest {

    @Test
    void testSpecialTileCreation() {
        SpecialTile specialTile = new SpecialTile(1, 1);
        assertEquals(4, specialTile.getValue());
        assertEquals(1, specialTile.getX());
        assertEquals(1, specialTile.getY());
    }

    @Test
    void testCanMergeWithRegularTile() {
        SpecialTile specialTile = new SpecialTile(0, 0);
        Tile regularTile = new Tile(2, 0, 1);
        assertTrue(specialTile.canMerge(regularTile));
    }

    @Test
    void testCannotMergeWithSpecialTile() {
        SpecialTile specialTile1 = new SpecialTile(0, 0);
        SpecialTile specialTile2 = new SpecialTile(0, 1);
        assertFalse(specialTile1.canMerge(specialTile2));
    }

    @Test
    void testCannotMergeWithNull() {
        SpecialTile specialTile = new SpecialTile(0, 0);
        assertFalse(specialTile.canMerge(null));
    }

    @Test
    void testSetPosition() {
        SpecialTile specialTile = new SpecialTile(0, 0);
        specialTile.setPosition(1, 1);
        assertEquals(1, specialTile.getX());
        assertEquals(1, specialTile.getY());
    }
}