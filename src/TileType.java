
public enum TileType {

    OPEN(0), WALL(1), TRIED(2), START(3), END(4), SOLVED(5);

    private int value;

    TileType(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

    public static TileType to(int value) {
        if(value == 0) {
            return OPEN;
        } else if(value == 1) {
            return WALL;
        } else if(value == 2) {
            return TRIED;
        } else if(value == 3) {
            return START;
        } else if(value == 4) {
            return END;
        } else if(value == 5) {
            return SOLVED;
        }
        throw new IllegalArgumentException("value=" + value + " is invalid for a tile");
    }
}
