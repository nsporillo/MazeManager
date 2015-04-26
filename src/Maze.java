
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Scanner;


public class Maze extends JPanel {

    private MazeFrame mazeFrame;

    private final int width = 500;
    private final int height = 400;

    private int rows = 0; // vertical
    private int cols = 0; // horizontal

    private int rowSize = 0;
    private int colSize = 0;

    private int[][] grid;

    public Maze(MazeFrame frame, File mazeFile) {
        this.mazeFrame = frame;
        this.parseMazeFile(mazeFile);
        this.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int y = 0; y < cols; y++) {
            for (int x = 0; x < rows; x++) {
                int val = grid[x][y];
                if (val == 1) {
                    g.setColor(Color.blue);
                } else if(val == 2) {
                    g.setColor(Color.pink);
                } else if(val == 3) {
                    g.setColor(Color.lightGray);
                } else {
                    g.setColor(Color.white);
                }
                g.fillRect(y * colSize, x * rowSize, colSize, rowSize);
            }
        }

        g.setColor(Color.black);
        for (int r = 0; r <= rows; r++) {
            int size = r * rowSize;
            g.drawLine(0, size, width + 3 - colSize, size);
        }
        for (int c = 0; c <= cols; c++) {
            int size = c * colSize;
            g.drawLine(size, 0, size, height);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    private void parseMazeFile(File mazeFile) {
        try {
            Scanner scan = new Scanner(mazeFile);
            rows = scan.nextInt();
            cols = scan.nextInt();
            colSize = (int) Math.floor((double) width / (double) cols);
            rowSize = (int) Math.floor((double) height / (double) rows);
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
