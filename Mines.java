package mines;

import java.awt.*;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Mines extends JFrame
{

 private final int WIDTH = 260;
 private final int HEIGHT = 320;

 private JLabel statusbar;

 private MenuBar menu = new MenuBar();

 public Mines()
 {
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
  setLocationRelativeTo(null); // centers the frame
  setResizable(true);
  setVisible(true);
 }

 //Creates new instance on mines
 public static void main(String[] args)
 {
  new Mines();
 }
}
