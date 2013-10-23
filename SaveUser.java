package mines;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedWriter;

import javax.swing.JOptionPane;

public class SaveUser
{
  private String difficultyStr;//Create an empty string to hold difficulty
  
  public SaveUser(int difficulty)
  {
    String user = JOptionPane.showInputDialog("Enter your name: ");//Ask the user to input their name
    
    if(user != null)
    {
      PrintWriter outfile = null;//Make an empty PrintWriter
      String file = "mines/users.txt";//File name
      
      switch (difficulty)
      {
        case 0: difficultyStr = "Beginner";
          break;
        case 1: difficultyStr = "Intermediate";
          break;
        case 2: difficultyStr = "Expert";
          break;
        default: difficultyStr = "Error";
      }
      
      try
      {
        outfile = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));//Create a new PrintWriter that appends to the end of the file
        outfile.println(user.trim() + " " + difficultyStr);//Write to the file
        outfile.close();
      }
      catch(IOException ex)
      {
        ex.printStackTrace();
      }
    }
  }
}