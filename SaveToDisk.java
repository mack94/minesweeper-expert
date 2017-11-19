import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SaveToDisk
{
    private String filename = "newgame.txt";//store the filename
    private PrintWriter outFile = null;//Create a PrintWriter but set to null for assigning later
    private int[] arr = Board.getField();//Create an int array and have it the same as Board.field[]

    public SaveToDisk()
    {
        //Code to save the board to a file
        try
        {
            outFile = new PrintWriter(new BufferedWriter(new FileWriter(filename)));//Have the FileWriter overwrite a selected file

            //Cycle through the field cells
            for (int i = 0; i < arr.length; i++)
            {
                outFile.print(arr[i] + " ");//Print cell number to the file with a space to help for reading
            }
            outFile.close();//Close the PrintWriter
        }

        catch (IOException ex)//Catch the exceptions
        {
            System.out.println("An IO exception occurred!");
        }
    }
}
