package mines;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class LoadListener implements ActionListener
{
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
        int returnVal = fileChooser.showOpenDialog(fileChooserPanel);
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = fileChooser.getSelectedFile();
            // open the file.
            System.out.println("Opening: " + file.getName());
            try
            {
                Scanner diskf = new Scanner(file);
                String fileText = "";
                /*
                while(diskf.hasNextLine())
                {
                  String line = diskf.nextLine();
                  fileText += line + "\n";
                }
                label.setText(fileText);
                */
            }

            catch (FileNotFoundException ex)
            {
                ex.printStackTrace();
            }
        }
        else
        {
            System.out.println("Open command cancelled by user.");
        }
    }

}
