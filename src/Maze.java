
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Random;
import java.util.Scanner;

public class Maze extends JPanel {

    private File file;
    private Random random = new Random();
    private Point start;
    private Point end;
    private int[][] grid;

    private int rows, cols, rowSize, colSize, steps;
    private boolean solved = false;

    public Maze(File mazeFile) {
        this.file = mazeFile;
        this.load();
        this.setVisible(true);
        this.start = this.findValue(3);
        this.end = this.findValue(4);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int y = 0; y < cols; y++) {
            for (int x = 0; x < rows; x++) {
                TileType type = TileType.to(grid[x][y]);
                switch (type) {
                    case OPEN:
                        g.setColor(Color.WHITE);
                        break;
                    case WALL:
                        g.setColor(Color.BLUE);
                        break;
                    case TRIED:
                        g.setColor(Color.PINK);
                        break;
                    case START:
                        g.setColor(Color.BLACK);
                        break;
                    case END:
                        g.setColor(Color.BLACK);
                        break;
                    case SOLVED:
                        g.setColor(Color.GREEN);
                        break;
                }
                g.fillRect(y * colSize, x * rowSize, colSize, rowSize);
            }
        }

        g.setColor(Color.black);
        for (int r = 0; r <= rows; r++) {
            int size = r * rowSize;
            g.drawLine(0, size, getPreferredSize().width + 3 - colSize, size);
        }
        for (int c = 0; c <= cols; c++) {
            int size = c * colSize;
            g.drawLine(size, 0, size, getPreferredSize().height);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 400);
    }

    public void load() {
        try {
            Scanner scan = new Scanner(file);
            rows = scan.nextInt();
            cols = scan.nextInt();
            colSize = (int) Math.floor((double) getPreferredSize().width / (double) cols);
            rowSize = (int) Math.floor((double) getPreferredSize().height / (double) rows);
            grid = new int[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    grid[i][j] = scan.nextInt();
                }
            }
            solved = false;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Point[] findAdjacent(Point tile) {
        Point[] adjacent = new Point[4];
        adjacent[0] = new Point(tile.x + 1, tile.y);
        adjacent[1] = new Point(tile.x, tile.y + 1);
        adjacent[2] = new Point(tile.x - 1, tile.y);
        adjacent[3] = new Point(tile.x, tile.y - 1);
        shuffle(adjacent); // variety in direction
        return adjacent;
    }

    private void shuffle(Point[] array) {
        Point temp;
        int index;
        for (int i = array.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    public boolean solve() {
        steps = 0;
        solved = traverse(this.start);
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
        grid[tile.x][tile.y] = TileType.OPEN.value();
        steps--;
    }

    private void enter(Point tile) {
        grid[tile.x][tile.y] = TileType.SOLVED.value();
        steps++;
    }

    public int getSteps() {
        return this.steps;
    }

    private boolean isFinished(Point current) {
        return current.equals(this.end);
    }

    public boolean isSolved() {
        return solved;
    }
}
