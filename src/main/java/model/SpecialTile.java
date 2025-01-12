package model;

public class SpecialTile extends AbstractTile {
    public SpecialTile(int x, int y) {
        super(4, x, y);
    }

    @Override
    public boolean canMerge(AbstractTile other) {
        return other != null && !(other instanceof SpecialTile);
    }

    @Override
    public void merge(AbstractTile other) {
    }
}
