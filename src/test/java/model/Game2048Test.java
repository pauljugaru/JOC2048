package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Game2048Test {
    private Game2048 game;

    @BeforeEach
    void setUp() {
        game = new Game2048();
    }

    @Test
    void testMove() {
        game.initialize();
        boolean moved = game.move(Direction.LEFT);
        assertNotNull(game.getBoard());
    }

    @Test
    void testIsGameOver() {
        game.initialize();
        assertFalse(game.isGameOver());
    }

    @Test
    void testGetScore() {
        game.initialize();
        assertEquals(0, game.getScore());
    }

    @Test
    void testAddNewTile() {
        game.initialize();
        int initialTiles = countNonNullTiles(game.getBoard());
        game.addNewTile();
        int afterTiles = countNonNullTiles(game.getBoard());

        assertEquals(initialTiles + 1, afterTiles);
    }

    private int countNonNullTiles(AbstractTile[][] board) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] != null) {
                    count++;
                }
            }
        }
        return count;
    }
}