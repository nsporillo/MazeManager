import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuListener implements ActionListener {

    private MazeSolver mazeSolver;

    public MenuListener(MazeSolver mazeSolver) {
        this.mazeSolver = mazeSolver;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals("New")) {
            // enter name, size
        } else if (action.equals("Load")) {
            // open dialog, list mazes, listen for choice
        } else if (action.equals("Save")) {
            // enter name, save in mazes directory
        } else if (action.equals("Start")) {
            mazeSolver.setTooltip(TileType.START);
        } else if (action.equals("Open")) {
            mazeSolver.setTooltip(TileType.OPEN);
        } else if (action.equals("Wall")) {
            mazeSolver.setTooltip(TileType.WALL);
        } else if (action.equals("Finish")) {
            mazeSolver.setTooltip(TileType.END);
        } else if (action.equals("Solve")) {
            if (mazeSolver.isSolved()) {
                mazeSolver.load();
            }
            if (mazeSolver.solve()) {
                System.out.println("Solved in " + mazeSolver.getSteps());
            } else {
                System.out.println("Could not solve");
            }
            mazeSolver.repaint();
        } else if (action.equals("Reset")) {
            mazeSolver.reset();
        }
    }
}
