import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class MazeFactory {
    private static String root = "mazes";

    static {
        File dir = new File(root);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public static Maze initialMaze(String name, int rows, int cols) {
        return new Maze(name, getEmptyGrid(rows, cols));
    }

    public static Maze load(String name) throws IOException {
        File file = new File(root, name + ".maze");
        Scanner scan = new Scanner(file);
        int rows = scan.nextInt();
        int cols = scan.nextInt();
        int[][] grid = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = scan.nextInt();
            }
        }
        return new Maze(name, grid);
    }

    public static void save(Maze maze) throws IOException {
        maze.reset(); // clear out tried/solved tiles
        PrintWriter pw = new PrintWriter(new File(root, maze.getName() + ".maze"));
        String one = maze.getRows() + " " + maze.getCols();
        pw.println(one);
        for (int i = 0; i < maze.getRows(); i++) {
            for (int j = 0; j < maze.getCols(); j++) {
                pw.print(maze.grid[i][j] + " ");
            }
            pw.println();
        }
        pw.close();

    }

    public static int[][] getEmptyGrid(int rows, int cols) {
        int[][] grid = new int[rows][cols];
        for(int[] row: grid) {
            Arrays.fill(row, TileType.OPEN.value());
        }
        return grid;
    }
}
