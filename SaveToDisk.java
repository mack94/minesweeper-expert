package mines;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SaveToDisk
{
    private PrintWriter printer = null;//Create a PrintWriter but set to null for assigning later

    public SaveToDisk()
    {
        //Code to save the board to a file
        String file = "mines/newgame.txt";
        try
        {
            printer = new PrintWriter(new BufferedWriter(new FileWriter(file)));//Have the FileWriter overwrite a selected file
        }
        catch (FileNotFoundException ex)//Catch the exceptions
        {
            ex.printStackTrace();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        //Create an int array and have it the same as Board.field[]
        int[] a = Board.field;
        //Cycle through the field cells
        for (int i = 0; i < a.length; i++)
        {
            printer.print(a[i] + " ");//Print cell number to the file with a space to help for reading
        }

        printer.close();//Close the PrintWriter
    }
}
