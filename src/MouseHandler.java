import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Author: Nick Porillo
 * Date: 5/7/15
 * Name: MouseHandler
 * Description: MouseAdapter which will set tile values
 */
public class MouseHandler extends MouseAdapter {

    private MazeManager frame;
    private boolean held = false;

    /**
     * Constructor for mouse handler
     *
     * @param frame main class
     */
    public MouseHandler(MazeManager frame) {
        this.frame = frame;
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e event
     */
    @Override
    public void mousePressed(MouseEvent e) {
        this.held = true;
    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e event
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        this.held = false;
    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e event
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        handleSet(e, true);
    }

    /**
     * Invoked when a mouse button is clicked on a component.
     *
     * @param e event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        handleSet(e, false);
    }

    /**
     * Handles TileType setting
     *
     * @param e    mouse event
     * @param drag if mouse was dragged
     */
    private void handleSet(MouseEvent e, boolean drag) {
        if (held || !drag) {
            Maze maze = frame.getMaze();
            if (SwingUtilities.isRightMouseButton(e)) {
                // right click will clear any tile
                maze.setValueAt(e.getPoint(), TileType.OPEN);
            } else {
                // left click will place the value at mouse with our tooltip
                maze.setValueAt(e.getPoint(), maze.getTooltip());
            }
        }
    }
}
