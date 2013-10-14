package mines;

//Java libraries
import javax.swing.*;
import java.awt.event.*;


public class OptionPanel_RED extends JPanel
{
    //Fields for this class
    protected JButton saveButton;
    private ButtonListener listener = new ButtonListener();
    
    //Constructor
    public OptionPanel_RED()
    {
      saveButton = new JButton("Save");
      saveButton.addActionListener(listener);
      add(saveButton);
    }
    
    //Event listener
    private class ButtonListener implements ActionListener
    {
      //Handles when a button is clicked
      public void actionPerformed(ActionEvent e)
      {
        if(e.getSource() == saveButton)
        {
          //Opens a simple JOptionPane
          JOptionPane.showMessageDialog(null, "Jared's going to implement a FileChooser soon!");
        }
      }
    }
}

