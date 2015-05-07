import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import static javax.swing.JOptionPane.*;

public class MenuListener implements ActionListener {

    private MazeManager frame;

    public MenuListener(MazeManager frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals("New")) {
            String title = "New maze"; // base dialog title

            // open maze name dialog
            String name = showInputDialog(frame.getMaze(), "Enter maze name", title, QUESTION_MESSAGE);
            if (name == null) return; // dialog was closed, move along

            title += " - " + name; // append maze name to dialog title

            // open row dialog
            String strRows = showInputDialog(frame.getMaze(), "Enter number of rows", title, QUESTION_MESSAGE);
            if (strRows == null) return; // dialog was closed, move along

            int rows = Integer.parseInt(strRows);
            if (rows < 2) {
                showMessageDialog(frame.getMaze(), "A maze must have at least 2 rows", "Error", ERROR_MESSAGE);
                return;
            }

            // open column dialog
            String strCols = showInputDialog(frame.getMaze(), "Enter number of columns", title, QUESTION_MESSAGE);
            if (strCols == null) return; // dialog was closed, move along

            int cols = Integer.parseInt(strCols);
            if (cols < 2) {
                showMessageDialog(frame.getMaze(), "A maze must have at least 2 columns", "Error", ERROR_MESSAGE);
                return;
            }

            // we reuse the maze object
            frame.getMaze().setName(name); // update name
            frame.getMaze().setGrid(MazeFactory.getEmptyGrid(rows, cols)); // update grid
            frame.getMaze().setRows(rows); // update rows
            frame.getMaze().setCols(cols); // update columns
            frame.getMaze().load();        // loads rest of maze
            frame.getMaze().repaint();     // repaint panel
        } else if (action.equals("Load")) {
            // open dialog, list mazes, then load

            File root = new File("mazes");
            File[] files = root.listFiles();
            if (files == null || files.length == 0) {
                String path = root.getAbsolutePath();
                showMessageDialog(frame.getMaze(), "Could not find files in mazes directory\n" + path, "Error", ERROR_MESSAGE);
                return;
            }
            Object[] choices = new Object[files.length];
            for (int i = 0; i < files.length; i++) {
                String name = files[i].getName().substring(0, files[i].getName().lastIndexOf("."));
                choices[i] = name;
            }
            Object choice = showInputDialog(null, "Select maze to load", "Load maze", QUESTION_MESSAGE, null, choices, choices[0]);
            String selection = (String) choice;
            if(selection == null) return; // dialog was closed, move along
            try {
                Maze newMaze = MazeFactory.load(selection); // load selection
                frame.getMaze().change(newMaze); // update maze
                frame.getMaze().repaint(); // repaint panel
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
                frame.getMaze().reset(); // if we're solved already, reset
            }
            if (!frame.getMaze().solve()) {
                showMessageDialog(frame.getMaze(), "Could not solve maze!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            frame.getMaze().repaint(); // repaint panel
        } else if (action.equals("Reset")) {
            frame.getMaze().reset();
        }
    }
}
