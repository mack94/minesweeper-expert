package mines;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class LoadFromDisk
{
    public LoadFromDisk()
    {
        File file = new File("mines/newgame.txt");
        Scanner scan = null;
        try
        {
            scan = new Scanner(file);
        }
        catch (FileNotFoundException ex)
        {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "newgame.txt is missing!");
            return; // exit out of method
        }

        //Get length of array
        int n = 0;
        while (scan.hasNext())
        {
            n += 1;
            scan.next();
        }
        scan.close();

        //Fill array
        try
        {
            scan = new Scanner(file);
        }
        catch (FileNotFoundException ex)//Handle exception
        {
            ex.printStackTrace();
        }

        int[] arr = new int[n];//Create a temporary array
        for (int i = 0; i < arr.length; i++)
        {
            arr[i] = scan.nextInt();
        }
        scan.close();//Close the scanner
        scan = null;//Nullify the scanner
        Board.field = arr;//Have Board.field[] be emptied
    }
}
