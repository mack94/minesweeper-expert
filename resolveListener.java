package mines;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class resolveListener implements ActionListener
{

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
        Board.resolve();
    }

}
