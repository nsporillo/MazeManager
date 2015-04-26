import javax.swing.*;
import java.awt.*;
import java.io.File;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class MazeFrame {

    private JFrame mazeFrame;
    private Maze maze;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                MazeFrame window = new MazeFrame();
            } catch (Exception e) {
                e.printStackTrace();
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
        mazeFrame.setTitle("MazeSolver");
        mazeFrame.setResizable(false);
        mazeFrame.setBounds(100, 100, 600, 500);
        mazeFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mazeFrame.getContentPane().setLayout(new FlowLayout());
        mazeFrame.setVisible(true);
        mazeFrame.add(new Maze(this, new File("test.maze")));

        JMenuBar menuBar = new JMenuBar();
        mazeFrame.setJMenuBar(menuBar);

        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);

        JMenuItem mnNew = new JMenuItem("New");
        mnFile.add(mnNew);

        JMenu mnRun = new JMenu("Run");
        menuBar.add(mnRun);
    }
}
