import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuListener implements ActionListener {

    private Maze maze;

    public MenuListener(Maze maze) {
        this.maze = maze;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Solve")) {
            if (maze.isSolved()) {
                maze.load();
            }
            if (maze.solve()) {
                System.out.println("Solved in " + maze.getSteps());
            } else {
                System.out.println("Could not solve");
            }
            maze.repaint();
        }
    }
}
