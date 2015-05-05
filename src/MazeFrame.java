import javax.swing.*;
import java.awt.*;

public class MazeFrame {

    private JFrame mazeFrame;
    private Maze maze;
    private MouseHandler handler = new MouseHandler(this);

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    MazeFrame window = new MazeFrame();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public MazeFrame() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        mazeFrame = new JFrame();
        mazeFrame.setTitle("MazeManager");
        mazeFrame.setResizable(false);
        mazeFrame.setSize(508, 458);
        mazeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mazeFrame.getContentPane().setLayout(new CardLayout());
        mazeFrame.setVisible(true);
        maze = MazeFactory.initialMaze("test", 5, 5);
        mazeFrame.getContentPane().add(maze);
        MenuListener ml = new MenuListener(this);

        JMenuBar menuBar = new JMenuBar();
        mazeFrame.setJMenuBar(menuBar);

        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);

        JMenuItem mnNew = new JMenuItem("New");
        mnFile.add(mnNew);
        mnNew.addActionListener(ml);

        JMenuItem mnLoad = new JMenuItem("Load");
        mnFile.add(mnLoad);
        mnLoad.addActionListener(ml);

        JMenuItem mnSave = new JMenuItem("Save");
        mnFile.add(mnSave);
        mnSave.addActionListener(ml);

        JMenu mnTile = new JMenu("Tile");
        menuBar.add(mnTile);

        JMenuItem start = new JMenuItem("Start");
        mnTile.add(start);
        start.addActionListener(ml);

        JMenuItem open = new JMenuItem("Open");
        mnTile.add(open);
        open.addActionListener(ml);

        JMenuItem wall = new JMenuItem("Wall");
        mnTile.add(wall);
        wall.addActionListener(ml);

        JMenuItem finish = new JMenuItem("Finish");
        mnTile.add(finish);
        finish.addActionListener(ml);

        JMenu mnRun = new JMenu("Run");
        menuBar.add(mnRun);

        JMenuItem mnSolve = new JMenuItem("Solve");
        mnRun.add(mnSolve);
        mnSolve.addActionListener(ml);

        JMenuItem mnReset = new JMenuItem("Reset");
        mnRun.add(mnReset);
        mnReset.addActionListener(ml);

        maze.addMouseListener(handler);
        maze.addMouseMotionListener(handler);
    }

    public Maze getMaze() {
        return this.maze;
    }
}
