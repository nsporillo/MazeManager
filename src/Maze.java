import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.math.BigDecimal;

/**
 * Author: Nick Porillo
 * Date: 5/7/15
 * Name: Maze
 * Description: Object that represents a maze. Handles solving and rendering
 */
public class Maze extends JPanel {

    private TileType tooltip;
    private Point start, finish;

    private String name;
    private int rows, cols;
    private double rowSize, colSize;
    protected boolean solved = false, paint = false;

    protected int[][] grid;

    /**
     * Constructs a new maze
     *
     * @param name maze name
     * @param grid maze grid
     */
    public Maze(String name, int[][] grid) {
        this.name = name;
        this.rows = grid.length;
        this.cols = grid[0].length;
        this.grid = grid;
        this.load();
    }

    /**
     * Updates this maze
     *
     * @param other new maze
     */
    public void change(Maze other) {
        this.name = other.name;
        this.rows = other.rows;
        this.cols = other.cols;
        this.grid = other.grid;
        this.load();
    }

    /**
     * Handles various maze loading tasks
     */
    public void load() {
        this.colSize = getPreferredSize().getWidth() / (double) this.cols;
        this.rowSize = getPreferredSize().getHeight() / (double) this.rows;
        this.tooltip = TileType.START;
        this.paint = true;
        this.solved = false;
        this.start = this.findValue(TileType.START);
        this.finish = this.findValue(TileType.END);
        super.setVisible(true);
    }

    /**
     * This is the size of the maze panel
     * Adjusting this changes entire app size, since parent component packs
     *
     * @return size of the maze panel
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(550, 450);
    }

    /**
     * Handles rendering the maze
     * Use double precision! Our row and column sizes aren't always even ints!
     * Our app height and width is constant, so the sizes are often decimals
     *
     * @param g graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        if (!paint) {
            return; // dont update
        }
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // render each tile in grid
        for (int y = 0; y < cols; y++) {
            for (int x = 0; x < rows; x++) {
                TileType type = TileType.to(grid[x][y]);
                g2.setColor(type.color());
                g2.fill(new Rectangle2D.Double(y * colSize, x * rowSize, colSize, rowSize));
            }
        }

        // render black grid lines
        g2.setColor(Color.black);
        for (int r = 0; r <= rows; r++) {
            double size = r * rowSize;
            g2.draw(new Line2D.Double(0, size, this.getWidth(), size));
        }
        for (int c = 0; c <= cols; c++) {
            double size = c * colSize;
            g2.draw(new Line2D.Double(size, 0, size, this.getHeight()));
        }
    }

    /**
     * Resets maze by clearing all tried or solved tiles
     */
    public void reset() {
        for (int y = 0; y < cols; y++) {
            for (int x = 0; x < rows; x++) {
                TileType type = TileType.to(grid[x][y]);
                if (type == TileType.TRIED || type == TileType.SOLVED) {
                    grid[x][y] = TileType.OPEN.value();
                }
            }
        }
        this.repaint();
        this.solved = false;
    }

    /**
     * Find all adjacent tiles
     *
     * @param tile base tile
     * @return array of all adjacent tiles
     */
    private Point[] findAdjacent(Point tile) {
        Point[] adjacent = new Point[4];
        adjacent[0] = new Point(tile.x + 1, tile.y); // right
        adjacent[1] = new Point(tile.x - 1, tile.y); // left
        adjacent[2] = new Point(tile.x, tile.y - 1); // up
        adjacent[3] = new Point(tile.x, tile.y + 1); // down
        return adjacent;
    }

    /**
     * Recursively solve maze
     * Uses Depth-first search algorithm
     * Start at root node and traverse as far as we can
     * Backtrack if we dont find the end point
     *
     * @param tile current tile
     * @return true if solved
     */
    private boolean traverse(Point tile) {
        if (isFinished(tile)) {
            grid[tile.x][tile.y] = TileType.END.value();
            return true;
        }
        Point[] adjacent = findAdjacent(tile);
        for (Point adja : adjacent) {
            if (isOpenTile(adja)) {
                enter(adja); // set tile to solved
                if (traverse(adja)) {
                    // true if this maze path finds the end point!
                    return true;
                }
                exit(adja); // tile wasn't in solution, set to tried
            }
        }
        return false;
    }

