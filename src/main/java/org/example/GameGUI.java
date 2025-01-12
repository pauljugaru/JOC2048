package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import java.io.File;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Direction;
import model.Game2048;
import model.AbstractTile;
import model.SpecialTile;

public class GameGUI extends Application {
    private static final int TILE_SIZE = 100;
    private static final int GAP = 10;
    private Game2048 game;
    private GridPane gameBoard;
    private Label scoreLabel;

    @Override
    public void start(Stage primaryStage) {
        game = new Game2048();
        game.initialize();

        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #F5F5F5;");
        root.setAlignment(Pos.CENTER);

        HBox header = createHeader();
        root.getChildren().add(header);

        gameBoard = createGameBoard();
        root.getChildren().add(gameBoard);

        Scene scene = new Scene(root);
        scene.setOnKeyPressed(this::handleKeyPress);

        primaryStage.setTitle("2048 Game");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        updateBoard();
    }

    private HBox createHeader() {
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER);

        Label title = new Label("2048");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        title.setTextFill(Color.web("#003366"));

        VBox scoreBox = new VBox(5);
        scoreBox.setAlignment(Pos.CENTER);
        scoreBox.setStyle("-fx-background-color: #004d40; -fx-padding: 10; -fx-background-radius: 5;");

        Label scoreTitle = new Label("SCORE");
        scoreTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        scoreTitle.setTextFill(Color.web("#FFFFFF"));

        scoreLabel = new Label("0");
        scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        scoreLabel.setTextFill(Color.web("#FFFFFF"));

        scoreBox.getChildren().addAll(scoreTitle, scoreLabel);
        header.getChildren().addAll(title, scoreBox);

        return header;
    }

    private GridPane createGameBoard() {
        GridPane grid = new GridPane();
        grid.setHgap(GAP);
        grid.setVgap(GAP);
        grid.setStyle("-fx-background-color: #B0BEC5; -fx-padding: 10; -fx-background-radius: 5;");

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                StackPane cell = createCell();
                grid.add(cell, col, row);
            }
        }

        return grid;
    }

    private StackPane createCell() {
        StackPane cell = new StackPane();
        cell.setPrefSize(TILE_SIZE, TILE_SIZE);
        cell.setStyle("-fx-background-color: #ECEFF1; -fx-background-radius: 5;");
        return cell;
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
            case W:
                game.move(Direction.UP);
                break;
            case DOWN:
            case S:
                game.move(Direction.DOWN);
                break;
            case LEFT:
            case A:
                game.move(Direction.LEFT);
                break;
            case RIGHT:
            case D:
                game.move(Direction.RIGHT);
                break;
            case R:
                resetGame();
                break;
            default:
                return;
        }
        updateBoard();
        if (game.isGameOver()) {
            showGameOver();
        }
    }

    private void resetGame() {
        try {
            File saveFile = new File("game_save.json");
            if (saveFile.exists()) {
                saveFile.delete();
            }
        } catch (Exception e) {
            System.err.println("Eroare la ștergerea salvării: " + e.getMessage());
        }

        game = new Game2048();
        game.initialize();

        scoreLabel.setText("0");

        updateBoard();
    }



    private void updateBoard() {
        AbstractTile[][] board = game.getBoard();
        scoreLabel.setText(String.valueOf(game.getScore()));

        gameBoard.getChildren().clear();
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                StackPane cell = createCell();
                if (board[row][col] != null) {
                    String displayValue;
                    if (board[row][col] instanceof SpecialTile) {
                        displayValue = "X4";
                    } else {
                        displayValue = String.valueOf(board[row][col].getValue());
                    }

                    Label valueLabel = new Label(displayValue);
                    valueLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
                    valueLabel.setTextFill(Color.web("#263238"));

                    String style;
                    if (board[row][col] instanceof SpecialTile) {
                        style = "-fx-background-color: #9575CD; -fx-background-radius: 5;";
                    } else {
                        style = getTileStyle(board[row][col].getValue());
                    }

                    cell.setStyle(style);
                    cell.getChildren().add(valueLabel);
                }
                gameBoard.add(cell, col, row);
            }
        }
    }

    private String getTileStyle(int value) {
        String backgroundColor;
        switch (value) {
            case 2:    backgroundColor = "#C8E6C9"; break;
            case 4:    backgroundColor = "#A5D6A7"; break;
            case 8:    backgroundColor = "#81C784"; break;
            case 16:   backgroundColor = "#66BB6A"; break;
            case 32:   backgroundColor = "#4CAF50"; break;
            case 64:   backgroundColor = "#388E3C"; break;
            case 128:  backgroundColor = "#29B6F6"; break;
            case 256:  backgroundColor = "#039BE5"; break;
            case 512:  backgroundColor = "#0288D1"; break;
            case 1024: backgroundColor = "#0277BD"; break;
            case 2048: backgroundColor = "#01579B"; break;
            case 4096: backgroundColor = "#01437B"; break;
            default:   backgroundColor = "#ECEFF1"; break;
        }
        return String.format("-fx-background-color: %s; -fx-background-radius: 5;", backgroundColor);
    }

    private void showGameOver() {
        VBox gameOverBox = new VBox(10);
        gameOverBox.setAlignment(Pos.CENTER);
        gameOverBox.setStyle("-fx-background-color: rgba(236, 239, 241, 0.9);");
        gameOverBox.setPrefSize(TILE_SIZE * 4 + GAP * 3, TILE_SIZE * 4 + GAP * 3);

        Label gameOverLabel = new Label("Game Over!");
        gameOverLabel.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        gameOverLabel.setTextFill(Color.web("#003366"));

        Label finalScoreLabel = new Label("Final Score: " + game.getScore());
        finalScoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        finalScoreLabel.setTextFill(Color.web("#003366"));

        gameOverBox.getChildren().addAll(gameOverLabel, finalScoreLabel);
        gameBoard.add(gameOverBox, 0, 0, 4, 4);

        game.resetSave();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
