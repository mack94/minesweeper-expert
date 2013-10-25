package mines;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class redoListener implements ActionListener
{

    @Override
    public void actionPerformed(ActionEvent e)
    {
        int undoRedo = Board.getUndoRedoIndex();
        undoRedo++;
        Board.setUndoRedoIndex(undoRedo);
        int[] newfield = Board.getUndoRedoArray(undoRedo);
        Board.setField(newfield);
        //Board.repaint();
        System.out.println(undoRedo);

    }

}
