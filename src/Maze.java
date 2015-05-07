import javax.swing.*;
import java.awt.*;

public class Maze extends JPanel {

    private TileType tooltip;
    private Point start, finish;

    private String name;
    private int rows, cols, rowSize, colSize;
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
        this.colSize = (int) ((double) getPreferredSize().width / (double) this.cols);
        this.rowSize = (int) ((double) getPreferredSize().height / (double) this.rows);
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
     *
     * @param g graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        if (!paint) {
            return; // dont update
        }
        super.paintComponent(g);

        // render each tile in grid
        for (int y = 0; y < cols; y++) {
            for (int x = 0; x < rows; x++) {
                TileType type = TileType.to(grid[x][y]);
                g.setColor(type.color());
                g.fillRect(y * colSize, x * rowSize, colSize, rowSize);
            }
        }

        // render black grid lines
        g.setColor(Color.black);
        for (int r = 0; r <= rows; r++) {
            int size = r * rowSize;
            g.drawLine(0, size, getPreferredSize().width, size);
        }
        for (int c = 0; c <= cols; c++) {
            int size = c * colSize;
            g.drawLine(size, 0, size, getPreferredSize().height);
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
     * Gets if a point in the grid is OPEN
     *
     * @param tile Search tile
     * @return if tile is free
     */
    private boolean isOpenTile(Point tile) {
        int x = tile.x;
        int y = tile.y;
        // check if tile is inside the grid
        if (x < 0 || x >= rows || y < 0 || y >= cols) {
            return false;
        }
        // check if tile is open or the end tile(needs to be open so we can solve)
        return grid[x][y] == TileType.OPEN.value() || grid[x][y] == TileType.END.value();
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
        int x = (rawX / colSize) % cols;
        int y = (rawY / rowSize) % rows;
        return grid[y][x];
    }

    /**
     * Sets a grid point to a TileType
     *
     * @param tile grid point
     * @param type new type
     * @return if successful
     */
    public boolean setValueAt(Point tile, TileType type) {
        int rawX = (int) tile.getX();
        int rawY = (int) tile.getY();
        TileType old = TileType.to(getValueAt(rawX, rawY));

        if (type != old) {
            if (type.isExclusive() && this.findValue(type) != null) {
                return false; // can't set more than one exclusive tile
            }
            int x = (rawX / colSize) % cols;
            int y = (rawY / rowSize) % rows;
            grid[y][x] = type.value();
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

    /**
     * Sets rows of maze
     *
     * @param rows new # of rows
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * Sets columns of maze
     *
     * @param cols new # of columns
     */
    public void setCols(int cols) {
        this.cols = cols;
    }

    /**
     * Sets new maze grid
     *
     * @param grid new maze grid
     */
    public void setGrid(int[][] grid) {
        this.grid = grid;
    }
}
