import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter {

    private MazeFrame frame;

    public MouseHandler(MazeFrame frame) {
        this.frame = frame;
    }

    private boolean held = false;

    @Override
    public void mousePressed(MouseEvent e) {
        this.held = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.held = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        handleSet(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Maze maze = frame.getMaze();
        if (SwingUtilities.isRightMouseButton(e)) {
            maze.setValueAt(e.getPoint(), TileType.OPEN);
        } else {
            maze.setValueAt(e.getPoint(), maze.getTooltip());
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        handleSet(e);
    }

    private void handleSet(MouseEvent e) {
        if (held) {
            Maze maze = frame.getMaze();
            if (SwingUtilities.isRightMouseButton(e)) {
                maze.setValueAt(e.getPoint(), TileType.OPEN);
            } else {
                maze.setValueAt(e.getPoint(), maze.getTooltip());
            }
        }
    }
}
