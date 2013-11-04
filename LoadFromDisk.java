package mines;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class LoadFromDisk
{
    private String filename = "mines/newgame.txt";
    private int n = 0;//length of array
    private int[] arr = null;//initialise an array

    public LoadFromDisk()
    {
        try
        {
            Scanner inFile = new Scanner(new File(filename));

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
            JOptionPane.showMessageDialog(null, "newgame.txt is missing!");
        }

    }
}
