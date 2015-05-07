import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class MazeFactory {
    private static String root = "mazes"; // mazes directory



    /**
     * Generates an initial, empty maze
     *
     * @param name maze name
     * @param rows number of rows
     * @param cols number of columns
     * @return {@link Maze} object
     */
    public static Maze initialMaze(String name, int rows, int cols) {
        return new Maze(name, getEmptyGrid(rows, cols));
    }

    /**
     * Loads maze from file
     *
     * @param name name of file to load
     * @return {@link Maze} object
     * @throws IOException
     */
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

    /**
     * Saves maze to file
     *
     * @param maze Maze object to save
     * @throws IOException
     */
    public static void save(Maze maze) throws IOException {
        maze.reset(); // clear out tried/solved tiles
        PrintWriter pw = new PrintWriter(new File(root, maze.getName() + ".maze"));
        String one = maze.getRows() + " " + maze.getCols();
        pw.println(one); // print row and column to file
        for (int i = 0; i < maze.getRows(); i++) {
            for (int j = 0; j < maze.getCols(); j++) {
                pw.print(maze.grid[i][j] + " ");
            }
            pw.println(); // new line for next row
        }
        pw.flush(); // flush buffer
        pw.close(); // close resource
    }

    /**
     * Generates a maze grid with all open tiles
     *
     * @param rows number of rows
     * @param cols number of columns
     * @return 3d int array filled with open tiles
     */
    public static int[][] getEmptyGrid(int rows, int cols) {
        int[][] grid = new int[rows][cols];
        for (int[] row : grid) {
            // fill each row with open tiles
            Arrays.fill(row, TileType.OPEN.value());
        }
        return grid;
    }
}
