
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

    private void paintLines() {
        mazeFrame.resize(rows * 10, cols * 10);
        for (int r = 0; r < rows; r++) {
            //x1, y1 -> x2, y2

        }
        for (int c = 0; c < cols; c++) {

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
                    grid[i][j] = scan.nextInt();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
