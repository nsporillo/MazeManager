
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
                int val = grid[x][y];
                if (val == 1) {
                    g.setColor(Color.BLUE); // wall
                } else if (val == 2) {
                    g.setColor(Color.PINK); // tried
                } else if (val == 3 || val == 4) {
                    g.setColor(Color.BLACK); // start or end
                } else if (val == 5) {
                    g.setColor(Color.GREEN); // path
                } else {
                    g.setColor(Color.WHITE); // open
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
            grid[tile.x][tile.y] = 4;
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
        if (tile.x < 0 || tile.x >= rows || tile.y < 0 || tile.y >= cols) {
            return false;
        }
        return grid[tile.x][tile.y] == 0 || grid[tile.x][tile.y] == 4;
    }

    private void exit(Point tile) {
        grid[tile.x][tile.y] = 0;
        steps--;
    }

    private void enter(Point tile) {
        grid[tile.x][tile.y] = 5;
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
