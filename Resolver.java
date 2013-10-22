package mines;

import java.awt.event.*;
import javax.swing.*;

public class Resolver implements ActionListener
{  
  Board board = new Board(new JLabel(""));
  int[] mineField = board.getField();
  
  public void actionPerformed(ActionEvent e)
  {
    for(int i = 0; i < mineField.length; i++)
    {
      mineField[i] -= 10;
      board.repaint();
    }
  }
}