package mines;

import javax.swing.*;
import java.awt.event.*;

public class DifficultyListener implements ActionListener
{
  private JRadioButtonMenuItem beginnerItem, intermediateItem, expertItem;
  private Board gameBoard = new Board(new JLabel(""));
  
  public DifficultyListener(JRadioButtonMenuItem easy, JRadioButtonMenuItem medium, JRadioButtonMenuItem hard)
  {
    beginnerItem = easy;
    intermediateItem = medium;
    expertItem = hard;
  }
  
  @Override
  public void actionPerformed(ActionEvent e)
  {
    if(beginnerItem.isSelected())
    {
      gameBoard.setMines(20);
      gameBoard.setRows(10);
      gameBoard.setCols(10);
    }
    
    else if(intermediateItem.isSelected())
    {
      gameBoard.setMines(40);
      gameBoard.setRows(16);
      gameBoard.setCols(16);
    }
    
    else if(expertItem.isSelected())
    {
      gameBoard.setMines(60);
      gameBoard.setRows(24);
      gameBoard.setCols(24);
    }
  }
}