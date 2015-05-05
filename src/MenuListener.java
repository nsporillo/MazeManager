import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import static javax.swing.JOptionPane.*;

public class MenuListener implements ActionListener {

    private MazeFrame frame;

    public MenuListener(MazeFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals("New")) {
            String title = "New maze";
            String name = showInputDialog(null, "Enter maze name", title, QUESTION_MESSAGE);
            int rows = Integer.parseInt(showInputDialog(null, "Enter number of rows", title, QUESTION_MESSAGE));
            if (rows < 2) {
                showMessageDialog(null, "A maze must have at least 2 rows", "Error", ERROR_MESSAGE);
                return;
            }
            int cols = Integer.parseInt(showInputDialog(null, "Enter number of columns", title, QUESTION_MESSAGE));
            if (cols < 2) {
                showMessageDialog(null, "A maze must have at least 2 columns", "Error", ERROR_MESSAGE);
                return;
            }
            frame.getMaze().setName(name);
            frame.getMaze().setGrid(MazeFactory.getEmptyGrid(rows, cols));
            frame.getMaze().setRows(rows);
            frame.getMaze().setCols(cols);
            frame.getMaze().load();
            frame.getMaze().repaint();
        } else if (action.equals("Load")) {
            // open dialog, list mazes, listen for choice

            File[] files = new File("mazes").listFiles();
            if (files == null) {
                System.out.println("Could not find files in mazes directory!");
                return;
            }
            Object[] choices = new Object[files.length];
            for (int i = 0; i < files.length; i++) {
                String name = files[i].getName().substring(0, files[i].getName().lastIndexOf("."));
                choices[i] = name;
            }
            Object choice = showInputDialog(null, "Select maze to load", "Load maze", QUESTION_MESSAGE, null, choices, choices[0]);
            String selection = (String) choice;
            try {
                Maze newMaze = MazeFactory.load(selection);
                frame.getMaze().change(newMaze);
                frame.getMaze().repaint();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        } else if (action.equals("Save")) {
            try {
                MazeFactory.save(frame.getMaze());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if (action.equals("Start")) {
            frame.getMaze().setTooltip(TileType.START);
        } else if (action.equals("Open")) {
            frame.getMaze().setTooltip(TileType.OPEN);
        } else if (action.equals("Wall")) {
            frame.getMaze().setTooltip(TileType.WALL);
        } else if (action.equals("Finish")) {
            frame.getMaze().setTooltip(TileType.END);
        } else if (action.equals("Solve")) {
            if (frame.getMaze().isSolved()) {
                frame.getMaze().reset(); // if we're solved already, reset first
            }
            if (!frame.getMaze().solve()) {
                showMessageDialog(null, "Could not solve maze!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            frame.getMaze().repaint();
        } else if (action.equals("Reset")) {
            frame.getMaze().reset();
        }
    }
}
