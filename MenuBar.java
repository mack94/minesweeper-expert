package mines;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar
{
  private JMenuBar menuBar = new JMenuBar();
  private JMenu fileMenu, viewMenu, helpMenu, highscore;
  private JMenuItem saveItem, loadItem, exitItem, newGameItem,
    beginnerItem, intermediateItem, expertItem, customItem, pauseItem, resolveItem, helpItem, aboutItem;
  
  public MenuBar()
  {
    buildFileMenu();
    buildGameMenu();
    buildHighscoreMenu();
    buildHelpMenu();

    //Add File, View and Help to the JMenuBar
    add(fileMenu);
    add(viewMenu);
    add(highscore);
    add(helpMenu);
  }
  
  private void buildFileMenu()
  {
    //Create menu items to add to File
    fileMenu = new JMenu("File");
    saveItem = new JMenuItem("Save");
    saveItem.addActionListener (new SaveListener());
    loadItem = new JMenuItem("Load");
    loadItem.addActionListener (new LoadListener());
    exitItem = new JMenuItem("Exit");
    exitItem.addActionListener (new ExitListener());
    fileMenu.add(saveItem);
    fileMenu.add(loadItem);
    fileMenu.add(exitItem);
  }
  
  private void buildGameMenu()
  {
    //Create menu items to add to View
    viewMenu = new JMenu("Game");
    pauseItem = new JMenuItem("Pause");
    newGameItem = new JMenuItem("New Game");
    beginnerItem = new JMenuItem("Beginner");
    intermediateItem = new JMenuItem("Intermediate");
    expertItem = new JMenuItem("Expert");
    customItem = new JMenuItem("Custom...");
    
    viewMenu.add(pauseItem);
    viewMenu.add(newGameItem);
    viewMenu.add(beginnerItem);
    viewMenu.add(intermediateItem);
    viewMenu.add(expertItem);
    viewMenu.add(customItem);
  }
  
  private void buildHelpMenu()
  {
	//Create menu items to add to Help
	  helpMenu = new JMenu("Help");
	  resolveItem = new JMenuItem("Solve");
	  helpItem = new JMenuItem("Help");
	  aboutItem = new JMenuItem("About");
	  helpMenu.add(resolveItem);
	  helpMenu.add(helpItem);
	  helpMenu.add(aboutItem);
  }
  private void buildHighscoreMenu()
  {
	  highscore = new JMenu("Highscore");
  }
}