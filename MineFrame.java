package mines;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.Timer;

public class MineFrame
{
    private static JFrame frame;
    private static JPanel gamePanel;

    private static JLabel statusbar;

    private static int noOfMines = 40;
    private static int noOfRows = 24;
    private static int noOfCols = 24;
    private static Timer timer;//Declare a Timer object
    private final static int DELAY = 20;//Delcare and set the delay on the timer
    public static boolean playingGame;//Static boolean to be accessed across all classes

    //Default width and height for the frame
    private static int height = 440;
    private static int width = 377;

    //Declare the menu bar and its items
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu, editMenu, viewMenu, helpMenu, highscore;
    private static JMenuItem pauseItem;
    private JMenuItem saveItem, loadItem, exitItem, newGameItem, resolveItem, undoItem, redoItem;
    private JRadioButtonMenuItem beginnerItem, intermediateItem, expertItem, customItem;

    //Constructor of the MineFrame
    public MineFrame()
    {
        frame = new JFrame();//Create the frame for the GUI

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Have the application exit when closed
        frame.setPreferredSize(new Dimension(width, height));//Set the preferred frame size
        frame.setLocationRelativeTo(null);//Centre the frame
        frame.setTitle("Minesweeper");//Title of the frame

        statusbar = new JLabel("");//Set the passed in status bar

        gamePanel = new JPanel(new BorderLayout());//New panel that contains the board

        frame.setJMenuBar(buildMenuBar());//Build the menu bar and set it as the JMenuBar

        frame.add(gamePanel);//Add gamePanel to the frame
        startNewGame();

        frame.setBackground(new Color(0xB3B3B3)); //Set Background colour
        frame.pack();//Resize the frame to occupy the smallest amount of space
        frame.setLocationRelativeTo(null); //Centres the frame
        frame.setResizable(true);//Have the frame re-sizable (useful for troubleshooting)
        frame.setVisible(true);//Show all components on the window
    }

    //Function to start/restart the game
    public static void startNewGame()
    {
        gamePanel.removeAll();
        gamePanel.add(statusbar, BorderLayout.SOUTH);

        playingGame = true;//Set to true so the user may make actions
        timer = new Timer(DELAY, new TimerListener());//Initialise a timer object
        timer.start();//Start the timer

        gamePanel.add(new Board(statusbar, getNoOfMines(), getNoOfRows(), getNoOfCols()));
        frame.setPreferredSize(new Dimension(width, height));

        frame.validate();
        frame.repaint();
        frame.pack();
    }

