import javax.swing.*;
import java.awt.*;

public class Maze extends JPanel {

    private TileType tooltip;
    private Point start, finish;

    private String name;
    private int rows, cols, rowSize, colSize;
    protected boolean solved = false, paint = false;

    protected int[][] grid;

    public Maze(String name, int[][] grid) {
        this.name = name;
        this.rows = grid.length;
        this.cols = grid[0].length;
        this.grid = grid;
        this.load();
    }

    public void change(Maze other) {
        this.name = other.name;
        this.rows = other.rows;
        this.cols = other.cols;
        this.grid = other.grid;
        this.load();
    }

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

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(550, 450);
    }

    @Override
    public void paintComponent(Graphics g) {
        if (!paint) {
            return; // dont update
        }
        super.paintComponent(g);
        for (int y = 0; y < cols; y++) {
            for (int x = 0; x < rows; x++) {
                TileType type = TileType.to(grid[x][y]);
                g.setColor(type.color());
                g.fillRect(y * colSize, x * rowSize, colSize, rowSize);
            }
        }

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

    private Point[] findAdjacent(Point tile) {
        Point[] adjacent = new Point[4];
        adjacent[0] = new Point(tile.x + 1, tile.y); // right
        adjacent[1] = new Point(tile.x - 1, tile.y); // left
        adjacent[2] = new Point(tile.x, tile.y - 1); // up
        adjacent[3] = new Point(tile.x, tile.y + 1); // down
        return adjacent;
    }

    private boolean traverse(Point tile) {
        if (isFinished(tile)) {
            grid[tile.x][tile.y] = TileType.END.value();
            return true;
        }
        Point[] adjacent = findAdjacent(tile);
        for (Point adja : adjacent) {
            if (isFreeTile(adja)) {
                enter(adja);
                if (traverse(adja)) {
                    return true;
                }
                exit(adja);
            }
        }
        return false;
    }

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

    private boolean isFreeTile(Point tile) {
        int x = tile.x;
        int y = tile.y;
        // check if tile is inside the grid
        if (x < 0 || x >= rows || y < 0 || y >= cols) {
            return false;
        }
        // check if tile is open or the end tile
        return grid[x][y] == TileType.OPEN.value() || grid[x][y] == TileType.END.value();
    }

    private void exit(Point tile) {
        grid[tile.x][tile.y] = TileType.TRIED.value();
    }

    private void enter(Point tile) {
        grid[tile.x][tile.y] = TileType.SOLVED.value();
    }

    private boolean isFinished(Point current) {
        return current.equals(this.finish);
    }

    public int getValueAt(int rawX, int rawY) {
        int x = (rawX / colSize) % cols;
        int y = (rawY / rowSize) % rows;
        return grid[y][x];
    }

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
                this.start = this.findValue(TileType.START);
                this.finish = this.findValue(TileType.END);
            }
            super.repaint();
            return true;
        }
        return false;
    }

    public boolean solve() {
        if (start == null || finish == null) {
            return false;
        }
        this.solved = traverse(this.start);
        return solved;
    }

    public boolean isSolved() {
        return solved;
    }

    public TileType getTooltip() {
        return tooltip;
    }

    public void setTooltip(TileType tooltip) {
        this.tooltip = tooltip;
    }

    public String getName() {
        return name;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }
}
