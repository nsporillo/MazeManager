import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MazeFrame {

    private JFrame mazeFrame;
    private MazeSolver mazeSolver;

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
        mazeFrame.setTitle("Mazes");
        mazeFrame.setResizable(false);
        mazeFrame.setBounds(100, 100, 600, 500);
        mazeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mazeFrame.getContentPane().setLayout(new FlowLayout());
        mazeFrame.setVisible(true);
        mazeSolver = new MazeSolver("hard.maze");
        mazeFrame.add(mazeSolver);
        MenuListener ml = new MenuListener(mazeSolver);

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

        JMenu mnEdit = new JMenu("Edit");
        menuBar.add(mnEdit);

        JMenuItem start = new JMenuItem("Start");
        mnEdit.add(start);
        start.addActionListener(ml);

        JMenuItem open = new JMenuItem("Open");
        mnEdit.add(open);
        open.addActionListener(ml);

        JMenuItem wall = new JMenuItem("Wall");
        mnEdit.add(wall);
        wall.addActionListener(ml);

        JMenuItem finish = new JMenuItem("Finish");
        mnEdit.add(finish);
        finish.addActionListener(ml);

        JMenu mnRun = new JMenu("Run");
        menuBar.add(mnRun);

        JMenuItem mnSolve = new JMenuItem("Solve");
        mnRun.add(mnSolve);
        mnSolve.addActionListener(ml);

        JMenuItem mnReset = new JMenuItem("Reset");
        mnRun.add(mnReset);
        mnReset.addActionListener(ml);

        mazeSolver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("Clicked: " + e.getX() + ", " + e.getY());
                int x = (e.getX() / mazeSolver.getColSize()) % mazeSolver.getCols();
                int y = (e.getY() / mazeSolver.getRowSize()) % mazeSolver.getRows();
                System.out.println("Row: " + y + ", Col: " + x);
                if (mazeSolver.setValueAt(e.getX(), e.getY(), mazeSolver.getTooltip())) {
                    System.out.println("Set value to " + mazeSolver.getTooltip().name());
                } else {
                    System.out.println("Didnt set value");
                }
            }
        });
    }
}
