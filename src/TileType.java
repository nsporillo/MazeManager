
public enum TileType {

    OPEN(0, false),
    WALL(1, false),
    TRIED(2, false),
    START(3, true), // can only be one start point
    END(4, true),   // can only be one end point
    SOLVED(5, false);

    private int value;
    private boolean exclusive;

    TileType(int value, boolean exclusive) {
        this.value = value;
        this.exclusive = exclusive;
    }

    public int value() {
        return this.value;
    }

    public static TileType to(int value) {
        for (TileType type : values()) {
            if (type.value() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("value=" + value + " is invalid for a tile");
    }

    public boolean isExclusive() {
        return this.exclusive;
    }
}