    public JMenuBar buildMenuBar()
    {
        fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
        saveItem = new JMenuItem("Save");
        saveItem.setMnemonic('S');
        saveItem.addActionListener(new SaveListener());
        loadItem = new JMenuItem("Load");
        loadItem.setMnemonic('L');
        loadItem.addActionListener(new LoadListener());
        exitItem = new JMenuItem("Exit");
        exitItem.setMnemonic('X');
        exitItem.addActionListener(new ExitListener());

        //Add items to the fileMenu
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.add(exitItem);

        //Create items to add to Edit
        editMenu = new JMenu("Edit");
        editMenu.setMnemonic('d');
        undoItem = new JMenuItem("Undo");
        undoItem.setMnemonic('Z');
        undoItem.addActionListener(new UndoListener());
        redoItem = new JMenuItem("Redo");
        redoItem.setMnemonic('Y');
        redoItem.addActionListener(new RedoListener());

        //Add items to the editMenu
        editMenu.add(undoItem);
        editMenu.add(redoItem);

        //Create menu items to add to View
        viewMenu = new JMenu("Game");
        viewMenu.setMnemonic('G');
        pauseItem = new JCheckBoxMenuItem("Pause");
        pauseItem.setMnemonic('P');
        pauseItem.addActionListener(new TimerListener());
        newGameItem = new JMenuItem("New Game");
        newGameItem.setMnemonic('N');
        newGameItem.addActionListener(new newGameListener());

        //Difficulty radio buttons
        beginnerItem = new JRadioButtonMenuItem("Beginner");
        beginnerItem.setMnemonic('B');
        beginnerItem.addActionListener(new DifficultyListener());

        intermediateItem = new JRadioButtonMenuItem("Intermediate", true);
        intermediateItem.setMnemonic('I');
        intermediateItem.addActionListener(new DifficultyListener());

        expertItem = new JRadioButtonMenuItem("Expert");
        expertItem.setMnemonic('E');
        expertItem.addActionListener(new DifficultyListener());

        customItem = new JRadioButtonMenuItem("Custom...");
        customItem.setMnemonic('C');
        customItem.addActionListener(new CustomGameListener());

        //Create a button group and add the difficulty items to it
        ButtonGroup difficultyGroup = new ButtonGroup();
        difficultyGroup.add(beginnerItem);
        difficultyGroup.add(intermediateItem);
        difficultyGroup.add(expertItem);
        difficultyGroup.add(customItem);

        //Add all items to viewMenu
        viewMenu.add(newGameItem);
        viewMenu.add(pauseItem);
        viewMenu.add(beginnerItem);
        viewMenu.add(intermediateItem);
        viewMenu.add(expertItem);
        viewMenu.add(customItem);

        //Create menu items to add to Help
        helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');
        
        resolveItem = new JMenuItem("Solve");
        resolveItem.setMnemonic('c');
        resolveItem.addActionListener(new ResolveListener());

        //Add item to helpMenu
        helpMenu.add(resolveItem);

        highscore = new JMenu("Highscore");
        highscore.setMnemonic('H');
        highscore.addMenuListener(new HighscoreListener());

        //Add File, View and Help to the JMenuBar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        menuBar.add(highscore);
        menuBar.add(helpMenu);

        return menuBar;
    }

    //Accessors and mutators for the number of mines, rows and columns
    public static int getNoOfMines()
    {
        return noOfMines;
    }

    public static void setNoOfMines(int noOfMines)
    {
        MineFrame.noOfMines = noOfMines;
    }

    public static int getNoOfCols()
    {
        return noOfCols;
    }

    public static void setNoOfCols(int noOfCols)
    {
        MineFrame.noOfCols = noOfCols;
    }

    public static int getNoOfRows()
    {
        return noOfRows;
    }

    public static void setNoOfRows(int noOfRows)
    {
        MineFrame.noOfRows = noOfRows;
    }

    //Method to handle the game difficulty changes
    private class DifficultyListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (beginnerItem.isSelected())
            {
                Board.difficulty = 0;
                setNoOfMines(20);
                setNoOfRows(15);
                setNoOfCols(15);
                width = 250;
                height = 300;
                startNewGame();
            }

            else if (intermediateItem.isSelected())
            {
                Board.difficulty = 1;
                setNoOfMines(80);
                setNoOfRows(24);
                setNoOfCols(24);
                height = 440;
                width = 377;
                startNewGame();
            }