    /**
     * Find the {@link Point} in the grid
     * For exclusive TileTypes only...
     *
     * @param type type to find
     * @return single point of the TileType
     */
    private Point findValue(TileType type) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == type.value()) {
                    return new Point(i, j);
                }
            }
        }
        return null;
    }

    /**
     * Gets if a raw point is within the grid
     *
     * @param raw grid point
     * @return if point is within grid
     */
    public boolean isTile(Point raw) {
        if (raw.x < 0 || raw.x > getWidth() || raw.y < 0 || raw.y > getHeight()) {
            // raw point is outside maze panel
            return false;
        }
        Point tile = convert(raw);
        // check if tile is inside the grid
        return !(tile.x < 0 || tile.x >= cols || tile.y < 0 || tile.y >= rows);
    }

    /**
     * Gets if a point in the grid is OPEN
     *
     * @param tile Search tile
     * @return if tile is free
     */
    private boolean isOpenTile(Point tile) {
        if (tile.x < 0 || tile.x >= rows || tile.y < 0 || tile.y >= cols) {
            return false;
        }
        // check if tile is open or the end tile(needs to be open so we can solve)
        return grid[tile.x][tile.y] == TileType.OPEN.value() || grid[tile.x][tile.y] == TileType.END.value();
    }

    /**
     * Sets grid point to tried
     * Used when point was tried, but isn't part of solution
     *
     * @param tile grid point
     */
    private void exit(Point tile) {
        grid[tile.x][tile.y] = TileType.TRIED.value();
    }

    /**
     * Sets grid point to solved
     * is left alone if point is part of the solution
     *
     * @param tile grid point
     */
    private void enter(Point tile) {
        grid[tile.x][tile.y] = TileType.SOLVED.value();
    }

    /**
     * Gets if the maze reached the finish point
     *
     * @param current tile
     * @return if maze is finished
     */
    private boolean isFinished(Point current) {
        return current.equals(this.finish);
    }

    /**
     * Converts an x and y from a mouse click to a grid point
     *
     * @param rawX mouseX
     * @param rawY mouseY
     * @return grid point value
     */
    public int getValueAt(int rawX, int rawY) {
        Point tile = convert(new Point(rawX, rawY));
        return grid[tile.y][tile.x];
    }

    /**
     * Converts a raw mouse point to a grid point
     * Uses BigDecimal for accurate modulus operations
     *
     * @param raw raw mouse point
     * @return grid point
     */
    private Point convert(Point raw) {
        BigDecimal bdX = new BigDecimal(raw.x / colSize);
        BigDecimal bdCols = new BigDecimal(cols);
        BigDecimal bdY = new BigDecimal(raw.y / rowSize);
        BigDecimal bdRows = new BigDecimal(rows);
        int x = bdX.remainder(bdCols).intValue();
        int y = bdY.remainder(bdRows).intValue();
        return new Point(x, y);
    }

    /**
     * Sets a raw point to a TileType
     *
     * @param raw  grid point
     * @param type new type
     * @return if successful
     */
    public boolean setValueAt(Point raw, TileType type) {
        TileType old = TileType.to(getValueAt(raw.x, raw.y));
        if (type != old) {
            if (type.isExclusive() && this.findValue(type) != null) {
                setTooltip(TileType.WALL); // auto set tooltip to wall :)
                return false; // can't set more than one exclusive tile
            }
            Point tile = convert(raw);
            grid[tile.y][tile.x] = type.value();
            if (old.isExclusive() || type.isExclusive()) {
                // we made a tile change involving exclusive types
                // good idea to update refs to avoid null pointers
                this.start = this.findValue(TileType.START);
                this.finish = this.findValue(TileType.END);
            }
            super.repaint();
            return true;
        }
        return false;
    }

    /**
     * Solves the maze!
     *
     * @return if maze can be solved
     */
    public boolean solve() {
        if (start == null || finish == null) {
            return false;
        }
        this.solved = traverse(this.start);
        return solved;
    }

    /**
     * Gets if this maze needs to be reset to be solved again
     *
     * @return if maze has been solved
     */
    public boolean isSolved() {
        return solved;
    }

    /**
     * Gets the current tile editor value
     * is used to determine what tile to place on a mouse click
     *
     * @return current tooltip
     */
    public TileType getTooltip() {
        return tooltip;
    }

    /**
     * Sets the current tile editor value
     *
     * @param tooltip new tooltip
     */
    public void setTooltip(TileType tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * Override super class get name method with our own
     *
     * @return maze name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Get number of rows in the maze
     *
     * @return # of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Get number of columns in the maze
     *
     * @return # of columns
     */
    public int getCols() {
        return cols;
    }

    /**
     * Sets name of maze
     *
     * @param name new name
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }
}
