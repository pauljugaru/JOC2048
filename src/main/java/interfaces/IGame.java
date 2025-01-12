package interfaces;

import model.Direction;

public interface IGame {
    void initialize();
    boolean move(Direction direction);
    boolean isGameOver();
    int getScore();
    void addNewTile();
}
