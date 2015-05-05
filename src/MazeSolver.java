import java.awt.*;

public class MazeSolver extends Maze {

    private int steps;
    private boolean solved = false;

    public MazeSolver(String mazeName) {
        super(mazeName);
        super.setName("MazeSolver");
        super.load();
        this.solved = false;
        super.start = this.findValue(3);
        super.finish = this.findValue(4);
        super.setVisible(true);
    }

    private Point[] findAdjacent(Point tile) {
        Point[] adjacent = new Point[4];
        adjacent[0] = new Point(tile.x + 1, tile.y); // right
        adjacent[1] = new Point(tile.x - 1, tile.y); // left
        adjacent[2] = new Point(tile.x, tile.y - 1); // up
        adjacent[3] = new Point(tile.x, tile.y + 1); // down
        return adjacent;
    }

    public boolean solve() {
        this.steps = 0;
        this.solved = traverse(this.start);
        return solved;
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

    private Point findValue(int value) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == value) {
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
        steps--;
    }

    private void enter(Point tile) {
        grid[tile.x][tile.y] = TileType.SOLVED.value();
        steps++;
    }

    public int getValueAt(int rawX, int rawY) {
        int x = (rawX / colSize) % cols;
        int y = (rawY / rowSize) % rows;
        return grid[y][x];
    }

    public boolean setValueAt(int rawX, int rawY, TileType type) {
        int old = getValueAt(rawX, rawY);

        if (type.value() != old) {
            if(type.isExclusive() && this.findValue(type.value()) != null) {
                return false; // can't set more than one exclusive tile
            }
            int x = (rawX / colSize) % cols;
            int y = (rawY / rowSize) % rows;
            grid[y][x] = type.value();
            super.repaint();
            return true;
        }
        System.out.println("Type: " + type.name() + "-> old:" + old);
        return false;
    }

    public int getSteps() {
        return this.steps;
    }

    private boolean isFinished(Point current) {
        return current.equals(super.finish);
    }

    public boolean isSolved() {
        return solved;
    }
}
