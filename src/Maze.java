import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public abstract class Maze extends JPanel {

    private File file;             // file associated with this maze
    private String name;
    protected Point start, finish; // start and end points of maze
    protected int[][] grid;        // coordinate system
    protected int rows, cols, rowSize, colSize; // # of rows and # of columns
    protected TileType tooltip;

    protected boolean paint = false;

    public Maze(String mazeName) {
        this.name = mazeName;
        this.file = new File("mazes/" + name); // use maze directory
        this.tooltip = TileType.START; // default to start position
    }

    public void load() {
        try {
            Scanner scan = new Scanner(this.file);
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
            this.paint = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 400);
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

    public TileType getTooltip() {
        return this.tooltip;
    }

    public void setTooltip(TileType tooltip) {
        this.tooltip = tooltip;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "Maze{grid=" + Arrays.toString(grid) + ", rows=" + rows + ", cols=" + cols + ", rowSize=" + rowSize +
                ", colSize=" + colSize + ", tooltip=" + tooltip + ", paint=" + paint + '}';
    }
}
