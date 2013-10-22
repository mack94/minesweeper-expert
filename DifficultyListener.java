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
    if(beginnerItem.isSelected())
    {
      Board.difficultyFactor = (int)0.5;
      gameBoard.newGame();
      //gameBoard.resizeBoard(20, 10, 10);
    }
    
    else if(intermediateItem.isSelected())
    {
      Board.difficultyFactor = 1;
      gameBoard.newGame();
      //gameBoard.resizeBoard(40, 16, 16);
    }
    
    else if(expertItem.isSelected())
    {
      Board.difficultyFactor = 2;
      gameBoard.newGame();
      //gameBoard.resizeBoard(60, 24, 24);
    }
  }
}