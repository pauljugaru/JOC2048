package model;

import interfaces.IGame;
import interfaces.IBoard;

public class Game2048 implements IGame {
    private final IBoard board;
    private final GamePersistence gamePersistence;

    public Game2048() {
        this.board = new Board();
        this.gamePersistence = new GamePersistence();
    }

    @Override
    public void initialize() {

        gamePersistence.loadGame((Board) board);

        boolean isEmpty = true;
        AbstractTile[][] tiles = board.getBoard();
        for (AbstractTile[] row : tiles) {
            for (AbstractTile tile : row) {
                if (tile != null) {
                    isEmpty = false;
                    break;
                }
            }
        }

        if (isEmpty) {
            board.initialize();
        }
    }

    @Override
    public boolean move(Direction direction) {
        boolean moved = board.move(direction);
        if (moved) {
            board.addNewTile();
            gamePersistence.saveGame((Board) board);
        }
        return moved;
    }

    public void resetSave() {
        gamePersistence.deleteSave();
    }

    @Override
    public boolean isGameOver() {
        return board.isGameOver();
    }

    @Override
    public int getScore() {
        return board.getScore();
    }

    public AbstractTile[][] getBoard() {
        return board.getBoard();
    }

    @Override
    public void addNewTile() {
        board.addNewTile();
    }
}
