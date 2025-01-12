package model;

public abstract class AbstractTile {
    protected int value;
    protected int x;
    protected int y;

    public AbstractTile(int value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
    }

    public abstract boolean canMerge(AbstractTile other);
    public abstract void merge(AbstractTile other);

    public int getValue() {
        return value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
