package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    @Test
    void testTileCreation() {
        Tile tile = new Tile(2, 1, 1);
        assertEquals(2, tile.getValue());
        assertEquals(1, tile.getX());
        assertEquals(1, tile.getY());
    }

    @Test
    void testCanMergeWithEqualValue() {
        Tile tile1 = new Tile(2, 0, 0);
        Tile tile2 = new Tile(2, 0, 1);
        assertTrue(tile1.canMerge(tile2));
    }

    @Test
    void testCannotMergeWithDifferentValue() {
        Tile tile1 = new Tile(2, 0, 0);
        Tile tile2 = new Tile(4, 0, 1);
        assertFalse(tile1.canMerge(tile2));
    }

    @Test
    void testCanMergeWithSpecialTile() {
        Tile tile = new Tile(2, 0, 0);
        SpecialTile specialTile = new SpecialTile(0, 1);
        assertTrue(tile.canMerge(specialTile));
    }

    @Test
    void testMergeWithRegularTile() {
        Tile tile1 = new Tile(2, 0, 0);
        Tile tile2 = new Tile(2, 0, 1);
        tile1.merge(tile2);
        assertEquals(4, tile1.getValue());
    }

    @Test
    void testMergeWithSpecialTile() {
        Tile tile = new Tile(2, 0, 0);
        SpecialTile specialTile = new SpecialTile(0, 1);
        tile.merge(specialTile);
        assertEquals(8, tile.getValue());
    }

    @Test
    void testSetPosition() {
        Tile tile = new Tile(2, 0, 0);
        tile.setPosition(1, 1);
        assertEquals(1, tile.getX());
        assertEquals(1, tile.getY());
    }
}