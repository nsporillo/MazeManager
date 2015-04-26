
import jsjf.ArrayUnorderedList;

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
    private ArrayUnorderedList<Tile> tiles = new ArrayUnorderedList<Tile>();
    private int[][] grid;

    public Maze(MazeFrame frame, File mazeFile) {
        this.mazeFrame = frame;
        this.parseMazeFile(mazeFile);
        this.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Tile tile : tiles) {
            g.setColor(tile.getColor());
            g.fillRect(tile.getY() * colSize, tile.getX() * rowSize, colSize, rowSize);
        }

        g.setColor(Color.black);
        for (Tile tile : tiles) {
            int xSize = tile.getX() * rowSize;
            int ySize = tile.getY() * colSize;
            g.drawLine(0, xSize, width + 3 - colSize, xSize);
            g.drawLine(ySize, 0, ySize, height);
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
                    int val = scan.nextInt();
                    Tile tile = new Tile(i, j);
                    tile.setValue(val);
                    tiles.addToRear(tile);
                    setValue(i, j, val);
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
