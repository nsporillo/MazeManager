
import java.awt.*;
import java.io.File;
import java.util.Scanner;


public class Maze {

    private Graphics2D g2d;
    private MazeFrame mazeFrame;
    private int rows = 0; // vertical
    private int cols = 0; // horizontal
    private int[][] grid;

    public Maze(MazeFrame frame, File mazeFile) {
        this.mazeFrame = frame;
        this.g2d = (Graphics2D) frame.getMazePanel().getGraphics();
    }

    // as x increases, move right
    // as y increases, move down
    private void paintLines() {
        mazeFrame.resize(rows * 10, cols * 10);
        g2d.setColor(Color.BLACK);
        for (int r = 1; r < rows; r++) {
            g2d.drawLine(r * 10, 0, r * 10, rows * 10);
        }
        for (int c = 0; c < cols; c++) {
            g2d.drawLine(0, c * 10, cols * 10, c * 10);
        }
    }

    private void parseMazeFile(File mazeFile) {
        try {
            Scanner scan = new Scanner(mazeFile);
            rows = scan.nextInt();
            cols = scan.nextInt();
            grid = new int[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    setValue(i, j, scan.nextInt());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setValue(int row, int col, int val) {
        grid[row][col] = val;
    }
}
