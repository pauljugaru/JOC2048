package model;

public class Tile extends AbstractTile {
    public Tile(int value) {
        super(value, 0, 0);
    }

    public Tile(int value, int x, int y) {
        super(value, x, y);
    }

    @Override
    public boolean canMerge(AbstractTile other) {
        if (other instanceof SpecialTile) {
            return true;
        }
        return other != null && this.value == other.getValue();
    }

    @Override
    public void merge(AbstractTile other) {
        if (other instanceof SpecialTile) {
            this.value = this.value * 4;
        } else {
            this.value *= 2;
        }
    }
}
