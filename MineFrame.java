import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
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

public class MineFrame
{
    //time the game was paused for
    private static double pauseTime = 0.0;
    private static double startPauseTime = 0;

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

    //Static boolean to be accessed across all classes
    public static boolean playingGame;

    //Static long which will contain the time a game has started in milliseconds
    private static long startTime;

    //Init width and height for the gamePanel
    private static int height;
    private static int width;

    //Declare the menu bar and its items (GUI elements)
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu, editMenu, viewMenu, helpMenu;
    private static JMenuItem pauseItem;
    private JMenuItem saveItem, loadItem, exitItem, newGameItem, resolveItem,
            undoItem, redoItem, highscore;
    private JRadioButtonMenuItem beginnerItem, intermediateItem, expertItem,
            customItem;

    //Constructor of the MineFrame
    public MineFrame()
    {
        frame = new JFrame();//Create the frame for the GUI

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Have the application exit when closed
        frame.setTitle("Minesweeper");//Title of the frame
        frame.setResizable(false);//Have the frame re-sizable useful for custom games
        frame.setJMenuBar(buildMenuBar());//Build the menu bar and set it as the JMenuBar
        
        statusbar = new JLabel("");//Set the passed-in status bar
        gamePanel = new JPanel(new BorderLayout());//New panel that contains the board
        frame.add(gamePanel);//Add gamePanel to the frame
        //frame.setLocationRelativeTo(null);//Centre the frame
        startNewGame();
        frame.setVisible(true);//Show all components on the window
    }

