package mines;

import java.awt.BorderLayout;

import javax.swing.*;
import java.awt.event.*;

public class Mines extends JFrame {

    private final int WIDTH = 250;
    private final int HEIGHT = 300;

    private JLabel statusbar;
    private JMenuBar menuBar;
    private JMenu file, view, help;
    private JMenuItem save, load, exit, newGame, beginner, intermediate, expert, custom, pause, resolve;

    public Mines() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setTitle("Minesweeper");

        menuBar = new JMenuBar();

        file = new JMenu("File");
        save = new JMenuItem("Save");
        load = new JMenuItem("Load");
        exit = new JMenuItem("Exit");
        file.add(save);
        file.add(load);
        file.add(exit);

        view = new JMenu("Game");
        newGame = new JMenuItem("New Game");
        beginner = new JMenuItem("Beginner");
        intermediate = new JMenuItem("Intermediate");
        expert = new JMenuItem("Expert");
        custom = new JMenuItem("Custom...");
        pause = new JMenuItem("Pause");
        resolve = new JMenuItem("Resolve");
        view.add(newGame);
        view.add(beginner);
        view.add(intermediate);
        view.add(expert);
        view.add(custom);
        view.add(pause);
        view.add(resolve);
        
        help = new JMenu("Help");

        menuBar.add(file);
        menuBar.add(view);
        menuBar.add(help);

        statusbar = new JLabel("");
        add(statusbar, BorderLayout.SOUTH);
        add(menuBar, BorderLayout.NORTH);

        // creates new insence of board
        add(new Board(statusbar)); 

        setResizable(false);
        setVisible(true);
    }
    
    // creates new instance on mines
    public static void main(String[] args) {
        new Mines();
    }
}