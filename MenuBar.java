package mines;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class MenuBar extends JMenuBar
{
  private JMenuBar menuBar = new JMenuBar();
  private JMenu fileMenu, viewMenu, helpMenu;
  private JMenuItem saveItem, loadItem, exitItem, newGameItem, pauseItem, resolveItem;
  private JRadioButtonMenuItem beginnerItem, intermediateItem, expertItem, customItem;
  
  private JLabel nullLabel = new JLabel("");
  Board gameBoard = new Board(nullLabel);
  
  public MenuBar()
  {
    //Call methods to build the File and Game menus
    buildFileMenu();
    buildGameMenu();
      
    //Add Help to the JMenu
    helpMenu = new JMenu("Help");

    //Add File, View and Help to the JMenuBar
    add(fileMenu);
    add(viewMenu);
    add(helpMenu);
    
    gameBoard.setMines(40);
    gameBoard.setRows(16);
    gameBoard.setCols(16);
  }
    
  private void buildFileMenu()
  {
    //Create menu items to add to File
    fileMenu = new JMenu("File");
    saveItem = new JMenuItem("Save");
    loadItem = new JMenuItem("Load");
    exitItem = new JMenuItem("Exit");
    exitItem.addActionListener(new ExitListener());
    
    fileMenu.add(saveItem);
    fileMenu.add(loadItem);
    fileMenu.add(exitItem);
  }
  
  private void buildGameMenu()
  {
    //Create the JMenu
    viewMenu = new JMenu("Game");
    //Add the 'New Game' option to the JMenu
    newGameItem = new JMenuItem("New Game");
    newGameItem.addActionListener(new NewGameListener());
    
    //Add radio button menu items to the JMenu for difficulty and assign an ActionListener
    beginnerItem = new JRadioButtonMenuItem("Beginner");
    beginnerItem.addActionListener(new DifficultyListener());
    
    intermediateItem = new JRadioButtonMenuItem("Intermediate", true);
    intermediateItem.addActionListener(new DifficultyListener());
    
    expertItem = new JRadioButtonMenuItem("Expert");
    expertItem.addActionListener(new DifficultyListener());
    
    customItem = new JRadioButtonMenuItem("Custom...");
    customItem.addActionListener(new DifficultyListener());
    
    pauseItem = new JMenuItem("Pause");
    resolveItem = new JMenuItem("Resolve");
    
    //Group the difficulty radio buttons together
    ButtonGroup difficultyGroup = new ButtonGroup();
    
    difficultyGroup.add(beginnerItem);
    difficultyGroup.add(intermediateItem);
    difficultyGroup.add(expertItem);
    difficultyGroup.add(customItem);
    
    //Add all of the JMenu items to the JMenu
    viewMenu.add(newGameItem);
    viewMenu.add(beginnerItem);
    viewMenu.add(intermediateItem);
    viewMenu.add(expertItem);
    viewMenu.add(customItem);
    viewMenu.add(pauseItem);
    viewMenu.add(resolveItem);
  }
  
  //Private class to handle when the 'Exit' option is clicked
  private class ExitListener implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      System.exit(0);
    }
  }
  
  //Private class to generate a new game
  private class NewGameListener implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      gameBoard.newGame();
      gameBoard.repaint();
    }
  }
  
  //Private class to handle difficulty changes
  private class DifficultyListener implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      if(beginnerItem.isSelected())
      {
        //Set the number of mines, rows and cols then call the newGame method and repaint the board
        gameBoard.setMines(20);
        gameBoard.setRows(10);
        gameBoard.setCols(10);
        gameBoard.newGame();
        repaint();
      }
      if(intermediateItem.isSelected())
      {
        gameBoard.setMines(40);
        gameBoard.setRows(16);
        gameBoard.setCols(16);
        gameBoard.newGame();
        repaint();
      }
      if(expertItem.isSelected())
      {
        gameBoard.setMines(60);
        gameBoard.setRows(24);
        gameBoard.setCols(24);
        gameBoard.newGame();
      }
      if(customItem.isSelected())
      {
        //Open a JOptionPane that prompts the user for the number of rows and cols and gives a mine density of; rows * cols / 12
      }
    }
  }
}