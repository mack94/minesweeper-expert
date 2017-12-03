import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class LoadFromDisk
{
    private String filename = "newgame.txt";
    private int n = 0;//length of array
    private int[] arr = null;//initialise an array

    public LoadFromDisk()
    {
        File file = new File(filename);
        try
        {   
            file.createNewFile();
            Scanner inFile = new Scanner(file);

            //Get length of array
            while (inFile.hasNext())
            {
                n += 1;
                inFile.nextInt();
            }
            inFile.close();

            //Fill array
            inFile = new Scanner(new File(filename));//reopen file

            arr = new int[n];//Create a temporary array
            for (int i = 0; i < arr.length; i++)
            {
                arr[i] = inFile.nextInt();
            }
            inFile.close();//Close the scanner

            Board.setField(arr);//Have Board.field[] overwritten by arr
        }
        catch (FileNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null, "The '" + file.getAbsolutePath() + "' file could not be found!");
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "The '" + file.getAbsolutePath() + "' file could not be Created!");
        }

    }
}
