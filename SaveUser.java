package mines;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

public class SaveUser
{
    private PrintWriter printer = null;//Create a PrintWriter but set to null for assigning later
    private String difficultyString, file = "mines/highscore.txt", user = "";

    public SaveUser(int difficulty)
    {
        user = JOptionPane.showInputDialog("Enter your name:");

        //If the JOptionPane returns a string
        if (user != null)
        {
            switch (difficulty)
            {
                case 0:
                    difficultyString = "Beginner";
                    break;
                case 1:
                    difficultyString = "Intermediate";
                    break;
                case 2:
                    difficultyString = "Expert";
                    break;
            }
            try
            {
                printer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));//Have the FileWriter append to the end of the file
            }
            catch (IOException ex)//Catch the IO exception
            {
                ex.printStackTrace();
            }

            printer.println(user + ":" + difficultyString + ":" + 0);//Print to the file
            printer.close();//Close the PrintWriter
        }
    }
}
