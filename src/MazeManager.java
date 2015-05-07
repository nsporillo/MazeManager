import javax.swing.*;
import java.awt.*;

public class MazeManager {

    private Maze maze;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    MazeManager window = new MazeManager();
                    window.initialize();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        JFrame mazeFrame = new JFrame();
        mazeFrame.setTitle("MazeManager");
        mazeFrame.setResizable(false);
        mazeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mazeFrame.getContentPane().setLayout(new CardLayout());
        maze = MazeFactory.initialMaze("test", 6, 6); // load an empty maze
        mazeFrame.getContentPane().add(maze);
        MenuListener ml = new MenuListener(this);

        JMenuBar menuBar = new JMenuBar();
        mazeFrame.setJMenuBar(menuBar);

        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);

        JMenuItem mnNew = new JMenuItem("New");

        mnNew.addActionListener(ml);
        mnFile.add(mnNew);

        JMenuItem mnLoad = new JMenuItem("Load");
        mnLoad.addActionListener(ml);
        mnFile.add(mnLoad);

        JMenuItem mnSave = new JMenuItem("Save");
        mnSave.addActionListener(ml);
        mnFile.add(mnSave);

        JMenu mnTile = new JMenu("Tile");
        menuBar.add(mnTile);

        JMenuItem start = new JMenuItem("Start");
        start.setIcon(new ImageIcon(TileType.START.toIcon(16, 16)));
        start.addActionListener(ml);
        mnTile.add(start);

        JMenuItem open = new JMenuItem("Open");
        open.setIcon(new ImageIcon(TileType.OPEN.toIcon(16, 16)));
        open.addActionListener(ml);
        mnTile.add(open);

        JMenuItem wall = new JMenuItem("Wall");
        wall.setIcon(new ImageIcon(TileType.WALL.toIcon(16, 16)));
        wall.addActionListener(ml);
        mnTile.add(wall);

        JMenuItem finish = new JMenuItem("Finish");
        finish.setIcon(new ImageIcon(TileType.END.toIcon(16, 16)));
        finish.addActionListener(ml);
        mnTile.add(finish);


        JMenu mnRun = new JMenu("Run");
        menuBar.add(mnRun);

        JMenuItem mnSolve = new JMenuItem("Solve");
        mnRun.add(mnSolve);
        mnSolve.addActionListener(ml);

        JMenuItem mnReset = new JMenuItem("Reset");
        mnRun.add(mnReset);
        mnReset.addActionListener(ml);

        MouseHandler handler = new MouseHandler(this);
        maze.addMouseListener(handler); // handle mouse clicks
        maze.addMouseMotionListener(handler); // handle mouse drags

        mazeFrame.pack(); // adjust frame size to size of sub components
        mazeFrame.setLocationRelativeTo(null); // centers frame
        mazeFrame.setVisible(true);
    }

    public Maze getMaze() {
        return this.maze;
    }
}