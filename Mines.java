package mines;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;

//import java.awt.BorderLayout;

public class Mines extends JFrame {

    private final int WIDTH = 250;
    private final int HEIGHT = 300;

    private JLabel statusbar;

    private MenuBar menu = new MenuBar();
    
    public Mines()
    {
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //DISPOSE_ON_CLOSE which one should we use?
      setPreferredSize(new Dimension(WIDTH, HEIGHT));
      setLocationRelativeTo(null);
      setTitle("Minesweeper");
      
      statusbar = new JLabel("");
      add(statusbar, BorderLayout.SOUTH);
      
      //Create the Menu bar
      setJMenuBar(menu);
      
      //Creates new instance of board
      add(new Board(statusbar)); 
      
      pack();
      setResizable(false);
      setVisible(true);
    }
    
    //Creates new instance on mines
    public static void main(String[] args)
    {

        new Mines();
    }
}