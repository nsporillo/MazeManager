import java.awt.*;
import java.awt.image.BufferedImage;

public enum TileType {

    OPEN(0, new Color(245, 245, 245), false),
    WALL(1, new Color(100, 120, 255), false),
    TRIED(2, new Color(255, 170, 170), false),
    START(3, new Color(220, 220, 0), true), // can only be one start point
    END(4, new Color(255, 160, 0), true),   // can only be one end point
    SOLVED(5, new Color(0, 250, 50), false);

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

    public BufferedImage toIcon(int width, int height) {
        BufferedImage icon = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = icon.createGraphics();
        graphics.setColor(this.color);
        graphics.fillRect(0, 0, icon.getWidth(), icon.getHeight());
        return icon;
    }

    @Override
    public String toString() {
        return "TileType[" + "value=" + value + ", exclusive=" + exclusive + ']';
    }
}
