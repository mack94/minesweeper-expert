package mines;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class undoListener implements ActionListener
{

    @Override
    public void actionPerformed(ActionEvent e)
    {
        int undoRedo = Board.getUndoRedoIndex();
        undoRedo--;
        Board.setUndoRedoIndex(undoRedo);
        //Board.repaint();
        System.out.println(undoRedo);
        
    }

}
