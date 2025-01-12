package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class GamePersistence {
    private static final String SAVE_FILE = "game_save.json";
    private final ObjectMapper objectMapper;

    public GamePersistence() {
        this.objectMapper = new ObjectMapper();
    }


    public void deleteSave() {
        File saveFile = Paths.get(SAVE_FILE).toFile();
        if (saveFile.exists()) {
            saveFile.delete();
        }
    }
    public void saveGame(Board board) {
        try {
            GameState gameState = GameState.fromBoard(board);
            objectMapper.writeValue(new File(SAVE_FILE), gameState);
        } catch (IOException e) {
            System.err.println("Eroare la salvarea jocului: " + e.getMessage());
        }
    }

    public void loadGame(Board board) {
        File saveFile = Paths.get(SAVE_FILE).toFile();
        if (!saveFile.exists()) {
            return;
        }

        try {
            GameState gameState = objectMapper.readValue(saveFile, GameState.class);

            AbstractTile[][] tiles = new AbstractTile[4][4];

            for (TileState tileState : gameState.getTiles()) {
                AbstractTile tile;
                if (tileState.isSpecial()) {
                    tile = new SpecialTile(tileState.getX(), tileState.getY());
                } else {
                    tile = new Tile(tileState.getValue(), tileState.getX(), tileState.getY());
                }
                tiles[tileState.getX()][tileState.getY()] = tile;
            }

            board.setBoard(tiles);

            try {
                java.lang.reflect.Field scoreField = Board.class.getDeclaredField("score");
                scoreField.setAccessible(true);
                scoreField.set(board, gameState.getScore());
            } catch (Exception e) {
                System.err.println("Eroare la setarea scorului: " + e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("Eroare la încărcarea jocului: " + e.getMessage());
        }
    }
}