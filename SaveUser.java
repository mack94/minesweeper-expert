package mines;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class SaveUser
{
  private PrintWriter printer = null;
  private String difficultyString, file = "mines/highscore.txt";
  
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
      String lines = "";
      Scanner highscoreFile = null;
    try
    {
        highscoreFile = new Scanner(new File(file));
    }
    catch (FileNotFoundException e)
    {
        e.printStackTrace();
    }
    System.out.println("test");
      while (highscoreFile.hasNextLine());
      {
          System.out.println("test2");
           lines += highscoreFile.nextLine() + "\n";
      }
      try
      {
          printer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
      }
      catch (IOException ex)
      {
          ex.printStackTrace();
      }

      System.out.print(lines + user + ":" + difficultyString + ":" + 0);
      printer.println(lines + user + ":" + difficultyString + ":" + 0);
      printer.close();
    }
  }
}