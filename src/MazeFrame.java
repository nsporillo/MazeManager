import javax.swing.*;
import java.awt.*;

public class MazeFrame {

    private JFrame frmMaze;
    private Maze currentMaze;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    MazeFrame window = new MazeFrame();
                    window.frmMaze.setVisible(true);
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
        frmMaze = new JFrame();
        frmMaze.setTitle("MazeFrame");
        frmMaze.setResizable(false);
        frmMaze.setBounds(100, 100, 600, 367);
        frmMaze.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmMaze.getContentPane().setLayout(null);

        frmMaze.add()
        JMenuBar menuBar = new JMenuBar();
        frmMaze.setJMenuBar(menuBar);

        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);

        JMenuItem mnNew = new JMenuItem("New");
        mnFile.add(mnNew);

        JMenu mnRun = new JMenu("Run");
        menuBar.add(mnRun);
    }
}
