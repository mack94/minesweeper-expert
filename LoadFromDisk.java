package mines;

import java.util.InputMismatchException;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;

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
    }
    
    //Get length of array
    int n=0;
    while (scan.hasNext())
    {
      n +=1;
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
    for(int i = 0; i < arr.length; i++)
    {
      arr[i] = scan.nextInt();
    }
    scan.close();//Close the scanner
    scan = null;//Nullify the scanner
    Board.field = arr;//Have Board.field[] be emptied
  }
}