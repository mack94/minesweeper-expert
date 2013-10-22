package mines;

import javax.swing.*;
import java.awt.event.*;

public class DifficultyListener implements ActionListener
{
  private Board gameBoard = new Board(new JLabel(""));
  private JRadioButtonMenuItem beginnerItem, intermediateItem, expertItem;
  
  public DifficultyListener(JRadioButtonMenuItem easy, JRadioButtonMenuItem medium, JRadioButtonMenuItem hard)
  {
    beginnerItem = easy;
    intermediateItem = medium;
    expertItem = hard;
  }
  
  @Override
  public void actionPerformed(ActionEvent e)
  {
    //Throws an exception when you try to change the difficulty
    if(beginnerItem.isSelected())
    {
//      gameBoard.setMines(20);
//      gameBoard.setRows(10);
//      gameBoard.setCols(10);
//      gameBoard.newGame();
      gameBoard.resizeBoard(20, 10, 10);
    }
    
    if(intermediateItem.isSelected())
    {
//      gameBoard.setMines(40);
//      gameBoard.setRows(16);
//      gameBoard.setCols(16);
//      gameBoard.newGame();
      gameBoard.resizeBoard(40, 16, 16);
    }
    
    if(expertItem.isSelected())
    {
//      gameBoard.setMines(60);
//      gameBoard.setRows(24);
//      gameBoard.setCols(24);
//      gameBoard.newGame();
      gameBoard.resizeBoard(60, 24, 24);
    }
  }
}