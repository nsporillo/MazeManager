import java.awt.*;

public enum TileType {

    OPEN(0, Color.WHITE, false),
    WALL(1, Color.BLUE, false),
    TRIED(2, Color.PINK, false),
    START(3, Color.MAGENTA, true), // can only be one start point
    END(4, Color.CYAN, true),   // can only be one end point
    SOLVED(5, Color.GREEN, false);

    private int value;
    private Color color;
    private boolean exclusive;

    TileType(int value, Color color, boolean exclusive) {
        this.value = value;
        this.color = color;
        this.exclusive = exclusive;
    }

    public static TileType to(int value) {
        for (TileType type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("value=" + value + " is invalid for a tile");
    }

    public int value() {
        return this.value;
    }

    public Color color() {
        return this.color;
    }

    public boolean isExclusive() {
        return this.exclusive;
    }

    @Override
    public String toString() {
        return "TileType[" + "value=" + value + ", exclusive=" + exclusive + ']';
    }
}
