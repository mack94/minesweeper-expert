package mines;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class SaveListener implements ActionListener
{
    private PrintWriter printer = null;//Create a PrintWriter but set to null for assigning later
    private JFileChooser fileChooser = new JFileChooser();

    @Override
    public void actionPerformed(ActionEvent e)
    {
        // create new Panel
        class FileChooserPanel extends JPanel
        {
            public FileChooserPanel()
            {
            }
        }
        FileChooserPanel fileChooserPanel = new FileChooserPanel();

        //Handle open button action.
        int returnVal = fileChooser.showSaveDialog(fileChooserPanel);
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = fileChooser.getSelectedFile();
            // open the file.
            System.out.println("Saving: " + file.getName());
            try
            {
                printer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));//Have the FileWriter append to the end of the file
            }
            catch (FileNotFoundException ex)
            {
                ex.printStackTrace();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
            
            String save = "";
            int [] field = Board.getField();
            for(int i = 0; i < field.length;i++)
            {
                save += field[i];
                save += "\n";
            }
            
            printer.println(save);//Print to the file
            printer.close();//Close the PrintWriter
        }
        else
        {
            System.out.println("Open command cancelled by user.");
        }
    }

}
