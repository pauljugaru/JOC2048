package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void testInitialize() {
        board.initialize();
        AbstractTile[][] tiles = board.getBoard();
        int nonNullTiles = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (tiles[i][j] != null) {
                    nonNullTiles++;
                }
            }
        }

        assertEquals(2, nonNullTiles);
    }

    @Test
    void testMoveLeft() {
        AbstractTile[][] tiles = new AbstractTile[4][4];
        tiles[0][1] = new Tile(2, 0, 1);
        tiles[0][2] = new Tile(2, 0, 2);
        board.setBoard(tiles);

        boolean moved = board.move(Direction.LEFT);

        assertTrue(moved);
        assertEquals(4, board.getBoard()[0][0].getValue());
        assertNull(board.getBoard()[0][1]);
        assertNull(board.getBoard()[0][2]);
    }

    @Test
    void testMoveRight() {
        AbstractTile[][] tiles = new AbstractTile[4][4];
        tiles[0][1] = new Tile(2, 0, 1);
        tiles[0][2] = new Tile(2, 0, 2);
        board.setBoard(tiles);

        boolean moved = board.move(Direction.RIGHT);

        assertTrue(moved);
        assertNull(board.getBoard()[0][1]);
        assertNull(board.getBoard()[0][2]);
        assertEquals(4, board.getBoard()[0][3].getValue());
    }

    @Test
    void testMoveUp() {
        AbstractTile[][] tiles = new AbstractTile[4][4];
        tiles[1][0] = new Tile(2, 1, 0);
        tiles[2][0] = new Tile(2, 2, 0);
        board.setBoard(tiles);

        boolean moved = board.move(Direction.UP);

        assertTrue(moved);
        assertEquals(4, board.getBoard()[0][0].getValue());
        assertNull(board.getBoard()[1][0]);
        assertNull(board.getBoard()[2][0]);
    }

    @Test
    void testMoveDown() {
        AbstractTile[][] tiles = new AbstractTile[4][4];
        tiles[1][0] = new Tile(2, 1, 0);
        tiles[2][0] = new Tile(2, 2, 0);
        board.setBoard(tiles);

        boolean moved = board.move(Direction.DOWN);

        assertTrue(moved);
        assertNull(board.getBoard()[1][0]);
        assertNull(board.getBoard()[2][0]);
        assertEquals(4, board.getBoard()[3][0].getValue());
    }

    @Test
    void testIsGameOver() {
        AbstractTile[][] tiles = new AbstractTile[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[i][j] = new Tile((i * 4 + j + 1) * 2, i, j);
            }
        }
        board.setBoard(tiles);

        assertTrue(board.isGameOver());
    }

    @Test
    void testSpecialTileMerge() {
        AbstractTile[][] tiles = new AbstractTile[4][4];
        tiles[0][0] = new SpecialTile(0, 0);
        tiles[0][1] = new Tile(2, 0, 1);
        board.setBoard(tiles);

        board.move(Direction.LEFT);

        assertEquals(8, board.getBoard()[0][0].getValue());
        assertFalse(board.getBoard()[0][0] instanceof SpecialTile);
    }

    @Test
    void testScore() {
        AbstractTile[][] tiles = new AbstractTile[4][4];
        tiles[0][0] = new Tile(2, 0, 0);
        tiles[0][1] = new Tile(2, 0, 1);
        board.setBoard(tiles);

        board.move(Direction.LEFT);

        assertEquals(4, board.getScore());
    }
}