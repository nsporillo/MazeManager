import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Abstract Maze
 * Can be the object for a maze editor or maze solver panel
 */
public abstract class Maze extends JPanel {

    private File file; // file associated with this maze
    protected Point start, finish; // start and end points of maze
    protected int[][] grid; // coordinate system
    protected int rows, cols; // # of rows and # of colums

    public Maze(String mazeName) {
        file = new File("mazes/" + mazeName); // use maze directory
    }

    public abstract void load(); // load maze from file

    public File getFile() {
        return this.file;
    }
}
