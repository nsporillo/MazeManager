import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Author: Nick Porillo
 * Date: 5/7/15
 * Name: MazeManager
 * Description: Enumeration which represents all possible values on the maze grid.
 */
public enum TileType {

    NULL(-1, new Color(0, 0, 0), false), // for unknown types
    OPEN(0, new Color(245, 245, 245), false),
    WALL(1, new Color(100, 120, 255), false),
    TRIED(2, new Color(255, 170, 170), false),
    START(3, new Color(0, 220, 220), true), // can only be one start point
    END(4, new Color(255, 160, 0), true),   // can only be one end point
    SOLVED(5, new Color(0, 250, 50), false);

    private int value;
    private Color color;
    private boolean exclusive;

    /**
     * Constructs a new TileType
     *
     * @param value     grid value
     * @param color     tile color
     * @param exclusive single occurrence or not
     */
    TileType(int value, Color color, boolean exclusive) {
        this.value = value;
        this.color = color;
        this.exclusive = exclusive;
    }

    /**
     * Converts a grid value to a TileType
     *
     * @param value grid value
     * @return TileType
     */
    public static TileType to(int value) {
        for (TileType type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        return NULL; // couldnt find what value was in the grid, use null
    }

    /**
     * Gets the raw value this TileType represents
     *
     * @return grid value
     */
    public int value() {
        return this.value;
    }

    /**
     * Gets this TileType's color
     *
     * @return tile color
     */
    public Color color() {
        return this.color;
    }

    /**
     * Gets if this TileType can only have 1 occurrence in the maze
     *
     * @return true or false
     */
    public boolean isExclusive() {
        return this.exclusive;
    }

    /**
     * Generates icon version of this {@link TileType}
     *
     * @param width  icon width
     * @param height icon height
     * @return a simple icon version of this tile
     */
    public BufferedImage toIcon(int width, int height) {
        BufferedImage icon = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = icon.createGraphics();
        graphics.setColor(this.color);
        graphics.fillRect(0, 0, icon.getWidth(), icon.getHeight());
        return icon;
    }

    /**
     * Generates icon version of this {@link TileType}
     *
     * @param size icon size
     * @return a simple icon version of this tile
     */
    public BufferedImage toIcon(int size) {
        return toIcon(size, size);
    }

    /**
     * Unused toString for TileType
     *
     * @return string
     */
    @Override
    public String toString() {
        return "TileType{value=" + value + ", color=" + color.toString() + ", exclusive=" + exclusive + '}';
    }
}