            else if (expertItem.isSelected())
            {
                Board.difficulty = 2;
                setNoOfMines(200);
                setNoOfRows(30);
                setNoOfCols(30);
                height = 529;
                width = 466;
                startNewGame();
            }
        }
    }

    //Method to call the startNewGame method when the user selects the new game menu option
    private class GameListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == newGameItem)
            {
                startNewGame();
            }
        }
    }

    
    //Method to rotate through all field cells solve the board
    private class ResolveListener implements ActionListener
    {
      @Override
      public void actionPerformed(ActionEvent arg0)
      {
        for(int cCol = 0; cCol < MineFrame.getNoOfCols(); cCol++)
        {
            for(int cRow = 0; cRow < MineFrame.getNoOfRows(); cRow++)
            {
              //Checks that the square hasn't already been uncovered by the user
              if(Board.getField()[(cRow * MineFrame.getNoOfCols()) + cCol] >= 10 && Board.getField()[(cRow * MineFrame.getNoOfCols()) + cCol] != 20)
              {
                Board.getField()[(cRow * MineFrame.getNoOfCols()) + cCol] -= Board.COVER_FOR_CELL;//Remove the covers for all cells
                
                if(Board.getField()[(cRow * MineFrame.getNoOfCols()) + cCol] == 9)//Check if a cell is a mine
                {
                  Board.getField()[(cRow * MineFrame.getNoOfCols()) + cCol] += 11;//Turn mine cells into a marked mine cell
                }
              }
            }
        }
        Board.inGame = false;
        frame.repaint();//Repaint the frame to show the resolved board
      }
    }
    
    private class RedoListener implements ActionListener
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        Board.undoRedoIndex++;
        
        try
        {
          Board.field = Board.undoRedoArray.get(Board.undoRedoIndex);
        }
        catch (ArrayIndexOutOfBoundsException ex)
        {
            System.out.println("ArrayIndexOutOfBoundsException");
        }
        System.out.println("Board repainted");//Test if the program reaches this point
        frame.repaint();
      }
    }
    
    private class UndoListener implements ActionListener
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        System.out.println(Board.undoRedoIndex);
        Board.undoRedoIndex--;
        System.out.println(Board.undoRedoIndex);
        System.out.println(Board.field);
        System.out.println(Board.undoRedoArray);

        try
        {
          Board.field = Board.undoRedoArray.get(Board.undoRedoIndex);
          System.out.println("Board repainted");//Test if the program reaches this point
          frame.repaint();
        }
        catch (ArrayIndexOutOfBoundsException ex)
        {
            System.out.println("ArrayIndexOutOfBoundsException");
        }
      }
    }
    
    public class LoadListener implements ActionListener
    {
        private JFileChooser fileChooser = new JFileChooser();    

        @Override
        public void actionPerformed(ActionEvent e)
        {
            //Create new Panel
            class FileChooserPanel extends JPanel
            {
                public FileChooserPanel()
                {
                }
            }
            FileChooserPanel fileChooserPanel = new FileChooserPanel();//Create a new FileChooserPanel
            int returnVal = fileChooser.showOpenDialog(fileChooserPanel);//Handle open button action
            
            
            if (returnVal == JFileChooser.APPROVE_OPTION)//Run the following code if the user opens a file
            {
              File file = fileChooser.getSelectedFile();//Set the file to the one selected by the user
              System.out.println("Opening: " + file.getName());//Check the program gets to here
              
            // initialise scanner
            Scanner scan = null;
            try
            {
                scan = new Scanner(file);
            }
            catch (FileNotFoundException ex)
            {
                ex.printStackTrace();
            }
            
            // get length of array
            int n=0;
            while (scan.hasNext())
            {
            n +=1;
            scan.next();
            }
            scan.close();
            
            // fill array
            try
            {
                scan = new Scanner(file);
            }
            catch (FileNotFoundException ex)
            {
                ex.printStackTrace();
            }
              int[] arr = new int[n];
              try
              {
                  for(int i = 0; i < arr.length; i++)
                  {
                  
                      arr[i] = scan.nextInt();
                  }
              }
              catch(InputMismatchException ex)
              {
                  JOptionPane.showMessageDialog(null, "This file is not supported!");
                  ex.printStackTrace();
              }
              scan.close();
              scan=null;
              Board.field=arr;
              frame.repaint();

            }
            else
            {
                System.out.println("Open command cancelled by user.");
            }
        }
    }


    //Method to handle pausing the game and the timer
    public static class TimerListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (!pauseItem.isSelected())
            {
                playingGame = false;//Stop the user making actions
                timer.stop();//Stop the timer
            }
            if (pauseItem.isSelected())
            {
                playingGame = true;//Allow the user to continue the game
                timer.start();//Start the timer counting again
            }
        }
    }
}
