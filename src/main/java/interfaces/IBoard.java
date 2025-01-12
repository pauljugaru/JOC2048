package interfaces;

import model.AbstractTile;
import model.Direction;

public interface IBoard {
    void initialize();
    boolean move(Direction direction);
    void addNewTile();
    boolean isGameOver();
    int getScore();
    AbstractTile[][] getBoard();
    void setBoard(AbstractTile[][] board);
}
