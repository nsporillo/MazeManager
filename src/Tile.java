import java.awt.*;

public class Tile {

    private final int x;
    private final int y;
    private int value;

    public Tile(final int x, final int y) {
        this.x = x;
        this.y = y;
        this.value = 0; // default to open tile
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Color getColor() {
        if (value == 0) {
            return Color.WHITE;   // open
        } else if (value == 1) {
            return Color.BLUE;    // wall
        } else if (value == 2) {
            return Color.GREEN;   // start
        } else if (value == 3) {
            return Color.MAGENTA; // finish
        } else if (value == 4) {
            return Color.PINK;    // tried
        } else if (value == 5) {
            return Color.ORANGE;  // good
        }
        return Color.BLACK;
    }
}
