package mines;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;

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
    //Declare GUI objects
    private static JFrame frame;
    private static JPanel gamePanel;
    private static JLabel statusbar;

    //Generic int[] stacks
    public static Stack<int[]> undoStack = new Stack<int[]>();
    public static Stack<int[]> redoStack = new Stack<int[]>();

    //Declare static integers so that they can be accessed from static getters and setters.
    private static int noOfMines = 40;
    private static int noOfRows = 24;
    private static int noOfCols = 24;

    //Declare a Timer object
    private static Timer timer;

    //Declare and set the delay on the timer
    private final static int DELAY = 20;

    //Static boolean to be accessed across all classes
    public static boolean playingGame;

    //Static long which will contain the time a game has started in milliseconds
    private static long startTime;

    //Default width and height for the frame
    private static int height = 440;
    private static int width = 377;

    //Declare the menu bar and its items (GUI elements)
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu, editMenu, viewMenu, helpMenu, highscore;
    private static JMenuItem pauseItem;
    private JMenuItem saveItem, loadItem, exitItem, newGameItem, resolveItem,
            undoItem, redoItem;
    private JRadioButtonMenuItem beginnerItem, intermediateItem, expertItem,
            customItem;

    //Constructor of the MineFrame
    public MineFrame()
    {
        frame = new JFrame();//Create the frame for the GUI

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Have the application exit when closed
        frame.setPreferredSize(new Dimension(width, height));//Set the preferred frame size
        frame.setLocationRelativeTo(null);//Centre the frame
        frame.setTitle("Minesweeper");//Title of the frame

        statusbar = new JLabel("");//Set the passed-in status bar

        gamePanel = new JPanel(new BorderLayout());//New panel that contains the board

        frame.setJMenuBar(buildMenuBar());//Build the menu bar and set it as the JMenuBar

        frame.add(gamePanel);//Add gamePanel to the frame
        startNewGame();

        frame.setBackground(new Color(0xB3B3B3));//Set Background colour
        frame.pack();//Resize the frame to occupy the smallest amount of space
        frame.setLocationRelativeTo(null);//Centres the frame
        frame.setResizable(true);//Have the frame re-sizable useful for custom games
        frame.setVisible(true);//Show all components on the window
    }

    //Method to start/restart the game when a game has been lost, restarted or loaded
    public static void startNewGame()
    {
        gamePanel.removeAll();
        gamePanel.add(statusbar, BorderLayout.SOUTH);

        playingGame = true;//Set to true so the user may make actions
        timer = new Timer(DELAY, new TimerListener());//Initialise a timer object
        timer.start();//Start the timer
        startTime = System.currentTimeMillis(); //save the time the game started

        gamePanel.add(new Board(statusbar, getNoOfMines(), getNoOfRows(), getNoOfCols()));
        new SaveToDisk();//Save the generated board to disk
        Arrays.fill(Board.field, 0);//Set all entries in the field to 0 to prove that LoadFromDisk does work
        new LoadFromDisk();//Load the board from disk
        frame.setPreferredSize(new Dimension(width, height));

        frame.validate();
        frame.repaint();
        frame.pack();
    }

    //Method to create the MenuBar, its properties and associate ActionListners
    public JMenuBar buildMenuBar()
    {
        //Create the fileMenu and it's items
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

        //Add file items to the fileMenu
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.add(exitItem);

        //Create the editMenu and it's items
        editMenu = new JMenu("Edit");
        editMenu.setMnemonic('d');
        undoItem = new JMenuItem("Undo");
        undoItem.setMnemonic('Z');
        undoItem.addActionListener(new UndoListener());
        redoItem = new JMenuItem("Redo");
        redoItem.setMnemonic('Y');
        redoItem.addActionListener(new RedoListener());

        //Add edit items to the editMenu
        editMenu.add(undoItem);
        editMenu.add(redoItem);

        //Create the viewMenu and it's items
        viewMenu = new JMenu("Game");
        viewMenu.setMnemonic('G');
        pauseItem = new JCheckBoxMenuItem("Pause");
        pauseItem.setMnemonic('P');
        pauseItem.addActionListener(new TimerListener());
        newGameItem = new JMenuItem("New Game");
        newGameItem.setMnemonic('N');
        newGameItem.addActionListener(new newGameListener());

        //Create difficulty radio buttons
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

        //Add difficulty and view items to viewMenu
        viewMenu.add(newGameItem);
        viewMenu.add(pauseItem);
        viewMenu.add(beginnerItem);
        viewMenu.add(intermediateItem);
        viewMenu.add(expertItem);
        viewMenu.add(customItem);

        //Create the helpMenu and it's item
        helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');

        resolveItem = new JMenuItem("Solve");
        resolveItem.setMnemonic('c');
        resolveItem.addActionListener(new ResolveListener());

        //Add help item to helpMenu
        helpMenu.add(resolveItem);

        highscore = new JMenu("Highscore");
        highscore.setMnemonic('H');
        highscore.addMenuListener(new HighscoreListener());

        //Add File, View and Help Menus to the JMenuBar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        menuBar.add(highscore);
        menuBar.add(helpMenu);

        // return the menuBar
        return menuBar;
    }

    //Accessors and mutators

    //Accessor for the number of mines
    public static int getNoOfMines()
    {
        return noOfMines;
    }

    //Mutator for the number of mines
    public static void setNoOfMines(int noOfMines)
    {
        MineFrame.noOfMines = noOfMines;
    }

    //Accessor for the number of columns
    public static int getNoOfCols()
    {
        return noOfCols;
    }

    //Mutator for the number of columns
    public static void setNoOfCols(int noOfCols)
    {
        MineFrame.noOfCols = noOfCols;
    }

    //Accessor for the number of rows
    public static int getNoOfRows()
    {
        return noOfRows;
    }

    //Mutator for the number of rows
    public static void setNoOfRows(int noOfRows)
    {
        MineFrame.noOfRows = noOfRows;
    }

    //Method that returns the time elapsed from the time a game was started
    public static double getCurrentTime()
    {
        long endTime = System.currentTimeMillis();
        long tDelta = endTime - startTime;
        return tDelta / 1000.0;
    }

    //Class to handle the game difficulty changes
    private class DifficultyListener implements ActionListener
    {
        @Override
        //Beginner Difficulty
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

            //Intermediate Difficulty
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

            //Expert Difficulty
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

    //Method to rotate through all field cells to solve the board
    private class ResolveListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            for (int cCol = 0; cCol < MineFrame.getNoOfCols(); cCol++)
            {
                for (int cRow = 0; cRow < MineFrame.getNoOfRows(); cRow++)
                {
                    //Checks that the square hasn't already been uncovered by the user
                    if (Board.getField()[(cRow * MineFrame.getNoOfCols()) + cCol] >= 10 && Board.getField()[(cRow * MineFrame.getNoOfCols()) + cCol] != 20)
                    {
                        Board.getField()[(cRow * MineFrame.getNoOfCols()) + cCol] -= Board.COVER_FOR_CELL;//Remove the covers for all cells

                        if (Board.getField()[(cRow * MineFrame.getNoOfCols()) + cCol] == 9)//Check if a cell is a mine
                        {
                            Board.getField()[(cRow * MineFrame.getNoOfCols()) + cCol] += 11;//Turn mine cells into a marked mine cell
                        }
                    }
                }
            }
            Board.solved = true;
            Board.inGame = false;
            frame.repaint();//Repaint the frame to show the resolved board
        }
    }

    private class RedoListener implements ActionListener
    {
        @Override
        //Method that pushes the current redo to the undoStack and pops the redoStack to the (mine-) field
        public void actionPerformed(ActionEvent e)
        {
            if (!redoStack.empty())//Check if the undoStack is empty
            {
                undoStack.push(redoStack.peek());//Return the item to the undo stack
                Board.field = redoStack.pop();//Make the field equal to the item and remove it from the stack
                gamePanel.repaint();//Repaint the frame
            }
        }
    }

    private class UndoListener implements ActionListener
    {
        @Override
        //Method that pushes the current undo to the redoStack and pops the undoStack to the (mine-) field
        public void actionPerformed(ActionEvent e)
        {
            if (!undoStack.empty())//Check if the undoStack is empty
            {
                redoStack.push(undoStack.peek());//Push the first element of undoStack to redoStack
                Board.field = undoStack.pop();//Make the board equal to the first element in undoStack
                gamePanel.repaint();//Repaint the frame
            }
        }
    }

    public class LoadListener implements ActionListener
    {
        //Initialise fileChooser
        private JFileChooser fileChooser = new JFileChooser();

        @Override
        //Open a FileChooser, read the selected file into an array, override the (mine-) field with the array and repaint
        public void actionPerformed(ActionEvent e)
        {
            //Initialise new Panel for the File chooser
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

                //Initialise scanner
                Scanner scan = null;
                try
                {
                    scan = new Scanner(file);
                }
                catch (FileNotFoundException ex)//Handle file not found exception
                {
                    ex.printStackTrace();
                }

                int n = 0;//Initialise n
                while (scan.hasNext())
                {
                    n += 1;//Get the length of the array/file
                    scan.next();
                }
                scan.close();//Close scanner

                try
                {
                    scan = new Scanner(file);//Reopen Scanner
                }
                catch (FileNotFoundException ex)//Handle file not found exception
                {
                    ex.printStackTrace();
                }
                int[] arr = new int[n];//Initialise array
                try
                {
                    //Fill the array with the data from the file
                    for (int i = 0; i < arr.length; i++)
                    {

                        arr[i] = scan.nextInt();
                    }
                }
                catch (InputMismatchException ex)//Exception handling
                {
                    JOptionPane.showMessageDialog(null, "This file is not supported!");//Give user notification of exception
                }
                scan.close();//Close Scanner
                scan = null;//Garbage collection
                Board.field = arr;//Set arr to field
                frame.repaint();//Repaint

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
