package mines;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedWriter;

import javax.swing.JOptionPane;

public class SaveUser
{
  private PrintWriter printer = null;
  private String difficultyString, file = "mines/users.txt";
  
  public SaveUser(int difficulty)
  {
    String user = JOptionPane.showInputDialog("Enter your name:");
    
    if(user != null)
    {
      switch(difficulty)
      {
        case 0: difficultyString = "Beginner";
        break;
        case 1: difficultyString = "Intermediate";
        break;
        case 2: difficultyString = "Expert";
        break;
      }
      
      try
      {
        printer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
      }
      catch(IOException ex)
      {
        ex.printStackTrace();
      }
      
      System.out.print(user + " " + difficultyString);
      printer.println(user + " " + difficultyString);
      printer.close();
    }
  }
}