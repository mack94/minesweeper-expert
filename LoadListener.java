package mines;

import java.lang.Character;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JFileChooser;

public class LoadListener implements ActionListener
{
    private JFileChooser fileChooser = new JFileChooser();    

    @Override
    public void actionPerformed(ActionEvent e)
    {
        //Create new Panel
        class FileChooserPanel extends JPanel
        {
            public FileChooserPanel()
            {
            }
        }
        FileChooserPanel fileChooserPanel = new FileChooserPanel();//Create a new FileChooserPanel
        int returnVal = fileChooser.showOpenDialog(fileChooserPanel);//Handle open button action
        
        
        if (returnVal == JFileChooser.APPROVE_OPTION)//Run the following code if the user opens a file
        {
          File file = fileChooser.getSelectedFile();//Set the file to the one selected by the user
          System.out.println("Opening: " + file.getName());//Check the program gets to here
          
          try
          {
            BufferedReader br = new BufferedReader(new FileReader(file));   
            
            String cellVal = "", line = br.readLine();//Create a string to hold the cell value as a string
            int[] loadedField;//Create an array to hold the parsed integer values read from the file
            int tempVal = 0;
            
            for(int i = 0; i < line.length() / 4; i++)//Causes a very long read time
            {
              cellVal += br.read();
              
              if(br.read() == ' ')//Check the latest char added to see if it is a space
              {
                cellVal.trim();//Trim the whitespace off cellVal
                tempVal = Integer.parseInt(cellVal);//Make the string into an int so it can be added to loadedField[]
                cellVal = "";
                System.out.print("Testing inside the if statement");//Test if this block of code is triggered
              }
            }
            br.close();
          }
          
          catch (FileNotFoundException ex)//Catch the FileNotFoundException
          {
            ex.printStackTrace();
          }
          catch (IOException ex)//Catch the IOException
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
