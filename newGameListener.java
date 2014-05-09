import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class newGameListener implements ActionListener
{

    
    //Create a newGame after user agrees
    public void actionPerformed(ActionEvent e)
    {
        int ask = JOptionPane.showConfirmDialog(null, "Are you sure?");
        if (ask == 0)
        {
            MineFrame.startNewGame();
        }
    }

}
