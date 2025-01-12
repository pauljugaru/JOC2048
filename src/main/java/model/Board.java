package model;

import interfaces.IBoard;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board implements IBoard {
    private AbstractTile[][] board;
    private Random random;
    private int score;
    private static final int SIZE = 4;
    private boolean specialTileSpawned;

    public Board() {
        this.board = new AbstractTile[SIZE][SIZE];
        this.random = new Random();
        this.score = 0;
        this.specialTileSpawned = false;
        initialize();
    }

    @Override
    public void initialize() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = null;
            }
        }
        addNewTile();
        addNewTile();
    }

    @Override
    public boolean move(Direction direction) {
        boolean moved = false;
        switch (direction) {
            case UP:
                moved = moveUp();
                break;
            case DOWN:
                moved = moveDown();
                break;
            case LEFT:
                moved = moveLeft();
                break;
            case RIGHT:
                moved = moveRight();
                break;
        }
        return moved;
    }

    private boolean moveLeft() {
        boolean moved = false;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 1; j < SIZE; j++) {
                if (board[i][j] != null) {
                    int col = j;
                    while (col > 0 && board[i][col-1] == null) {
                        board[i][col-1] = board[i][col];
                        board[i][col] = null;
                        board[i][col-1].setPosition(i, col-1);
                        col--;
                        moved = true;
                    }
                }
            }
        }

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE-1; j++) {
                if (board[i][j] != null && board[i][j+1] != null) {
                    if (board[i][j] instanceof SpecialTile && !(board[i][j+1] instanceof SpecialTile)) {
                        int newValue = board[i][j+1].getValue() * 4;
                        board[i][j] = new Tile(newValue, i, j);
                        board[i][j+1] = null;
                        score += newValue;
                        moved = true;
                    } else if (board[i][j+1] instanceof SpecialTile && !(board[i][j] instanceof SpecialTile)) {
                        int newValue = board[i][j].getValue() * 4;
                        board[i][j] = new Tile(newValue, i, j);
                        board[i][j+1] = null;
                        score += newValue;
                        moved = true;
                    } else if (board[i][j].canMerge(board[i][j+1])) {
                        board[i][j].merge(board[i][j+1]);
                        score += board[i][j].getValue();
                        board[i][j+1] = null;
                        moved = true;
                    }
                }
            }
        }

        for (int i = 0; i < SIZE; i++) {
            for (int j = 1; j < SIZE; j++) {
                if (board[i][j] != null) {
                    int col = j;
                    while (col > 0 && board[i][col-1] == null) {
                        board[i][col-1] = board[i][j];
                        board[i][col] = null;
                        board[i][col-1].setPosition(i, col-1);
                        col--;
                        moved = true;
                    }
                }
            }
        }
        return moved;
    }

    private boolean moveRight() {
        boolean moved = false;

        for (int i = 0; i < SIZE; i++) {
            for (int j = SIZE-2; j >= 0; j--) {
                if (board[i][j] != null) {
                    int col = j;
                    while (col < SIZE-1 && board[i][col+1] == null) {
                        board[i][col+1] = board[i][col];
                        board[i][col] = null;
                        board[i][col+1].setPosition(i, col+1);
                        col++;
                        moved = true;
                    }
                }
            }
        }

        for (int i = 0; i < SIZE; i++) {
            for (int j = SIZE-1; j > 0; j--) {
                if (board[i][j] != null && board[i][j-1] != null) {
                    if (board[i][j] instanceof SpecialTile && !(board[i][j-1] instanceof SpecialTile)) {
                        int newValue = board[i][j-1].getValue() * 4;
                        board[i][j] = new Tile(newValue, i, j);
                        board[i][j-1] = null;
                        score += newValue;
                        moved = true;
                    } else if (board[i][j-1] instanceof SpecialTile && !(board[i][j] instanceof SpecialTile)) {
                        int newValue = board[i][j].getValue() * 4;
                        board[i][j] = new Tile(newValue, i, j);
                        board[i][j-1] = null;
                        score += newValue;
                        moved = true;
                    } else if (board[i][j].canMerge(board[i][j-1])) {
                        board[i][j].merge(board[i][j-1]);
                        score += board[i][j].getValue();
                        board[i][j-1] = null;
                        moved = true;
                    }
                }
            }
        }

        for (int i = 0; i < SIZE; i++) {
            for (int j = SIZE-2; j >= 0; j--) {
                if (board[i][j] != null) {
                    int col = j;
                    while (col < SIZE-1 && board[i][col+1] == null) {
                        board[i][col+1] = board[i][j];
                        board[i][col] = null;
                        board[i][col+1].setPosition(i, col+1);
                        col++;
                        moved = true;
                    }
                }
            }
        }
        return moved;
    }

    private boolean moveUp() {
        boolean moved = false;

        for (int j = 0; j < SIZE; j++) {
            for (int i = 1; i < SIZE; i++) {
                if (board[i][j] != null) {
                    int row = i;
                    while (row > 0 && board[row-1][j] == null) {
                        board[row-1][j] = board[row][j];
                        board[row][j] = null;
                        board[row-1][j].setPosition(row-1, j);
                        row--;
                        moved = true;
                    }
                }
            }
        }

        for (int j = 0; j < SIZE; j++) {
            for (int i = 0; i < SIZE-1; i++) {
                if (board[i][j] != null && board[i+1][j] != null) {
                    if (board[i][j] instanceof SpecialTile && !(board[i+1][j] instanceof SpecialTile)) {
                        int newValue = board[i+1][j].getValue() * 4;
                        board[i][j] = new Tile(newValue, i, j);
                        board[i+1][j] = null;
                        score += newValue;
                        moved = true;
                    } else if (board[i+1][j] instanceof SpecialTile && !(board[i][j] instanceof SpecialTile)) {
                        int newValue = board[i][j].getValue() * 4;
                        board[i][j] = new Tile(newValue, i, j);
                        board[i+1][j] = null;
                        score += newValue;
                        moved = true;
                    } else if (board[i][j].canMerge(board[i+1][j])) {
                        board[i][j].merge(board[i+1][j]);
                        score += board[i][j].getValue();
                        board[i+1][j] = null;
                        moved = true;
                    }
                }
            }
        }

        for (int j = 0; j < SIZE; j++) {
            for (int i = 1; i < SIZE; i++) {
                if (board[i][j] != null) {
                    int row = i;
                    while (row > 0 && board[row-1][j] == null) {
                        board[row-1][j] = board[i][j];
                        board[row][j] = null;
                        board[row-1][j].setPosition(row-1, j);
                        row--;
                        moved = true;
                    }
                }
            }
        }
        return moved;
    }

    private boolean moveDown() {
        boolean moved = false;

        for (int j = 0; j < SIZE; j++) {
            for (int i = SIZE-2; i >= 0; i--) {
                if (board[i][j] != null) {
                    int row = i;
                    while (row < SIZE-1 && board[row+1][j] == null) {
                        board[row+1][j] = board[row][j];
                        board[row][j] = null;
                        board[row+1][j].setPosition(row+1, j);
                        row++;
                        moved = true;
                    }
                }
            }
        }

        for (int j = 0; j < SIZE; j++) {
            for (int i = SIZE-1; i > 0; i--) {
                if (board[i][j] != null && board[i-1][j] != null) {
                    if (board[i][j] instanceof SpecialTile && !(board[i-1][j] instanceof SpecialTile)) {
                        int newValue = board[i-1][j].getValue() * 4;
                        board[i][j] = new Tile(newValue, i, j);
                        board[i-1][j] = null;
                        score += newValue;
                        moved = true;
                    } else if (board[i-1][j] instanceof SpecialTile && !(board[i][j] instanceof SpecialTile)) {
                        int newValue = board[i][j].getValue() * 4;
                        board[i][j] = new Tile(newValue, i, j);
                        board[i-1][j] = null;
                        score += newValue;
                        moved = true;
                    } else if (board[i][j].canMerge(board[i-1][j])) {
                        board[i][j].merge(board[i-1][j]);
                        score += board[i][j].getValue();
                        board[i-1][j] = null;
                        moved = true;
                    }
                }
            }
        }

        for (int j = 0; j < SIZE; j++) {
            for (int i = SIZE-2; i >= 0; i--) {
                if (board[i][j] != null) {
                    int row = i;
                    while (row < SIZE-1 && board[row+1][j] == null) {
                        board[row+1][j] = board[i][j];
                        board[i][j] = null;
                        board[row+1][j].setPosition(row+1, j);
                        row++;
                        moved = true;
                    }
                }
            }
        }
        return moved;
    }

    @Override
    public void addNewTile() {
        List<int[]> emptySpots = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == null) {
                    emptySpots.add(new int[]{i, j});
                }
            }
        }

        if (!emptySpots.isEmpty()) {
            int[] spot = emptySpots.get(random.nextInt(emptySpots.size()));

            if (!specialTileSpawned && random.nextDouble() < 0.05) {
                board[spot[0]][spot[1]] = new SpecialTile(spot[0], spot[1]);
                specialTileSpawned = true;
            } else {
                board[spot[0]][spot[1]] = new Tile(random.nextDouble() < 0.9 ? 2 : 4, spot[0], spot[1]);
            }
        }
    }

    @Override
    public boolean isGameOver() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == null) return false;
            }
        }

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (j < SIZE-1 && board[i][j].canMerge(board[i][j+1])) return false;
                if (i < SIZE-1 && board[i][j].canMerge(board[i+1][j])) return false;
            }
        }
        return true;
    }

    @Override
    public AbstractTile[][] getBoard() {
        return board;
    }

    @Override
    public void setBoard(AbstractTile[][] board) {
        this.board = board;
    }

    @Override
    public int getScore() {
        return score;
    }
}
