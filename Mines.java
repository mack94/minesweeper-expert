package mines;

import java.awt.*;
import javax.swing.*;
import java.awt.BorderLayout;

public class Mines extends JFrame {

    private final int WIDTH = 280;
    private final int HEIGHT = 320;

    private JLabel statusbar;
    private MenuBar menu = new MenuBar();
    
    private JLabel rowLabel;
    Board board;
    
    public Mines()
    {
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setPreferredSize(new Dimension(WIDTH, HEIGHT));
      setLocationRelativeTo(null);
      setTitle("Minesweeper");
      
      //Create the Menu bar
      setJMenuBar(menu);
      
      
      statusbar = new JLabel("");
      add(statusbar, BorderLayout.SOUTH);
      
      //Creates a new instance of board
      add(board = new Board(statusbar)); 
      rowLabel = new JLabel(Integer.toString(board.getRows()));
      add(rowLabel, BorderLayout.EAST);
      
      pack();
      setResizable(true);
      setVisible(true);
    }
    
    //Creates new instance on mines
    public static void main(String[] args)
    {
        new Mines();
    }
}
