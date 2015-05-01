
public class MazeEditor extends Maze {

    public MazeEditor(String filename, int rows, int cols) {
        super(filename);
        super.rows = rows;
        super.cols = cols;
        super.grid = new int[rows][cols];
    }

    @Override
    public void load() {

    }
}
