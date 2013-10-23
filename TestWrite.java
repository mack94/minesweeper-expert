import java.io.*;
import javax.swing.JOptionPane;

class TestWrite
{
  public static void main(String[] args)
  {
    String user = JOptionPane.showInputDialog("Enter your name: ");//Ask the user to input their name
    PrintWriter outfile = null;//Make an empty PrintWriter
    String file = "users.txt";//File name
    
    try
      {
        outfile = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
        
        outfile.println(user.trim());//Supposedly writes to the file
        System.out.print("User saved to users.txt");
        outfile.close();
      }
      catch(IOException ex)
      {
        ex.printStackTrace();
      }
  }
}