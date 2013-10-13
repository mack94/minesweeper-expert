package mines;

//Java libraries
import java.awt.event.*;

import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.*;
import javax.swing.JOptionPane;


public class OptionPanel extends JPanel
{
    //Fields for this class
    protected JButton saveButton;
    private ButtonListener listener = new ButtonListener();
    
    //Constructor
    public OptionPanel()
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

