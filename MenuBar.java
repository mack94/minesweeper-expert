package mines;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar
{
  private JMenuBar menuBar = new JMenuBar();
  private JMenu fileMenu, viewMenu, helpMenu;
  private JMenuItem saveItem, loadItem, exitItem, newGameItem,
    beginnerItem, intermediateItem, expertItem, customItem, pauseItem, resolveItem;
  
  public MenuBar()
  {
    buildFileMenu();
    buildGameMenu();
      
    //Add Help to the JMenu
    helpMenu = new JMenu("Help");

    //Add File, View and Help to the JMenuBar
    add(fileMenu);
    add(viewMenu);
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
    newGameItem = new JMenuItem("New Game");
    beginnerItem = new JMenuItem("Beginner");
    intermediateItem = new JMenuItem("Intermediate");
    expertItem = new JMenuItem("Expert");
    customItem = new JMenuItem("Custom...");
    pauseItem = new JMenuItem("Pause");
    resolveItem = new JMenuItem("Resolve");
    
    viewMenu.add(newGameItem);
    viewMenu.add(beginnerItem);
    viewMenu.add(intermediateItem);
    viewMenu.add(expertItem);
    viewMenu.add(customItem);
    viewMenu.add(pauseItem);
    viewMenu.add(resolveItem);
  }
}