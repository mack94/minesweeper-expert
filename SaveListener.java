import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class SaveListener implements ActionListener
{
    private PrintWriter printer = null;//Create a PrintWriter but set to null for assigning later
    private JFileChooser fileChooser = new JFileChooser();

    public void actionPerformed(ActionEvent e)
    {
        //Create new Panel
        class FileChooserPanel extends JPanel
        {
            public FileChooserPanel()
            {
            }
        }
        FileChooserPanel fileChooserPanel = new FileChooserPanel();
        //Handle save button action.
        int returnVal = fileChooser.showSaveDialog(fileChooserPanel);

        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = fileChooser.getSelectedFile();

            try
            {
                printer = new PrintWriter(new BufferedWriter(new FileWriter(file)));//Have the FileWriter overwrite a selected file
            }
            catch (FileNotFoundException ex)//Catch the exceptions
            {
                ex.printStackTrace();
                return;//break out of method
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
                return;//break out of method
            }

            int[] field = Board.getField();
            //Cycle through the field cells
            for (int i = 0; i < field.length; i++)
            {
                printer.print(field[i] + " ");//Print cell number to the file with a space to help for reading
            }

            printer.close();//Close the PrintWriter
        }
    }
}
