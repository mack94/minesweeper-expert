package mines;

import java.awt.*;
import javax.swing.*;

public class MineFrame
{
  private JFrame frame;
  private JPanel gamePanel;
  private MenuBar menu;
  private JLabel statusbar;
  private Board mineBoard;
  
  public MineFrame()
  {
    frame = new JFrame();//Create the frame for the GUI
    
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Have the applcation exit when closed
    frame.setPreferredSize(new Dimension(260, 320));//Set the preferred frame size
    frame.setLocationRelativeTo(null);//Centre the frame
    frame.setTitle("Minesweeper");//Title of the frame
    
    statusbar = new JLabel("");//Set the passed in status bar
    
    gamePanel = new JPanel(new BorderLayout());//New panel that contains the board
    menu = new MenuBar();//Set menu to be a new MenuBar
    frame.setJMenuBar(menu);//Set the MenuBar as the JMenuBar
    frame.add(gamePanel);//Add gamePanel to the frame
    startNewGame();
    
    frame.pack();//Resize the frame to occupy the smallest amount of space
    frame.setLocationRelativeTo(null); //Centres the frame
    frame.setResizable(true);//Have the frame re-sizable (useful for troubleshooting)
    frame.setVisible(true);//Show all components on the window
  }
  
  public void startNewGame()
  {
    gamePanel.removeAll();
    gamePanel.add(statusbar, BorderLayout.SOUTH);
    mineBoard = new Board(statusbar);
    gamePanel.add(mineBoard);
    
    frame.validate();
    frame.repaint();
    frame.pack();
  }
}