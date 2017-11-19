import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

public class SaveUser
{
    private PrintWriter printer = null;//Create a PrintWriter but set to null for assigning later
    private String difficultyString, file = "highscore.txt", user = "";
    private double score = MineFrame.getScore();//get score immediately after the game is won

    public SaveUser(int difficulty)
    {
        user = JOptionPane.showInputDialog("You Won!\nEnter your name:");

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
                case 3:
                    difficultyString = "Custom";
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

            printer.println(user + ":" + difficultyString + ":" + score);//Print to the file
            printer.close();//Close the PrintWriter
        }
    }
}
