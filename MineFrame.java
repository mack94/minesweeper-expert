package mines;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.Timer;

public class MineFrame
{
    private static JFrame frame;
    private static JPanel gamePanel;

    private static JLabel statusbar;
    private Board mineBoard;

    private static int noOfMines = 40;
    private static int noOfRows = 24;
    private static int noOfCols = 24;
    private static Timer timer;//Declare a Timer object
    private final static int DELAY = 20;//Delay on the timer
    public static boolean playingGame;//Static boolean to be accessed across all classes

    private static int height = 440;//Default width and height for the frame
    private static int width = 377;

    private JMenu fileMenu, editMenu, viewMenu, helpMenu, highscore;
    private JMenuBar menuBar = new JMenuBar();
    private JMenuItem saveItem, loadItem, exitItem, newGameItem;
    private static JMenuItem pauseItem;
    private JMenuItem resolveItem;
    private JMenuItem undoItem, redoItem;
    private JRadioButtonMenuItem beginnerItem, intermediateItem, expertItem,
            customItem;

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
        undoItem.addActionListener(new undoListener());
        redoItem = new JMenuItem("Redo");
        redoItem.setMnemonic('Y');
        redoItem.addActionListener(new redoListener());

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
                Board.getField()[(cRow * MineFrame.getNoOfCols()) + cCol] -= Board.COVER_FOR_CELL;//Remove the covers for all cells
                
                if(Board.getField()[(cRow * MineFrame.getNoOfCols()) + cCol] == 9)//Check if a cell is a mine
                {
                  Board.getField()[(cRow * MineFrame.getNoOfCols()) + cCol] += 11;//Turn mine cells into a marked mine cell
                }
            }
        }
        frame.repaint();
      }
    }

    
    public static class TimerListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (!pauseItem.isSelected())
            {
                playingGame = false;
                timer.stop();
            }
            if (pauseItem.isSelected())
            {
                playingGame = true;
                timer.start();
            }
        }
    }
}
