package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class GameState {
    private final int score;
    private final List<TileState> tiles;

    @JsonCreator
    public GameState(
            @JsonProperty("score") int score,
            @JsonProperty("tiles") List<TileState> tiles) {
        this.score = score;
        this.tiles = tiles;
    }

    public int getScore() {
        return score;
    }

    public List<TileState> getTiles() {
        return tiles;
    }

    public static GameState fromBoard(Board board) {
        List<TileState> tileStates = new ArrayList<>();
        AbstractTile[][] boardTiles = board.getBoard();

        for (int i = 0; i < boardTiles.length; i++) {
            for (int j = 0; j < boardTiles[i].length; j++) {
                AbstractTile tile = boardTiles[i][j];
                if (tile != null) {
                    tileStates.add(new TileState(
                            tile.getValue(),
                            tile.getX(),
                            tile.getY(),
                            tile instanceof SpecialTile
                    ));
                }
            }
        }

        return new GameState(board.getScore(), tileStates);
    }
}

class TileState {
    private final int value;
    private final int x;
    private final int y;
    private final boolean isSpecial;

    @JsonCreator
    public TileState(
            @JsonProperty("value") int value,
            @JsonProperty("x") int x,
            @JsonProperty("y") int y,
            @JsonProperty("special") boolean isSpecial) {
        this.value = value;
        this.x = x;
        this.y = y;
        this.isSpecial = isSpecial;
    }

    public int getValue() {
        return value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @JsonProperty("special")
    public boolean isSpecial() {
        return isSpecial;
    }
}