    //Method to start/restart the game when a game has been lost, restarted or loaded
    public static void startNewGame()
    {
        gamePanel.removeAll();
        undoStack.removeAllElements();
        redoStack.removeAllElements();
        gamePanel.add(statusbar, BorderLayout.SOUTH);
        gamePanel.add(new Board(statusbar, noOfMines, noOfRows, noOfCols), BorderLayout.CENTER);

        playingGame = true;//Set to true so the user may make actions
        startTime = System.currentTimeMillis(); //save the time the game started

        //new SaveToDisk();//Save the generated board to disk 
        //Arrays.fill(Board.getField(), 0);//Set all entries in the field to 0 to prove that LoadFromDisk does work
        //new LoadFromDisk();//Load the board from disk
        
        calcDimentions();
        gamePanel.setPreferredSize(new Dimension(width, height));
        gamePanel.validate();
        gamePanel.repaint();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - width) / 2);
        int y = (int) ((dimension.getHeight() - height - 100) / 2);
        frame.setLocation(x, y);
        frame.pack();
    }

    //Method to create the MenuBar, its properties and associate ActionListners
    private JMenuBar buildMenuBar()
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
        highscore = new JMenuItem("Highscore");
        highscore.setMnemonic('H');
        highscore.addActionListener(new HighscoreListener());
        exitItem = new JMenuItem("Exit");
        exitItem.setMnemonic('X');
        exitItem.addActionListener(new ExitListener());

        //Add file items to the fileMenu
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.addSeparator();
        fileMenu.add(highscore);
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
        pauseItem.addActionListener(new PauseListener());
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
        viewMenu.addSeparator();
        viewMenu.add(beginnerItem);
        viewMenu.add(intermediateItem);
        viewMenu.add(expertItem);
        viewMenu.add(customItem);

        //Create the helpMenu and it's item
        helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');

        resolveItem = new JMenuItem("Solve Game");
        resolveItem.setMnemonic('c');
        resolveItem.addActionListener(new ResolveListener());

        //Add help item to helpMenu
        helpMenu.add(resolveItem);

        //Add File, View and Help Menus to the JMenuBar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
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
    
    //Setter for width and height
    public static void setWidth(int width)
    {
        MineFrame.width = width;
    }
    
    public static void setHeight(int height)
    {
        MineFrame.height = height;
    }

    //Method that returns the time elapsed from the time a game was started
    public static double getCurrentTime()
    {
        double endTime = System.currentTimeMillis();
        return (endTime - startTime) / 1000.0;
    }

    public static void timePause()
    {
        if (playingGame)
        {
            startPauseTime = System.currentTimeMillis();
        }

        if (!playingGame)
        {
            double endPauseTime = System.currentTimeMillis();
            pauseTime += (endPauseTime - startPauseTime) / 1000.0;
        }
    }

    //Method that returns the score (total time - paused time)
    public static double getScore()
    {
        return getCurrentTime() - pauseTime;
    }
    
    public static void calcDimentions(){
    	width = noOfCols*15;
    	height = noOfRows*15+20;
    }

    //Class to handle the game difficulty changes
    private class DifficultyListener implements ActionListener
    {
        
        //Beginner Difficulty
        public void actionPerformed(ActionEvent e)
        {
            if (beginnerItem.isSelected())
            {
                Board.setDifficulty(0);
                setNoOfMines(20);
                setNoOfRows(15);
                setNoOfCols(15);
                calcDimentions();
                startNewGame();
            }

            //Intermediate Difficulty
            else if (intermediateItem.isSelected())
            {
                Board.setDifficulty(1);
                setNoOfMines(80);
                setNoOfRows(24);
                setNoOfCols(24);
                calcDimentions();
                startNewGame();
            }

            //Expert Difficulty
            else if (expertItem.isSelected())
            {
                Board.setDifficulty(2);
                setNoOfMines(200);
                setNoOfRows(30);
                setNoOfCols(30);
                calcDimentions();
                startNewGame();
            }
        }
    }
    
    public class newGameListener implements ActionListener
    {
        //Create a newGame after user agrees
        public void actionPerformed(ActionEvent e)
        {
            int ask = JOptionPane.showConfirmDialog(null, "Are you sure?");
            if (ask == 0)
            {
                MineFrame.startNewGame();
            }
        }
    }

    //Method to rotate through all field cells to solve the board
    private class ResolveListener implements ActionListener
    {
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
            Board.setSolved(true);
            Board.setInGame(false);
            frame.repaint();//Repaint the frame to show the resolved board
        }
    }

    private class RedoListener implements ActionListener
    {
        
        //Method that pushes the current redo to the undoStack and pops the redoStack to the (mine-) field
        public void actionPerformed(ActionEvent e)
        {
            if (!redoStack.empty())//Check if the undoStack is empty
            {
                undoStack.push(redoStack.peek());//Return the item to the undo stack
                Board.setField(redoStack.pop());//Make the field equal to the item and remove it from the stack
                gamePanel.repaint();//Repaint the frame
            }
        }
    }

    private class UndoListener implements ActionListener
    {
        
        //Method that pushes the current undo to the redoStack and pops the undoStack to the (mine-) field
        public void actionPerformed(ActionEvent e)
        {
            if (!undoStack.empty())//Check if the undoStack is empty
            {
                redoStack.push(undoStack.pop());//Push the first element of undoStack to redoStack and remove current field
                if (!undoStack.empty())
                {
                	Board.setField(undoStack.pop());//Make the board equal to the next element in undoStack
                	Board.pushFieldToUndoStack();//Push the new current frame into stack
                	gamePanel.repaint();//Repaint the frame
                }
                else if(!redoStack.empty())
                {
                	undoStack.push(redoStack.pop());//Return the item to the undo stack
                }
            }
        }
    }

    public class LoadListener implements ActionListener
    {
        //Initialise fileChooser
        private JFileChooser fileChooser = new JFileChooser();

        
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

                try
                {
                    Scanner scan = new Scanner(file);

                    int n = 0;//Initialise n
                    while (scan.hasNext())
                    {
                        n += 1;//Get the length of the array/file
                        scan.next();
                    }
                    scan.close();//Close scanner

                    scan = new Scanner(file);//Reopen Scanner

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
                    Board.setField(arr);//Set arr to field
                    frame.repaint();//Repaint
                }
                catch (FileNotFoundException ex)//Handle file not found exception
                {
                    ex.printStackTrace();
                }

            }
        }
    }

    //Method to handle pausing the game
    public static class PauseListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (pauseItem.isSelected())
            {
                timePause();//save current time
                playingGame = false;//Stop the user making actions
            }
            if (!pauseItem.isSelected())
            {
                timePause();//calculate time the game was paused for
                playingGame = true;//Allow the user to continue the game
            }
        }
    }
    public class ExitListener implements ActionListener {

    	public void actionPerformed(ActionEvent e) {
    		//Quit the program
    		System.exit(0);
    	}
    }
}
